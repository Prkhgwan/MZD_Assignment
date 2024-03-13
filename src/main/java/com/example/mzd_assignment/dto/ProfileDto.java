package com.example.mzd_assignment.dto;

import com.example.mzd_assignment.entity.Profile;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProfileDto {
    private Long profileId;
    private Long userId;
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickName;
    @NotBlank(message = "휴대전화번호를 입력해주세요")
    private String phoneNumber;
    @NotBlank(message = "주소를 입력해주세요")
    private String address;
    private boolean default_profile;

    public static ProfileDto createProfileDto(Profile profile) {
        return new ProfileDto(
                profile.getProfileId(),
                profile.getMember().getUserId(),
                profile.getNickName(),
                profile.getPhoneNumber(),
                profile.getAddress(),
                profile.isDefault_profile()
        );
    }

    public static ProfileDto fromEntity(Profile profile) {
        return new ProfileDto(
                profile.getProfileId(),
                profile.getMember().getUserId(),
                profile.getNickName(),
                profile.getPhoneNumber(),
                profile.getAddress(),
                profile.isDefault_profile()
        );
    }
}
