'use strict';

let usernamePage = document.querySelector('.username-page');
let chatPage = document.querySelector('#chat-page');
let usernameForm = document.querySelector('#usernameForm');
let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#messageInput');
let messageArea = document.querySelector('#chat-messages');
let connectingElement = document.querySelector('.connecting');
let closebtn = document.querySelector('#closebtn');

let stompClient = null;
let username = null;
let colors = ['#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107', '#ff85af', '#FF9800', '#39bbb0'];

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);

//reload page
closebtn.addEventListener('click', () => location.reload());

function connect(event) {
    username = document.querySelector("#usernameInput").value.trim();
    
    if(username && username.length >= 3) {
        usernamePage.classList.add("hidden");
        chatPage.classList.remove("hidden");
        chatPage.style.display = "flex";

        // Connect to the WebSocket server
        let socket = new SockJS('/ws');

        // Create a new Stomp client
        stompClient = Stomp.over(socket);

        // Define the onConnected function to subscribe to the chat topic
        stompClient.connect({}, onConnected, onError);   
    }
    else
    {
        alert("Please enter a valid username.");
    }

    event.preventDefault();
}

function onConnected() {
    // Subscribe to the public topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageElement = document.createElement('li');

    switch (message.type) {
        case 'JOIN':
            messageElement.classList.add('event-message');
            messageElement.textContent = message.sender + ' joined!';
            messageArea.appendChild(messageElement); // Ensure the message is added to the chat area
            break;
        case 'CHAT':
            addMessage(message);
            break;
        case 'LEAVE':
            console.log("entro al leave")
            messageElement.classList.add('event-message');
            messageElement.textContent = message.sender + ' left!';

            messageArea.appendChild(messageElement);
            break;
    }
}

function addMessage(message) {
    let messageElement = document.createElement('li');
    let avatarElement = document.createElement('i');
    let usernameElement = document.createElement('span');
    let textElement = document.createElement('p');
    let avatarText = document.createTextNode(message.sender[0]);
    
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    messageElement.classList.add('chat-message');

    avatarElement.classList.add('fa', 'fa-user-circle', 'pull-left', 'avatar');
    usernameElement.classList.add('username');
    textElement.classList.add('message-text');

    usernameElement.textContent = message.sender;
    textElement.textContent = message.content;

    messageElement.appendChild(avatarElement);
    messageElement.appendChild(usernameElement);
    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);

     // **Scroll automático al final**
    messageArea.scrollTop = messageArea.scrollHeight;
}

function sendMessage(event) {
    let messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageContent,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash += messageSender.charCodeAt(i);
    }
    let index = hash % colors.length;
    return colors[index];
}
