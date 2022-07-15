package com.courses.courses.entities;

public class Course {
    private int Id;
    private String Name;
    private String Description;
    private String UrlResource;
    private int DurationTime;

    public Course() {}

    public Course(int id, String name, String description, String urlResource, int durationTime) {
        Id = id;
        Name = name;
        Description = description;
        UrlResource = urlResource;
        DurationTime = durationTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrlResource() {
        return UrlResource;
    }

    public void setUrlResource(String urlResource) {
        UrlResource = urlResource;
    }

    public int getDurationTime() {
        return DurationTime;
    }

    public void setDurationTime(int durationTime) {
        DurationTime = durationTime;
    }
}
