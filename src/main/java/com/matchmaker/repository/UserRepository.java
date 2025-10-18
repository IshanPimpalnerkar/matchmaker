package com.matchmaker.repository;

import com.matchmaker.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findByGender(String gender);
    List<UserProfile> findByAgeBetween(int minAge, int maxAge);
    List<UserProfile> findByCity(String city);
    List<UserProfile> findByReligion(String religion);
    List<UserProfile> findByCaste(String caste);
}
