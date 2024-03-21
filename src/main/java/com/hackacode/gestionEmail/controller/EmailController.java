package com.hackacode.gestionEmail.controller;

import com.hackacode.gestionEmail.models.PdfModel;
import com.hackacode.gestionEmail.models.VentaModel;
import com.hackacode.gestionEmail.service.EmpCliService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("verificacion")
@Slf4j
public class EmailController {

    @Autowired
    private EmpCliService empCliService;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping
    public ResponseEntity<String> generarEmail(@RequestBody VentaModel venta) throws IOException {
        String destinatario = "21zam03.free@gmail.com";
        String asunto = "\uD83D\uDECD\uFE0F✨ "+venta.getCliente().getNombre()+ " su compra se realizo con exito!";
        String mensaje = "En hora buena "+venta.getCliente().getNombre()+" su compra se realizo con exito! muchas gracias por confiar en nuestros servicios. " +
                "Se adjunta su detalle de compra en pdf.";

        PdfModel pdfModel = new PdfModel();
        String plantillaVenta= pdfModel.generarPlantillaParaVenta(venta);
        byte[] pdfData = pdfModel.crearPdf(plantillaVenta, venta.getCliente().getNombre(), venta.getIdVenta().toString());

        //Crear el correo electrónico
        /*MimeMessage correo = javaMailSender.createMimeMessage();
        try {
            // Utilizar MimeMessageHelper para adjuntar el archivo PDF
            MimeMessageHelper helper = new MimeMessageHelper(correo, true);

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensaje);

            // Adjuntar el PDF al correo electrónico
            ByteArrayResource pdfAdjunto = new ByteArrayResource(pdfData);
            helper.addAttachment("DetalleCompra.pdf", pdfAdjunto);

            // Enviar el correo electrónico
            javaMailSender.send(correo);

            return new ResponseEntity<>("Correo enviado con el archivo adjunto", HttpStatus.CREATED);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Error al enviar el correo electrónico: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
        return new ResponseEntity<>("Correo enviado con el archivo adjunto", HttpStatus.CREATED);
    }

}
