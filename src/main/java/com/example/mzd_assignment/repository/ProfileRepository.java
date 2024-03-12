package com.example.mzd_assignment.repository;

import com.example.mzd_assignment.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
