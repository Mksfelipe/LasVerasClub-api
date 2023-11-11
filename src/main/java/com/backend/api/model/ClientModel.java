package com.backend.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientModel {

	private Long id;
	private String firstName;
	private String lastName;
	private String cpf;
	private String email;
	private Boolean enable = true;
}
