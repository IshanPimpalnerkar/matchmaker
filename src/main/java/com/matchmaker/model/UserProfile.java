package com.matchmaker.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;
    private String city;
    private String religion;
    private String caste;
    private String email;
    private String phone;

    @ElementCollection
    @CollectionTable(name = "user_images", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "image_path")
    private List<String> imagePaths = new ArrayList<>();

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }

    public String getCaste() { return caste; }
    public void setCaste(String caste) { this.caste = caste; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<String> getImagePaths() { return imagePaths; }
    public void setImagePaths(List<String> imagePaths) { this.imagePaths = imagePaths; }
}
