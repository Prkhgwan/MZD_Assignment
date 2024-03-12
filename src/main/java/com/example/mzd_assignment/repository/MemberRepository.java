package com.example.mzd_assignment.repository;

import com.example.mzd_assignment.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m JOIN m.profileList p WHERE p.default_profile = true")
    Page<Member> findAllWithDefaultProfile(Pageable pageable);
    @Query("SELECT m FROM Member m JOIN FETCH m.profileList p WHERE p.default_profile = true AND m.name LIKE %:name%")
    List<Member> findByNameWithDefaultProfile(@Param("name") String name);
}
