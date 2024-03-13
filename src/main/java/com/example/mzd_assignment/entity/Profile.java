package com.example.mzd_assignment.entity;

import com.example.mzd_assignment.dto.ProfileDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROFILE")
@ToString(exclude = "member")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Long profileId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private Member member;

    @Column(name = "PROFILE_NICKNAME", nullable = false)
    private String nickName;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DEFAULT_PROFILE")
    private boolean default_profile;

    public Profile(Member member, String nickName, String phoneNumber, String address, boolean default_profile) {
        this.member = member;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.default_profile = default_profile;
    }

    public static Profile createDefaultProfile(ProfileDto profile, Member member) {
        return new Profile(
                member,
                profile.getNickName(),
                profile.getPhoneNumber(),
                profile.getAddress(),
                true
        );
    }

    public static Profile createProfile(ProfileDto profileDto, Member member) {
        if (profileDto.getProfileId() != null)
            throw new IllegalArgumentException("프로필 ID가 존재함.");
        return new Profile(
                null,
                member,
                profileDto.getNickName(),
                profileDto.getPhoneNumber(),
                profileDto.getAddress(),
                false
        );
    }

    public void patch(ProfileDto profileDto) {
        if (this.profileId != profileDto.getProfileId())
            throw new IllegalArgumentException("프로필 ID가 일치하지 않음.");
        if (this.getNickName() != null)
            this.nickName = profileDto.getNickName();
        if (this.getPhoneNumber() != null)
            this.phoneNumber = profileDto.getPhoneNumber();
        if (this.getAddress() != null)
            this.address = profileDto.getAddress();
    }
}
