package com.petsalone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pet_types")
public class PetType {

  @Id private Integer id;

  @Column private String type;

  public Integer getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "PetType{" + "id=" + id + ", type='" + type + '\'' + '}';
  }
}
