package com.backend.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.api.model.UserModel;
import com.backend.domain.model.User;

@Component
public class ClientModelAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public UserModel toModel(User user) {
		return mapper.map(user, UserModel.class);
	}
	
	public List<UserModel> toCollect(List<User> clients) {
		return clients.stream().map(this::toModel).collect(Collectors.toList());
	}
}
