package com.petsalone.controller;

import com.petsalone.entity.Pet;
import com.petsalone.service.PetService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetController {

  @Autowired PetService petService;

  Logger logger = LoggerFactory.getLogger(PetController.class);

  @GetMapping("/")
  String getMissingPets(@RequestParam(required = false) Integer petId, Model model) {
    List<Pet> pets = petService.getPets(petId);
    model.addAttribute("petsData", pets);
    model.addAttribute("petTypes", petService.getAllPetTypes());
    logger.info("Returned missing pet list with size: " + pets.size());
    return "layout";
  }

  @PostMapping("/create")
  String createPet(
      @Valid @ModelAttribute("pet") Pet pet, BindingResult bindingResult, Model model) {
    logger.info("Request received for create request: " + pet.toString());
    model.addAttribute("petTypes", petService.getAllPetTypes());
    if (bindingResult.hasErrors()) {
      return "createPet";
    }
    petService.savePetData(pet);
    model.addAttribute("message", "Data saved successfully!!");
    return "createPet";
  }
}
