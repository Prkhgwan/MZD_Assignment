package com.example.mzd_assignment.service;

import com.example.mzd_assignment.dto.MemberDto;
import com.example.mzd_assignment.entity.Member;
import com.example.mzd_assignment.entity.Profile;
import com.example.mzd_assignment.repository.MemberRepository;
import com.example.mzd_assignment.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProfileRepository profileRepository;

    @Transactional
    public Member createMember(MemberDto memberDto) {
        Member member = Member.createMember(memberDto);
        Profile profile = Profile.createDefaultProfile(memberDto.getProfile(), member);
        member.getProfileList().add(profile);
        Member createdMember = memberRepository.save(member);
        return MemberDto.createMemberDto(createdMember);
    }

    public Page<MemberDto> list(Pageable pageable) {
        return memberRepository.findAllWithDefaultProfile(pageable).map(MemberDto::fromEntity);
    }

    public List<MemberDto> searchByName(String name) {
        return memberRepository.findByNameWithDefaultProfile(name).stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Member detailMember(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member deleteMember(Long id) {
        Member target = memberRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        memberRepository.delete(target);
        return target;
    }
}
