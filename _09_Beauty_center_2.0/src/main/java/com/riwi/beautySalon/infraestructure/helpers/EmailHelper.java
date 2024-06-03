package com.riwi.beautySalon.infraestructure.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailHelper 
{
    private final JavaMailSender mailSender;


    //Enviar el email
    public ResponseEntity<String> sendMail(String destinity, String nameClient, String nameEmployee, LocalDateTime date) {
        MimeMessage message = mailSender.createMimeMessage(); // Se usa cuando es mensaje html
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateAppointment = date.format(formatter);
        String htmlContent = readHTMLTemplate(nameClient, nameEmployee, dateAppointment);
    
        try {
            message.setFrom(new InternetAddress("juanferhiguita65@gmail.com")); // Va el correo corporativo
            message.setSubject("Cite Confirm in Beauty Salon!");
    
            message.setRecipients(MimeMessage.RecipientType.TO, destinity); // De donde se envia, a donde ->
            message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE); // Enumerado de tipo HTML
    
            mailSender.send(message);
            return ResponseEntity.ok("Email sent successfully"); // Indica que el correo se envió correctamente
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
            "An unexpected error occurred", 
            e.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //Leer y remplazar palabras html
    private String readHTMLTemplate(String nameClient, String employeeName, String date)
    {
        //Leer el template de html
        final Path path = Paths.get("src/main/resources/emails/email_template.html");
        try(var lines = Files.lines(path)) 
        {
            var html = lines.collect(Collectors.joining());//unir todo el html es una sola linea
            
            return html.replace( "{userName}" , nameClient).replace("{employee}", employeeName).replace("{date}", date);
        }
        catch (IOException e)
        {
            System.out.println("Email template cant be readed...");
            throw new RuntimeException();
        }
    }
}
