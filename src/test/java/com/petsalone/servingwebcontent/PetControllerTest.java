package com.petsalone.servingwebcontent;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.petsalone.entity.Pet;
import com.petsalone.entity.PetType;
import com.petsalone.repository.PetRepository;
import com.petsalone.repository.PetTypeRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PetControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PetRepository petRepository;

  @MockBean private PetTypeRepository petTypeRepository;

  @Test
  void testReturnAllListPetsSuccess() throws Exception {
    PetType petType = new PetType();
    petType.setId(2);
    petType.setType("Dog");
    Pet pet = new Pet();
    pet.setId(new Long(1));
    pet.setName("my pet");
    pet.setPetType(petType);
    pet.setMissingSince(LocalDateTime.now());
    Mockito.when(petRepository.findAll()).thenReturn(Arrays.asList(pet));
    Mockito.when(petTypeRepository.findAll()).thenReturn(Arrays.asList(petType));

    this.mockMvc
        .perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("layout"))
        .andExpect(model().attribute("petsData", Matchers.contains(pet)))
        .andExpect(model().attribute("petTypes", Matchers.hasValue(petType.getType())))
        .andExpect(model().attributeExists("petsData"))
        .andExpect(model().attributeExists("petTypes"));
  }

  @Test
  void testReturnPetTypeListPetsSuccess() throws Exception {
    PetType petType = new PetType();
    petType.setId(2);
    petType.setType("Dog");
    Pet pet = new Pet();
    pet.setId(new Long(1));
    pet.setName("my pet");
    pet.setPetType(petType);
    pet.setMissingSince(LocalDateTime.now());
    Mockito.when(petRepository.findByPetType(petType)).thenReturn(Arrays.asList(pet));
    Mockito.when(petTypeRepository.findAll()).thenReturn(Arrays.asList(petType));
    Mockito.when(petTypeRepository.findById(2)).thenReturn(Optional.of(petType));

    this.mockMvc
        .perform(get("/").queryParam("petId", String.valueOf(2)))
        .andExpect(status().isOk())
        .andExpect(view().name("layout"))
        .andExpect(model().attribute("petsData", Matchers.contains(pet)))
        .andExpect(model().attribute("petTypes", Matchers.hasValue(petType.getType())))
        .andExpect(model().attributeExists("petsData"))
        .andExpect(model().attributeExists("petTypes"));
  }

  @Test
  void testReturnPetTypeListPetsNoData() throws Exception {
    PetType petType = new PetType();
    petType.setId(2);
    petType.setType("Dog");
    PetType petTypeCat = new PetType();
    petTypeCat.setId(1);
    petTypeCat.setType("Cat");
    Pet pet = new Pet();
    pet.setId(new Long(1));
    pet.setName("my pet");
    pet.setPetType(petType);
    pet.setMissingSince(LocalDateTime.now());
    Mockito.when(petRepository.findByPetType(petType)).thenReturn(Arrays.asList(pet));
    Mockito.when(petTypeRepository.findAll()).thenReturn(Arrays.asList(petType, petTypeCat));
    Mockito.when(petTypeRepository.findById(1)).thenReturn(Optional.of(petTypeCat));

    this.mockMvc
        .perform(get("/").queryParam("petId", String.valueOf(1)))
        .andExpect(status().isOk())
        .andExpect(view().name("layout"))
        .andExpect(model().attribute("petsData", Matchers.empty()))
        .andExpect(model().attribute("petTypes", Matchers.hasValue(petType.getType())))
        .andExpect(model().attributeExists("petsData"))
        .andExpect(model().attributeExists("petTypes"));
  }

  @Test
  void testCreatePetSuccess() throws Exception {
    PetType petType = new PetType();
    petType.setId(2);
    petType.setType("Dog");
    Pet pet = new Pet();
    pet.setId(new Long(1));
    pet.setName("my pet");
    pet.setMissingSince(LocalDateTime.now());
    pet.setPetType(petType);

    Mockito.when(petRepository.save(Mockito.any())).thenReturn(pet);
    Mockito.when(petTypeRepository.findAll()).thenReturn(Arrays.asList(petType));

    this.mockMvc
        .perform(
            post("/create")
                .param("name", "my pet")
                .param("missingSince", "2022-01-01T13:30")
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("createPet"))
        .andExpect(model().attribute("message", "Data saved successfully!!"))
        .andExpect(model().attributeExists("message"));
  }

  @Test
  void testCreatePetUnAuthorizedAccess() throws Exception {
    this.mockMvc
        .perform(post("/create").param("name", "my pet").param("missingSince", "2022-01-01T13:30"))
        .andExpect(status().isForbidden());
  }
}
