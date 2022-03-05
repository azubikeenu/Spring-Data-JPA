package com.azubike.ellipsis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByEmail(String email);

	@Query(value = "select * from Student s where s.first_name= :firstName", nativeQuery = true)
	List<Student> findStudentByFirstName(@Param("firstName") String firstName);

	Student findByFirstNameAndLastName(String firstName, String lastName);

	List<Student> findByFirstNameOrLastName(String firstName, String lastName);

//---------Using Native Queries for IN QUERIES  --------------//
//	@Query(value = "select * from Student s where s.first_name in :inFirstName", nativeQuery = true)
//	List<Student> findByFirstNameIn(@Param("inFirstName") List<String> inFirstName);

	List<Student> findByFirstNameIn(List<String> firstNames);

}
