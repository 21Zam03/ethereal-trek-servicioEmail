package com.hackacode.gestionEmail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.awt.*;
import java.io.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfModel {

    private String nombre;

    public byte[] crearPdf(String plantillVenta, String nombre, String numVenta) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // Inicializar el renderizador de Flying Saucer
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(plantillVenta);
            renderer.layout();
            renderer.createPDF(outputStream);
            this.guardarPdf("C:/Users/DELL/Desktop/TeamCode/ArchivosPdfs/"+numVenta+" "+nombre+".pdf", outputStream.toByteArray());
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String guardarPdf(String ruta, byte[] pdfData) {
        try {
            // Guardar el PDF en el sistema de archivos
            try (FileOutputStream fileOutputStream = new FileOutputStream(ruta)) {
                fileOutputStream.write(pdfData);
            }
            return ruta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generarPlantillaParaVenta(VentaModel venta) {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, Helvetica, sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        .titulo {\n" +
                "            background-color: #81AFFA;\n" +
                "            color: white;\n" +
                "            padding: 10px 20px;\n" +
                "            text-align: center;\n" +
                "            width: 80%;\n" +
                "            margin: auto;\n" +
                "            border-radius: 10px;\n" +
                "            margin-bottom: 50px;\n" +
                "        }\n" +
                "\n" +
                "        .etiqueta {\n" +
                "            margin: auto;\n" +
                "            width: 84%;\n" +
                "            padding: 5px 10px;\n" +
                "            color: white;\n" +
                "        }\n" +
                "\n" +
                "        .etiqueta p {\n" +
                "            display: inline;\n" +
                "            margin: 0px;\n" +
                "            padding: 5px 10px;\n" +
                "            background-color: rgb(11, 92, 154);\n" +
                "        }\n" +
                "\n" +
                "        .empresa,\n" +
                "        .cliente,\n" +
                "        .final {\n" +
                "            width: 80%;\n" +
                "            margin: auto;\n" +
                "            border: 1px solid black;\n" +
                "            padding: 10px 20px;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .cliente {\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        .total {\n" +
                "            width: 80%;\n" +
                "            margin: auto;\n" +
                "            padding: 10px 20px;\n" +
                "            margin-bottom: 10px;\n" +
                "            justify-content: end;\n" +
                "            align-items: center;\n" +
                "        }\n" +
                "\n" +
                "        .tabla {\n" +
                "            width: 90%;\n" +
                "            margin: auto;\n" +
                "        }\n" +
                "\n" +
                "        .tabla,\n" +
                "        .final {\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        .final {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .tabla {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border-collapse: collapse;\n" +
                "            margin: auto;\n" +
                "            width: 100%;\n" +
                "            padding: 10px 0px;\n" +
                "        }\n" +
                "\n" +
                "        thead {\n" +
                "            color: white;\n" +
                "            background-color: rgb(5, 17, 83);\n" +
                "        }\n" +
                "\n" +
                "        th, td {\n" +
                "            border: 1px solid black;\n" +
                "            padding: 10px 20px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"titulo\">\n" +
                "    <h1>RECIBO DE PAGO</h1>\n" +
                "</div>\n" +
                "<div class=\"etiqueta\">\n" +
                "    <p>Empresa</p>\n" +
                "</div>\n" +
                "<div class=\"empresa\">\n" +
                "    <p>R.U.C: {ruc}</p>\n" +
                "    <p>N°: {numeroVenta}</p>\n" +
                "    <p>Direccion: {direccion}</p>\n" +
                "</div>\n" +
                "<div class=\"etiqueta\">\n" +
                "    <p>Cliente</p>\n" +
                "</div>\n" +
                "<div class=\"cliente\">\n" +
                "    <p>Dni: {dni}</p>\n" +
                "    <p>Nombre: {nombre}</p>\n" +
                "    <p>Telefono: {telefono}</p>\n" +
                "</div>\n" +
                "<div class=\"tabla\">\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "        <tr>\n" +
                "            <th>Nombre</th>\n" +
                "            <th>Descrip</th>\n" +
                "            <th>Cant</th>\n" +
                "            <th>Precio U</th>\n" +
                "            <th>Desc</th>\n" +
                "            <th>Precio T</th>\n" +
                "        </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "            <td>{nombreProducto}</td>\n" +
                "            <td>{descripcion}</td>\n" +
                "            <td>{cantidad}</td>\n" +
                "            <td>{precioUnitario}</td>\n" +
                "            <td>{descuento}</td>\n" +
                "            <td>{precioTotal}</td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n" +
                "<div class=\"total\">\n" +
                "    <p>Total a pagar: {total}</p>\n" +
                "</div>\n" +
                "<div class=\"final\">\n" +
                "    <p>Gracias por su compra!</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";

        htmlContent = htmlContent.replace("{ruc}", "93939393939");
        htmlContent = htmlContent.replace("{numeroVenta}", venta.getIdVenta());
        htmlContent = htmlContent.replace("{direccion}", "Av. Aramburu 123");
        htmlContent = htmlContent.replace("{dni}", venta.getCliente().getDni());
        htmlContent = htmlContent.replace("{nombre}", venta.getCliente().getNombre());
        htmlContent = htmlContent.replace("{telefono}", venta.getCliente().getCelular());
        if (venta.getServicio()!= null) {
            htmlContent = htmlContent.replace("{descripcion}", venta.getServicio().getDescripcion());
            htmlContent = htmlContent.replace("{nombreProducto}", venta.getServicio().getNombre());
        } else {
            htmlContent = htmlContent.replace("{descripcion}", venta.getPaquete().getDescripcion());
            htmlContent = htmlContent.replace("{nombreProducto}", venta.getPaquete().getNombre());
        }

        htmlContent = htmlContent.replace("{cantidad}", "1");
        htmlContent = htmlContent.replace("{precioUnitario}", venta.getServicio().getCosto().toString());
        htmlContent = htmlContent.replace("{descuento}", "0%");
        htmlContent = htmlContent.replace("{precioTotal}", venta.getTotal().toString());
        htmlContent = htmlContent.replace("{total}", venta.getTotal().toString());

        return htmlContent;
    }

    public void abrirPdf(String nombre) {
        File pdf = new File("C:/Users/DELL/Desktop/TeamCode/ArchivosPdfs/" + nombre);
        if (pdf.exists()) {
            try {
                Desktop.getDesktop().open(pdf);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            System.out.println("El archivo no existe");
        }
    }

    /*public byte[] generarBytes(File pdfFile) throws IOException {
        // Verificar si el archivo existe
        if (!pdfFile.exists()) {
            throw new IOException("El archivo PDF no existe");
        }

        // Leer el contenido del archivo y escribirlo en un ByteArrayOutputStream
        try (FileInputStream fis = new FileInputStream(pdfFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            // Devolver el array de bytes
            return baos.toByteArray();
        }
    }
    */
}
