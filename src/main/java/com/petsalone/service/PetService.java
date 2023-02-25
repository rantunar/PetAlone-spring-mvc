package com.petsalone.service;

import com.petsalone.entity.Pet;
import com.petsalone.entity.PetType;
import java.util.List;
import java.util.Map;

public interface PetService {

  Map<Integer, String> getAllPetTypes();

  List<Pet> getPets(Integer petType);

  void savePetData(Pet pet);
}
