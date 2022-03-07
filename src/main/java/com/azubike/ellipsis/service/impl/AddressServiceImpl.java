package com.azubike.ellipsis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azubike.ellipsis.entity.Address;
import com.azubike.ellipsis.exceptions.AddressServiceException;
import com.azubike.ellipsis.model.response.ErrorMessages;
import com.azubike.ellipsis.repository.AddressRepository;
import com.azubike.ellipsis.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository repository;

	@Override
	public Address findById(long id) {
		return repository.findById(id)
				.orElseThrow(() -> new AddressServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages(),
						HttpStatus.NOT_FOUND.value()));
	}

}
