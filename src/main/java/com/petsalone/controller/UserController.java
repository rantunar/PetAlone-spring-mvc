package com.petsalone.controller;

import com.petsalone.entity.Pet;
import com.petsalone.models.LoginModel;
import com.petsalone.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

  @Autowired PetService petService;

  @GetMapping("/users/loginsuccess")
  String loginSuccess(Model model) {
    model.addAttribute("pet", new Pet());
    model.addAttribute("petTypes", petService.getAllPetTypes());
    return "createPet";
  }

  @GetMapping("/users/login")
  String successLogin(LoginModel loginModel, Model model, String error, String logout) {
    if (error != null) model.addAttribute("error", "Your username and password is invalid.");
    if (logout != null) model.addAttribute("message", "You have been logged out successfully.");
    return "userLogin";
  }
}
