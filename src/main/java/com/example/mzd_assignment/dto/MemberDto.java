package com.example.mzd_assignment.dto;

import com.example.mzd_assignment.entity.Member;
import com.example.mzd_assignment.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {
    private String loginId;
    private String password;
    private String name;
    private ProfileDto profile;

    public static Member createMemberDto(Member member) {
        return new Member(
            member.getUserId(),
            member.getLoginId(),
            member.getPassword(),
            member.getName(),
                member.getProfileList()
        );
    }

    public static MemberDto fromEntity(Member member) {
        Profile defaultProfile = member.getProfileList().stream()
                .filter(Profile::isDefault_profile)
                .findFirst()
                .orElse(null);
        ProfileDto defaultProfileDto = defaultProfile != null ? ProfileDto.fromEntity(defaultProfile) : null;
        return new MemberDto(
                member.getLoginId(),
                member.getPassword(),
                member.getName(),
                defaultProfileDto
        );
    }
}
