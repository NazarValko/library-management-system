package com.nazarvalko.lms.controller;


import com.itextpdf.text.DocumentException;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.service.PdfService;
import com.nazarvalko.lms.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import java.io.IOException;
import java.security.Principal;

@Controller
public class GetPdfController {

    private UserService userService;
    private PdfService pdfService;


    @Autowired
    public GetPdfController(UserService userService, PdfService pdfService) {
        this.userService = userService;
        this.pdfService = pdfService;
    }

    @GetMapping("/generate-pdf")
    public void generatePdf(Principal principal, HttpServletResponse response) throws IOException, DocumentException {
        String username = principal.getName();
        User user = userService.findUserByEmail(username);
        if (user != null) {
            pdfService.generatePdf(user, response);
        }
    }
}
