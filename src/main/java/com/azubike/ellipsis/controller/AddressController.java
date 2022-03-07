package com.azubike.ellipsis.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.entity.Address;
import com.azubike.ellipsis.model.response.AddressResponse;
import com.azubike.ellipsis.service.AddressService;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
	@Autowired
	private AddressService addressService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressResponse> getAddress(@PathVariable(value = "id", required = true) long id) {
		Address address = addressService.findById(id);
		AddressResponse returnedValue = new ModelMapper().map(address, AddressResponse.class);
		return ResponseEntity.ok(returnedValue);
	}

}
