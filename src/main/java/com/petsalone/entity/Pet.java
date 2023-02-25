package com.petsalone.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pets")
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotNull(message = "Pet name could not be null or blank")
  @NotBlank(message = "Pet name could not be null or blank")
  @Column(name = "name")
  private String name;

  @NotNull(message = "Missing since could not be null")
  @Column(name = "missing_since")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime missingSince;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "pet_type", referencedColumnName = "id")
  private PetType petType;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getMissingSince() {
    return missingSince;
  }

  public PetType getPetType() {
    return petType;
  }

  public void setPetType(PetType petType) {
    this.petType = petType;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMissingSince(LocalDateTime missingSince) {
    this.missingSince = missingSince;
  }

  @Override
  public String toString() {
    return "Pet{"
        + "name='"
        + name
        + '\''
        + ", missingSince="
        + missingSince
        + ", petType="
        + petType
        + '}';
  }
}
