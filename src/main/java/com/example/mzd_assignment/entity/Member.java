package com.example.mzd_assignment.entity;

import com.example.mzd_assignment.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Member")
@ToString(exclude = "profileList")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Profile> profileList = new ArrayList<>();

    public static Member createMember(MemberDto memberDto) {
        Member member = new Member(
                null,
                memberDto.getLoginId(),
                memberDto.getPassword(),
                memberDto.getName(),
                new ArrayList<>()
        );
        return member;
    }
}
