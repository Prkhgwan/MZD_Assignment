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
        for (int i = 0; i <10; i++) {
            Member member = new Member(null, "loginId" + (i + 1), "password" + (i + 1), "Tester" + (i + 1), new ArrayList<>());
            ProfileDto profileDto = new ProfileDto(null, null, "테스터" + (i + 1), "0101234567" + i, "서울시", true);
            Profile profile = Profile.createDefaultProfile(profileDto, member);
            member.getProfileList().add(profile);
            memberRepository.save(member);
        }
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
    void list() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<MemberDto> members = memberService.list(pageable);

        assertEquals(10, members.getTotalElements());
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
        assertEquals("Tester1", member.getName());
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