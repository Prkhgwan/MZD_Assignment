package com.example.mzd_assignment.service;

import com.example.mzd_assignment.dto.MemberDto;
import com.example.mzd_assignment.dto.ProfileDto;
import com.example.mzd_assignment.entity.Member;
import com.example.mzd_assignment.entity.Profile;
import com.example.mzd_assignment.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member1 = new Member(null, "loginId1", "password1", "이순신", new ArrayList<>());
        Member member2 = new Member(null, "loginId2", "password2", "강감찬", new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            ProfileDto profileDto1 = new ProfileDto(null, null, "테스터" + (i+1), "0101234567" + i, "서울시", i == 0);
            ProfileDto profileDto2 = new ProfileDto(null, null, "테스터" + (i+1), "0101234567" + i, "부산시", i == 0);
            Profile profile1 = Profile.createDefaultProfile(profileDto1, member1);
            Profile profile2 = Profile.createDefaultProfile(profileDto2, member2);
            member1.getProfileList().add(profile1);
            member2.getProfileList().add(profile2);
        }

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void createMember() {
        ProfileDto profileDto = new ProfileDto(null, null, "테스터1", "01012345678", "서울시", true);
        MemberDto memberDto = new MemberDto("TEST", "PASSWORD", "홍길동", profileDto);

        Member createdMember = memberService.createMember(memberDto);

        assertEquals("TEST", createdMember.getLoginId());
        assertEquals("PASSWORD", createdMember.getPassword());
        assertEquals("홍길동", createdMember.getName());
    }

    @Test
    @Transactional
    void list() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<MemberDto> members = memberService.list(pageable);

        assertEquals(2, members.getTotalElements());
        assertEquals("loginId1", members.getContent().get(0).getLoginId());
    }

    @Test
    void searchByNameFail() {
        List<MemberDto> members = memberService.searchByName("홍길동");

        assertTrue(members.isEmpty());
    }

    @Test
    void searchByNameSuccess() {
        List<MemberDto> members = memberService.searchByName("이순신");

        assertFalse(members.isEmpty());
        assertEquals("이순신", members.get(0).getName());
    }

    @Test
    void detailMember() {
        Long userId = 1L;

        Member member = memberService.detailMember(userId);

        assertEquals("loginId1", member.getLoginId());
        assertEquals("password1", member.getPassword());
        assertEquals("이순신", member.getName());
    }

    @Test
    void detailMemberFail() {
        Long userId = 99L;

        Member member = memberService.detailMember(userId);

        assertNull(member);
    }

    @Test
    void deleteMember() {
        Long userId = 1L;

        Member deleteMember = memberService.deleteMember(userId);

        assertEquals("loginId1", deleteMember.getLoginId());
        assertNull(memberService.detailMember(userId));
    }
}