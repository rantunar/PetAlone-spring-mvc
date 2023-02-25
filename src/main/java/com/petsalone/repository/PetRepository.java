package com.petsalone.repository;

import com.petsalone.entity.Pet;
import com.petsalone.entity.PetType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
  List<Pet> findByPetType(PetType petType);
}
