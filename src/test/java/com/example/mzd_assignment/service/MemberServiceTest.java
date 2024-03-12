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
        Member member1 = new Member(null, "loginId1", "password1", "name1", new ArrayList<>());
        Member member2 = new Member(null, "loginId2", "password2", "name2", new ArrayList<>());

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

        assertEquals("TEST", createdMember.getLoginId());  // 로그인 ID가 "TEST"인지 확인
        assertEquals("PASSWORD", createdMember.getPassword());  // 비밀번호가 "PASSWORD"인지 확인
        assertEquals("홍길동", createdMember.getName());  // 이름이 "홍길동"인지 확인
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
    void searchByName() {
        List<MemberDto> members = memberService.searchByName("홍길동");

        // 반환된 결과가 예상대로인지 확인
        assertFalse(members.isEmpty());  // 회원 목록이 비어 있지 않은지 확인
        assertEquals("홍길동", members.get(0).getName());  // 첫 번째 회원의 이름이 "홍길동"인지 확인
    }

    @Test
    void detailMember() {
        Long userId = 1L;

        Member member = memberService.detailMember(userId);

        assertEquals("loginId1", member.getLoginId());  // 로그인 ID가 "loginId1"인지 확인
        assertEquals("password1", member.getPassword());  // 비밀번호가 "password1"인지 확인
        assertEquals("name1", member.getName());  // 이름이 "name1"인지 확인
    }

    @Test
    void detailMemberFail() {
        Long userId = 1L;

        Member member = memberService.detailMember(userId);

        assertEquals("loginId1", member.getLoginId());  // 로그인 ID가 "loginId1"인지 확인
        assertEquals("password1", member.getPassword());  // 비밀번호가 "password1"인지 확인
        assertEquals("name1", member.getName());  // 이름이 "name1"인지 확인
    }

    @Test
    void deleteMember() {
        // setUp 메서드에서 생성된 첫 번째 Member의 ID를 사용
        Long userId = 1L;

        // deleteMember 메서드 호출
        Member deleteMember = memberService.deleteMember(userId);

        // 반환된 결과가 예상대로인지 확인
        assertEquals("loginId1", deleteMember.getLoginId());  // 삭제된 회원의 로그인 ID가 "loginId1"인지 확인
        // 삭제된 회원이 더 이상 존재하지 않는지 확인
        assertNull(memberService.detailMember(userId));
    }
}