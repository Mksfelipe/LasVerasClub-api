package com.backend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.backend.domain.model.Client;
import com.backend.domain.model.Role;
import com.backend.domain.repository.ClientRepository;
import com.backend.domain.repository.RoleRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientIntegrationTest {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	void testClientPersistence() {
		// Criar uma instância de Role
		Role role = new Role();
		role.setName("ROLE_USER");
		roleRepository.save(role);

		// Criar uma instância de Client
		Client client = new Client();
		client.setFirstName("John");
		client.setLastName("Doe");
		client.setCpf("07782169348");
		client.setPassword("password");
		client.setEmail("john.doe@example.com");

		// Adicionar a role ao cliente
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		client.setRoles(roles);

		// Salvar o cliente no banco de dados
		clientRepository.save(client);

		// Buscar o cliente do banco de dados
		Client savedClient = clientRepository.findById(client.getId()).orElse(null);

		// Verificar se o cliente foi salvo corretamente
		assertNotNull(savedClient);
		assertEquals("JOHN", savedClient.getFirstName());
		assertEquals("DOE", savedClient.getLastName());
		assertEquals("07782169348", savedClient.getCpf());
		assertEquals("password", savedClient.getPassword());
		assertEquals("john.doe@example.com", savedClient.getEmail());

		// Verificar se a role está associada ao cliente
		assertEquals(1, savedClient.getRoles().size());
		Role savedRole = savedClient.getRoles().iterator().next();
		assertEquals("ROLE_USER", savedRole.getName());
	}
	
	@Test
    void testUpdateClient() {
        // Cria uma instância de Role
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        // Cria uma instância de Client
        Client client = new Client();
        client.setFirstName("JOHN");
        client.setLastName("DOE");
        client.setCpf("07782169348");
        client.setPassword("password");
        client.setEmail("john.doe@example.com");

        // Adiciona a role ao cliente
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        client.setRoles(roles);

        // Salva o cliente no banco de dados
        clientRepository.save(client);

        // Atualiza o nome do cliente
        String newName = "UPDATENAME";
        client.setFirstName(newName);
        clientRepository.save(client);

        // Busca o cliente atualizado do banco de dados
        Optional<Client> updatedClientOptional = clientRepository.findById(client.getId());

        // Verifica se o cliente foi encontrado
        assertTrue(updatedClientOptional.isPresent());

        // Verifica se o nome do cliente foi atualizado corretamente
        Client updatedClient = updatedClientOptional.get();
        assertEquals(newName, updatedClient.getFirstName());
    }
}
