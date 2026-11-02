package com.jobportal.jobportal.dto;

public class JobResponseDTO {
  private  Long id;
  private String title;
  private String company;
  private String location;
  private  String description;

    public JobResponseDTO(Long id, String title, String company, String location, String description) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
