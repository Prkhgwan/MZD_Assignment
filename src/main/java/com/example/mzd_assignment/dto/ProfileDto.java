package com.example.mzd_assignment.dto;

import com.example.mzd_assignment.entity.Profile;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProfileDto {
    private Long profileId;
    private Long userId;
    private String nickName;
    private String phoneNumber;
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
