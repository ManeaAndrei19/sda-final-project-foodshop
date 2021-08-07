package com.maneaandrei.sda.foodshop.controller;

import com.maneaandrei.sda.foodshop.service.AccountService;
import com.maneaandrei.sda.foodshop.service.dto.AccountDTO;
import com.maneaandrei.sda.foodshop.service.dto.UserRegistrationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class AccountRegistrationController {
    private final AccountService accountService;

    public AccountRegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute(name = "user")
    public UserRegistrationDTO accountRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerAccount(@ModelAttribute(name = "user") @Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult) {
        if (accountService.accountExist(userRegistrationDTO.getEmail())) {
            bindingResult.rejectValue("login", null, "account already exists");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        accountService.createAccount(userRegistrationDTO);

        return "redirect:/registration?success";
    }

}
