package com.petsalone.servingwebcontent;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.petsalone.entity.PetType;
import com.petsalone.repository.PetTypeRepository;
import java.util.Arrays;
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
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PetTypeRepository petTypeRepository;

  @Test
  void testLoginSuccess() throws Exception {
    PetType petType = new PetType();
    petType.setId(2);
    petType.setType("Dog");

    Mockito.when(petTypeRepository.findAll()).thenReturn(Arrays.asList(petType));

    this.mockMvc
        .perform(get("/users/loginsuccess"))
        .andExpect(status().isOk())
        .andExpect(view().name("createPet"))
        .andExpect(model().attribute("petTypes", Matchers.hasValue(petType.getType())))
        .andExpect(model().attributeExists("petTypes"))
        .andExpect(model().attributeExists("pet"));
  }

  @Test
  void testUserLoginSuccess() throws Exception {
    this.mockMvc
        .perform(get("/users/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("userLogin"));
  }
}
