package com.backend.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.domain.exception.ClientNotFoundException;
import com.backend.domain.exception.NegocioException;
import com.backend.domain.model.Client;
import com.backend.domain.model.Role;
import com.backend.domain.repository.ClientRepository;
import com.backend.domain.repository.RoleRepository;

@Service
public class ClientService {

	private static String MSG_CONTA_NAO_ENCONTRADA = "Não existe um cadastro de conta com código %d";

	private final ClientRepository clientRepository;

	private final RoleRepository roleRepository;

	public ClientService(ClientRepository clientRepository, RoleRepository roleRepository) {
		this.clientRepository = clientRepository;
		this.roleRepository = roleRepository;
	}
	
	public Client findById(Long id) {
		return buscarOuFalhar(id);
	}

	@Transactional
	public Client save(Client client) {

		Optional<Client> clientOptional = clientRepository.findByCpf(client.getCpf());
		Optional<Client> clientOptionalEmail = clientRepository.findByEmail(client.getEmail());
		
		if (clientOptional.isPresent()) {
			throw new NegocioException("Cpf já cadastrado");
		}
		
		if (clientOptionalEmail.isPresent()) {
			throw new NegocioException("Email já cadastrado");
		}

		
		Optional<Role> role = roleRepository.findById(2L);

		// ADD ROLE PADRAO PARA NOVOS USUARIOS
		if (role.isPresent()) {
			client.getRoles().add(role.get());
		}

		return clientRepository.save(client);
	}

	@Transactional
	public Client disableClient(Long id) {
		Client client = buscarOuFalhar(id);
		client.disable();

		return client;
	}

	private Client buscarOuFalhar(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(String.format(MSG_CONTA_NAO_ENCONTRADA, id)));
	}
}
