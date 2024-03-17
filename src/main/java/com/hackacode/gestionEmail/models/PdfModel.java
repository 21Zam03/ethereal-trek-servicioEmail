package com.hackacode.gestionEmail.models;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfModel {

    private String nombre;

    public byte[] crearPdf(VentaModel ventaModel) throws IOException {
        PdfWriter writer = null;
        File file = null;
        try {
            //Ubicacion del archivo.
            file = new File("C:/Users/DELL/Desktop/TeamCode/ArchivosPdfs/"+ventaModel.getIdVenta()+" "+ventaModel.getCliente().getNombre()+ ".pdf");

            //Se instancia un objeto de tipo Pdfwriter que permitira escribir en el documento
            writer = new PdfWriter(file);

            //Creamos un objeto de tipo PdfDocument que representara el documento
            PdfDocument docPdf = new PdfDocument(writer);

            //Del objeto docPdf agregamos un evento de tipo START_PAGE que permitira que en todas las paginas se cree un encabezado
            docPdf.addEventHandler(PdfDocumentEvent.START_PAGE, new IEventHandler() {
                @Override
                public void handleEvent(Event event) {
                    try {
                        //Se crea un PdfDocumentEvent para el manejo de eventos en un documento pdf
                        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                        PdfDocument docPdf = docEvent.getDocument();
                        PdfPage page = docEvent.getPage();

                        //Creamos un canvas que servira para poder crear el encabezado
                        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), docPdf);

                        /*La constante PdfEncodings.IDENTITY_H se utiliza para especificar la codificación de la fuente y el valor booleano
                        true se utiliza para especificar que la fuente se incrustará en el documento.*/
                        PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/Helvetica-Bold-Font.ttf", PdfEncodings.IDENTITY_H, true);

                        //Creamos la primera parte del encabezado
                        canvas.beginText().setFontAndSize(font, 14)
                                .moveText(50, 806)
                                .showText("ETHEREAL TREK")
                                .endText();
                        //Creamos la segunda parte del encabezado
                        canvas.beginText().setFontAndSize(font, 14)
                                .moveText(365, 806)
                                .showText("RECIBO DE PAGO")
                                .endText();
                        //Creamos la linea
                        canvas.beginText()
                                .moveTo(50, 800)
                                .lineTo(page.getPageSize().getWidth() - 50, 800)
                                .setLineWidth(2)
                                .stroke()
                                .endText();
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }

            });

            //Document se encargara de escribir en el documento PDF
            try (Document documento = new Document(docPdf);) {
                com.itextpdf.layout.element.Image imagen = null;
                try {
                    imagen = new Image(ImageDataFactory.create("src/main/resources/imgs/logo.jpeg"));
                    imagen.scaleAbsolute(60, 60);
                    imagen.setHorizontalAlignment(HorizontalAlignment.CENTER);
                } catch (MalformedURLException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

                Paragraph p1 = new Paragraph();
                p1.add(new Text("Telefono: 903712983\n"));
                p1.add(new Text("Av. Aramburu 153\n"));

                Paragraph p2 = new Paragraph();
                p2.add(new Text("R.U.C: 10957829456\n"));
                p2.add(new Text("N° "+ventaModel.getIdVenta()));

                Table table = new Table(3);
                table.addCell(new Cell().add(imagen).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setWidth(180));
                table.addCell(new Cell().add(p1).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(new Cell().add(p2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                table.useAllAvailableWidth();
                table.setAutoLayout();

                Table tablaCliente = new Table(4);
                com.itextpdf.kernel.colors.Color color = ColorConstants.BLUE;
                com.itextpdf.kernel.colors.Color fondo = ColorConstants.GREEN;
                tablaCliente.addCell(new Cell().add(new Paragraph("Dni/Ruc").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Nombre").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Telefono").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Direccion").setFontColor(color)).setBackgroundColor(fondo).setBorder(Border.NO_BORDER));
                tablaCliente.setHorizontalAlignment(HorizontalAlignment.CENTER);
                tablaCliente.useAllAvailableWidth();
                tablaCliente.setAutoLayout();

                Table datosCliente = new Table(4);
                tablaCliente.addCell(new Cell().add(new Paragraph(ventaModel.getCliente().getDni())).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph(ventaModel.getCliente().getNombre())).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph(ventaModel.getCliente().getCelular())).setBorder(Border.NO_BORDER));
                tablaCliente.addCell(new Cell().add(new Paragraph("Av. La estancia")).setBorder(Border.NO_BORDER));
                datosCliente.setHorizontalAlignment(HorizontalAlignment.CENTER);
                datosCliente.useAllAvailableWidth();
                datosCliente.setAutoLayout();

                Table productos = new Table(4);
                Color colorFondo = ColorConstants.LIGHT_GRAY;
                productos.addCell(new Cell().add(new Paragraph("Descripcion").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setWidth(250).setBackgroundColor(colorFondo));
                productos.addCell(new Cell().add(new Paragraph("Cantidad").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setBackgroundColor(colorFondo));
                productos.addCell(new Cell().add(new Paragraph("Precio U").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setBackgroundColor(colorFondo));
                productos.addCell(new Cell().add(new Paragraph("Precio T").setFontColor(color).setBold()).setBorder(Border.NO_BORDER).setBackgroundColor(colorFondo));
                productos.setHorizontalAlignment(HorizontalAlignment.CENTER);
                productos.useAllAvailableWidth();
                productos.setAutoLayout();

                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(table);
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Vendido a: ").setBold());
                documento.add(new Paragraph(" "));
                documento.add(tablaCliente);
                documento.add(datosCliente);
                documento.add(new Paragraph(" "));
                documento.add(productos);

                double total = 0.0;
                /*for (int i=0; i<listaProductos.size(); i++) {
                    double precio_cantidad = listaProductos.get(i).getPrecio()*listaProductos.get(i).getCantidad();
                    total = precio_cantidad + total;
                    Table pro = new Table(4);
                    pro.addCell(new Cell().add(new Paragraph(listaProductos.get(i).getNombre())).setBorder(Border.NO_BORDER).setWidth(250));
                    pro.addCell(new Cell().add(new Paragraph(String.valueOf(listaProductos.get(i).getCantidad()+" u"))).setWidth(88).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/"+String.valueOf(listaProductos.get(i).getPrecio()))).setWidth(85).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/"+String.format("%.2f",(listaProductos.get(i).getPrecio()*listaProductos.get(i).getCantidad())))).setBorder(Border.NO_BORDER));
                    pro.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    pro.useAllAvailableWidth();
                    pro.setAutoLayout();
                    documento.add(pro);
                }*/

                if (ventaModel.getServicio() != null) {
                    Table pro = new Table(4);
                    pro.addCell(new Cell().add(new Paragraph(ventaModel.getServicio().getNombre())).setBorder(Border.NO_BORDER).setWidth(250));
                    pro.addCell(new Cell().add(new Paragraph(String.valueOf("1 u"))).setWidth(88).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/" + String.valueOf("....."))).setWidth(85).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/" + String.format("%.2f", (3.4 * 1)))).setBorder(Border.NO_BORDER));
                    pro.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    pro.useAllAvailableWidth();
                    pro.setAutoLayout();
                    documento.add(pro);
                } else {
                    Table pro = new Table(4);
                    pro.addCell(new Cell().add(new Paragraph(ventaModel.getPaquete().getNombre())).setBorder(Border.NO_BORDER).setWidth(250));
                    pro.addCell(new Cell().add(new Paragraph(String.valueOf("1 u"))).setWidth(88).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/" + String.valueOf(ventaModel.getPaquete().getPrecio()))).setWidth(85).setBorder(Border.NO_BORDER));
                    pro.addCell(new Cell().add(new Paragraph("S/" + String.format("%.2f", (ventaModel.getPaquete().getPrecio() * 1)))).setBorder(Border.NO_BORDER));
                    pro.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    pro.useAllAvailableWidth();
                    pro.setAutoLayout();
                    documento.add(pro);
                }

                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Total a pagar: S/" + String.format("%.2f", total)).setTextAlignment(TextAlignment.RIGHT));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Cancelacion y firma").setTextAlignment(TextAlignment.CENTER));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("--------------------").setTextAlignment(TextAlignment.CENTER));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Gracias por su compra").setTextAlignment(TextAlignment.CENTER));
                documento.close();
                docPdf.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        /*String nombre = ventaModel.getCliente().getNombre() + ".pdf";
        this.abrirPdf(nombre);*/
        return this.generarBytes(file);
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

    public byte[] generarBytes(File pdfFile) throws IOException {
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

}
