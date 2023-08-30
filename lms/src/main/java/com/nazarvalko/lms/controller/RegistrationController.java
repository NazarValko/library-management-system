package com.nazarvalko.lms.controller;

import com.nazarvalko.lms.command.RegistrationCommand;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/form")
    public String register(Model model) {
        model.addAttribute("registerCommand", new RegistrationCommand());
        return "registration/registration-form";
    }
    @GetMapping("/confirmation")
    public String confirm() {
        return "registration/confirmation";
    }
    @PostMapping("/sign-up")
    public String processRegistration(@Valid @ModelAttribute("registerCommand") RegistrationCommand registrationCommand,
                                      BindingResult theBindingResult,
                                      HttpSession session, Model model) {

        User user = new User();
        user.setFirstName(registrationCommand.getFirstName());
        user.setLastName(registrationCommand.getLastName());
        user.setEmail(registrationCommand.getEmail());
        user.setUsername(registrationCommand.getUsername());
        user.setPassword(registrationCommand.getPassword());
        user.setAddress(registrationCommand.getAddress());
        user.setPhone(registrationCommand.getPhone());


        String userName = user.getUsername();
        User existing = userService.findUserByUsername(userName);

        if (theBindingResult.hasErrors()){
            return "registration/registration-form";
        }
        if (existing != null){
            model.addAttribute("webUser", new User());
            model.addAttribute("registrationError", "User name already exists.");

            return "redirect:/registration/registration-form";
        }
        userService.addNewUser(user);
        session.setAttribute("user", user);
        return "redirect:/confirmation";
    }
}
