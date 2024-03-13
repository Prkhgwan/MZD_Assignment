package com.example.mzd_assignment.controller;

import com.example.mzd_assignment.dto.MemberDto;
import com.example.mzd_assignment.entity.Member;
import com.example.mzd_assignment.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    // 회원 생성(가입) API
    @PostMapping("/api/signup")
    public ResponseEntity<Member> registMember(@Valid @RequestBody MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            // 검증 오류 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Member registMember = memberService.createMember(memberDto);
        return (registMember != null) ?
                ResponseEntity.status(HttpStatus.OK).body(registMember) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 회원 리스트 출력 API
    // URL패턴(/api/memberlist?page=0&size=[받아올 길이])
    @GetMapping("/api/memberlist")
    public ResponseEntity<Page<MemberDto>> memberList(Pageable pageable) {
        Page<MemberDto> members = memberService.list(pageable);
        return (members != null && !members.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(members) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    // 회원 검색 출력 API
    // URL패턴(/api/memberlist/search?name=[검색어])
    @GetMapping("/api/memberlist/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String name) {
        List<MemberDto> members = memberService.searchByName(name);
        return (members != null && !members.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(members) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    // 회원 상세 출력 API
    @GetMapping("/api/member/{id}")
    public ResponseEntity<Member> detailMember(@PathVariable Long id) {
        Member member = memberService.detailMember(id);
        return (member != null) ?
                ResponseEntity.status(HttpStatus.OK).body(member) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    // 회원 삭제 API
    @DeleteMapping("/api/member/{id}/delete")
    public ResponseEntity<Member> deleteMember(@PathVariable Long id) {
        Member deleteMember = memberService.deleteMember(id);
        return (deleteMember != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
