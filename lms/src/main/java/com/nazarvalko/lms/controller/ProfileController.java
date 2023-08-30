package com.nazarvalko.lms.controller;

import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.service.UserService;
import com.nazarvalko.lms.util.ImageUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Base64;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String profile(Principal principal, Model model) throws IOException {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("update")
    public String updateProfile(@ModelAttribute("user") User user,
                                @RequestParam(value = "profilePhoto", required = false) MultipartFile file) throws IOException {

        userService.updateProfile(user, file);
        return "redirect:/profile/";
    }

}
