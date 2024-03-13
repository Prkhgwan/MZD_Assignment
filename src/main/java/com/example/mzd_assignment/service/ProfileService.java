package com.example.mzd_assignment.service;

import com.example.mzd_assignment.dto.ProfileDto;
import com.example.mzd_assignment.entity.Member;
import com.example.mzd_assignment.entity.Profile;
import com.example.mzd_assignment.repository.MemberRepository;
import com.example.mzd_assignment.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public ProfileDto createProfile(Long id, ProfileDto profileDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 계정 없음"));
        Profile profile = Profile.createProfile(profileDto, member);
        Profile created = profileRepository.save(profile);
        return ProfileDto.createProfileDto(created);
    }

    @Transactional
    public ProfileDto updateProfile(Long id, ProfileDto profileDto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필 없음"));
        profile.patch(profileDto);
        Profile updated = profileRepository.save(profile);
        return ProfileDto.createProfileDto(updated);
    }

    @Transactional
    public ProfileDto deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필 없음"));
        if (profile.isDefault_profile() == true) {
            throw new IllegalArgumentException("기본 프로필은 삭제할 수 없습니다.");
        }
        profileRepository.delete(profile);
        return ProfileDto.createProfileDto(profile);
    }
}
