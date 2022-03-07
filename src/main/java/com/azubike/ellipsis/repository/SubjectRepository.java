package com.azubike.ellipsis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.azubike.ellipsis.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
