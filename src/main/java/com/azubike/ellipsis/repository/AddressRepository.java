package com.azubike.ellipsis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
