package com.riwi.beautySalon.infraestructure.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.riwi.beautySalon.api.dto.errors.ErrorsResp;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailHelper 
{
    private final JavaMailSender mailSender;


    //Enviar el email = caso normal se deberia devolver un void, ya que la respuesta la da el metodo que use este helper y noo un controlador directamente para esto como si fuera un servicio

    //Se devolveria un Sout("message") para que el programmer vea el error
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
                ErrorsResp errorResponse = ErrorsResp.builder()
                .status("error")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errors(Collections.singletonList(Collections.singletonMap("message", e.getMessage())))
                .build();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }

    //Ejemplo con Void
    /*public void sendMail(String destinity, String nameClient, String nameEmployee, LocalDateTime date){
        MimeMessage message = mailSender.createMimeMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateAppointment = date.format(formatter);
        String htmlContent = this.readHTMLTemplate(nameClient, nameEmployee, dateAppointment);

        try {
            message.setFrom(new InternetAddress("kwmejia9@gmail.com"));
            message.setSubject("Confirmación de cita en Beauty Salon");

            message.setRecipients(MimeMessage.RecipientType.TO,destinity);
            message.setContent(htmlContent,MediaType.TEXT_HTML_VALUE);

            mailSender.send(message);
            System.out.println("Email enviado");

        } catch (Exception e) {
            System.out.println("ERROR no se pudo enviar el email " + e.getMessage());

        }
    } */

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
