package com.example.mzd_assignment.service;

import com.example.mzd_assignment.dto.MemberDto;
import com.example.mzd_assignment.dto.ProfileDto;
import com.example.mzd_assignment.entity.Member;
import com.example.mzd_assignment.entity.Profile;
import com.example.mzd_assignment.repository.MemberRepository;
import com.example.mzd_assignment.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceTest {
    @Autowired
    ProfileService profileService;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        // 테스트에 필요한 데이터 설정
        Member member1 = new Member(null, "loginId1", "password1", "name1", new ArrayList<>());
        memberRepository.save(member1);
        ProfileDto defaultProfileDto = new ProfileDto(null, member1.getUserId(), "테스터1", "01012345678", "서울특별시", true);
        Profile defaultProfile = Profile.createDefaultProfile(defaultProfileDto, member1);
        member1.getProfileList().add(defaultProfile);
        profileRepository.save(defaultProfile);
        ProfileDto additionalProfileDto = new ProfileDto(null, member1.getUserId(), "추가프로필닉네임", "01012345678", "대구광역시", false);
        Profile additionalProfile = Profile.createProfile(additionalProfileDto, member1);
        member1.getProfileList().add(additionalProfile);
        profileRepository.save(additionalProfile);
    }

    @Test
    void createProfile() {
        Long memberId = 1L;
        ProfileDto profileDto = new ProfileDto(null, memberId, "테스트하는테스트코드", "01077779999", "부산광역시", false);
        ProfileDto createdProfile = profileService.createProfile(1L, profileDto);

        assertEquals("테스트하는테스트코드", createdProfile.getNickName());
        assertEquals("01077779999", createdProfile.getPhoneNumber());
        assertEquals("부산광역시", createdProfile.getAddress());
    }

    @Test
    void updateProfile() {
        Long profileId = 1L;
        ProfileDto profileDto = new ProfileDto(profileId, null, "수정된닉네임", "01011110000", "대전광역시", true);
        ProfileDto updatedProfile = profileService.updateProfile(1L, profileDto);

        assertEquals("수정된닉네임", updatedProfile.getNickName());
        assertEquals("01011110000", updatedProfile.getPhoneNumber());
        assertEquals("대전광역시", updatedProfile.getAddress());
    }

    @Test
    void deleteProfile() {
        Long profileId = 2L;

        ProfileDto deletedProfile = profileService.deleteProfile(profileId);
        assertEquals("추가프로필닉네임", deletedProfile.getNickName());
        assertEquals("01012345678", deletedProfile.getPhoneNumber());
        assertEquals("대구광역시", deletedProfile.getAddress());
        assertFalse(deletedProfile.isDefault_profile());
        assertNull(profileRepository.findById(profileId).orElse(null));
    }

    @Test
    void deleteProfile_fail() {
        Long profileId = 1L;
        assertThrows(IllegalArgumentException.class, () -> {
            profileService.deleteProfile(profileId);
        });
    }
}