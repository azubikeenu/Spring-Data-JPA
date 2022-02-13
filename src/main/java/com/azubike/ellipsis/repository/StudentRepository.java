package com.azubike.ellipsis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByEmail(String email);
}
