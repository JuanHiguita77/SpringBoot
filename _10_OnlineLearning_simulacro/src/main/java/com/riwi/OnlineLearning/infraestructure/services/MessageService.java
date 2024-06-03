package com.riwi.OnlineLearning.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.OnlineLearning.api.dto.request.MessageReq;
import com.riwi.OnlineLearning.api.dto.response.MessageResp;
import com.riwi.OnlineLearning.domain.entities.Course;
import com.riwi.OnlineLearning.domain.entities.Message;
import com.riwi.OnlineLearning.domain.entities.User;
import com.riwi.OnlineLearning.domain.repositories.CourseRepository;
import com.riwi.OnlineLearning.domain.repositories.MessageRepository;
import com.riwi.OnlineLearning.domain.repositories.UserRepository;
import com.riwi.OnlineLearning.infraestructure.abstract_services.IMessageService;
import com.riwi.OnlineLearning.utils.enums.SortType;
import com.riwi.OnlineLearning.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService implements IMessageService {

    private static final String FIELD_BY_SORT = "sentDate";

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Override
    public MessageResp create(MessageReq request) {
        Message message = this.requestToEntity(request);
        return this.entityToResp(this.messageRepository.save(message));
    }

    @Override
    public MessageResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public MessageResp update(MessageReq request, Long id) {
        Message message = this.find(id);

        message= this.requestToEntity(request);
        message.setId(id);

        return this.entityToResp(this.messageRepository.save(message));
    }

    @Override
    public void delete(Long id) {
        Message message = this.find(id);
        this.messageRepository.delete(message);
    }

    @Override
    public Page<MessageResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.messageRepository.findAll(pagination).map(this::entityToResp);
    }

    @Override
    public List<MessageResp> getMessagesByCourseId(Long courseId) {
        List<Message> messages = this.messageRepository.findByCourseId(courseId);
        return messages.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    @Override
public List<MessageResp> getMessagesBetweenUsers(Long senderId, Long receiverId) {
    // Buscar mensajes donde el senderId sea el remitente y el receiverId sea el destinatario
    List<Message> messagesBetweenUsers = this.messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);

    // Convertir los mensajes a DTOs de respuesta
    return messagesBetweenUsers.stream()
            .map(this::entityToResp)
            .collect(Collectors.toList());
}

    private MessageResp entityToResp(Message entity) {
        return MessageResp.builder()
                .messageId(entity.getId())
                .senderId(entity.getSender().getId())
                .receiverId(entity.getReceiver().getId())
                .courseId(entity.getCourse().getId())
                .messageContent(entity.getMessageContent())
                .sentDate(entity.getSentDate())
                .build();
    }

    private Message requestToEntity(MessageReq messageReq) {
        User sender = this.userRepository.findById(messageReq.getSenderId()).orElseThrow(() -> new BadRequestException("No Sender found with the supplied ID"));

        User receiver = this.userRepository.findById(messageReq.getReceiverId()).orElseThrow(() -> new BadRequestException("No Receiver found with the supplied ID"));

        Course course = this.courseRepository.findById(messageReq.getCourseId()).orElseThrow(() -> new BadRequestException("No Course found with the supplied ID"));

        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .course(course)
                .messageContent(messageReq.getMessageContent())
                .sentDate(messageReq.getSentDate())
                .build();
    }

    private Message find(Long id) {
        return this.messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No message found with the supplied ID"));
    }
}
