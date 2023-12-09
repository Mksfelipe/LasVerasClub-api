package com.backend.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.api.model.input.UserInputModel;
import com.backend.domain.model.User;


@Component
public class UserInputDisassembler {

	@Autowired
	private ModelMapper mapper;
	
	public User toDomainObject(UserInputModel userInputModel) {
		return mapper.map(userInputModel, User.class);
	}
	
}
