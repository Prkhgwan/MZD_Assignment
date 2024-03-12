package com.example.mzd_assignment.controller;

import com.example.mzd_assignment.dto.ProfileDto;
import com.example.mzd_assignment.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    // 프로필 생성
    @PostMapping("/api/member/{id}/createprofile")
    public ResponseEntity<ProfileDto> createProfile(@PathVariable Long id, @RequestBody ProfileDto profileDto) {
        ProfileDto createProfileDto = profileService.createProfile(id, profileDto);
        return ResponseEntity.status(HttpStatus.OK).body(createProfileDto);
    }
    // 프로필 수정
    @PatchMapping("/api/profile/{id}/update")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable Long id, @RequestBody ProfileDto profileDto) {
        ProfileDto updateProfileDto = profileService.updateProfile(id, profileDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateProfileDto);
    }
    // 프로필 삭제
    @DeleteMapping("/api/profile/{id}/delete")
    public ResponseEntity<ProfileDto> deleteProfile(@PathVariable Long id) {
        ProfileDto deleteProfileDto = profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteProfileDto);
    }
}
