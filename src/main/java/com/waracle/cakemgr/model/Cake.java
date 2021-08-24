package com.waracle.cakemgr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author bianca.findlay, created on 23/08/2021.
 */
@Entity
public class Cake {

  @Id
  @GeneratedValue
  private Long id;
  private String title;
  private String desc;
  private String image;

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}