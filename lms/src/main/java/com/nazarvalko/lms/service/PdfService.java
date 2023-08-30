package com.nazarvalko.lms.service;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.nazarvalko.lms.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfService {

    public void generatePdf(User user, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=UserProfile.pdf");

        OutputStream outputStream = response.getOutputStream();

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        Paragraph paragraph = new Paragraph("Library Card\n\n", font);
        paragraph.add("Id: " + user.getId() + "\n");
        paragraph.add("Name: " + user.getFirstName() + " " + user.getLastName() + "\n");
        paragraph.add("Phone: " + user.getPhone() + "\n");
        paragraph.add("Email: " + user.getEmail() + "\n");
        paragraph.add("Address: " + user.getAddress() + "\n");
        Paragraph instruction = new Paragraph("Instruction\n\n", font);
        instruction.add("1. By this registration the holder agreed to abide by Rules and Regulation of the Institute\n");
        instruction.add("2. The Card is for personal use and it is not transferable\n");
        instruction.add("3. Finder of the lost card are asked to return them to the Program office at the above address\n");
        instruction.add("4. Rs 10 will be changed if this card is lost\n");
        instruction.add("5. Book will be issued only on presence of this card\n");

        document.add(paragraph);
        document.add(instruction);
        document.close();

        outputStream.close();
    }
}
