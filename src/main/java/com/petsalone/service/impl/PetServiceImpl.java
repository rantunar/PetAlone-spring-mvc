package com.petsalone.service.impl;

import com.petsalone.entity.Pet;
import com.petsalone.entity.PetType;
import com.petsalone.repository.PetRepository;
import com.petsalone.repository.PetTypeRepository;
import com.petsalone.service.PetService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

  private Map<Integer, String> petTypesMap = new HashMap<>();

  @Autowired private PetRepository petRepository;

  @Autowired private PetTypeRepository petTypeRepository;

  Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

  @Override
  public Map<Integer, String> getAllPetTypes() {
    if (petTypesMap.isEmpty()) {
      logger.info("No data found in pet type local cache, hit db");
      petTypesMap =
          petTypeRepository.findAll().stream()
              .collect(Collectors.toMap(PetType::getId, PetType::getType));
    } else {
      logger.info("data found in pet type local cache");
    }
    return petTypesMap;
  }

  @Override
  public List<Pet> getPets(Integer petId) {
    List<Pet> finalList = new ArrayList<>();
    try {
      if (petId != null) {
        logger.info("Searching all missing pet list by petType id :" + petId);
        Optional<PetType> petType = petTypeRepository.findById(petId);
        if (petType.isPresent()) {
          finalList = petRepository.findByPetType(petType.get());
          logger.info("Pet data found with for petid: " + petId + " size: " + finalList.size());
        } else return finalList;
      } else {
        logger.info("Getting all missing pet list");
        finalList = petRepository.findAll();
      }
      finalList =
          finalList.stream()
              .sorted(Comparator.comparing(Pet::getMissingSince).reversed())
              .collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("Exception: petId = " + petId + "error: " + e);
    }
    return finalList;
  }

  @Override
  public void savePetData(Pet pet) {
    final Pet obj = petRepository.save(pet);
    logger.info("Pet data saved with id =" + obj.getId());
  }
}
