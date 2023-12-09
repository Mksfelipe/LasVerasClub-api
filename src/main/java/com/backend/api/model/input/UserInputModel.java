package com.backend.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputModel {

	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String cpf;
	private Boolean enable;
	
}