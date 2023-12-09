package com.backend.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.domain.exception.ClientNotFoundException;
import com.backend.domain.exception.NegocioException;
import com.backend.domain.model.Role;
import com.backend.domain.model.User;
import com.backend.domain.repository.RoleRepository;
import com.backend.domain.repository.UserRepository;

@Service
public class ClientService {

	private static String MSG_CONTA_NAO_ENCONTRADA = "Não existe um cadastro de conta com código %d";

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	public ClientService(UserRepository clientRepository, RoleRepository roleRepository) {
		this.userRepository = clientRepository;
		this.roleRepository = roleRepository;
	}
	
	public User findById(Long id) {
		return buscarOuFalhar(id);
	}

	@Transactional
	public User save(User client) {

		Optional<User> clientOptional = userRepository.findByCpf(client.getCpf());
		
		if (clientOptional.isPresent()) {
			throw new NegocioException("Cpf já cadastrado");
		}

		
		Optional<Role> role = roleRepository.findById(2L);

		// ADD ROLE PADRAO PARA NOVOS USUARIOS
		if (role.isPresent()) {
			client.getRoles().add(role.get());
		}

		return userRepository.save(client);
	}

	@Transactional
	public User disableClient(Long id) {
		User client = buscarOuFalhar(id);
		client.disable();

		return client;
	}

	private User buscarOuFalhar(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(String.format(MSG_CONTA_NAO_ENCONTRADA, id)));
	}
}
