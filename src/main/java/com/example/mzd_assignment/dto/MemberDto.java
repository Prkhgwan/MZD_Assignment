package com.example.mzd_assignment.dto;

import com.example.mzd_assignment.entity.Member;
import com.example.mzd_assignment.entity.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {
    @NotBlank(message = "아이디를 입력해 주세요")
    @Size(min = 4, max = 16, message = "아이디는 4자 이상 16자 이하로 입력해 주세요")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 입력해 주세요.")
    private String password;
    @NotBlank(message = "성명을 입력해 주세요")
    @Pattern(regexp = "^[가-힣]*$", message = "이름은 한글만 가능합니다")
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
