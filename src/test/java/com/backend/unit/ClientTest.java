package com.backend.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.backend.domain.model.Client;
import com.backend.domain.model.Role;

class ClientTest {

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
    }

    @Test
    void testDisable() {
        // Initially, the client should be enabled
        assertTrue(client.getEnable());

        // Disable the client
        client.disable();

        // After disabling, the client should be disabled
        assertFalse(client.getEnable());
    }

    @Test
    void testEnable() {
        // Initially, the client should be enabled
        assertTrue(client.getEnable());

        // Disable and then enable the client
        client.disable();
        client.enable();

        // After enabling, the client should be enabled again
        assertTrue(client.getEnable());
    }

    @Test
    void testSetFirstName() {
        // Set the first name to a lowercase value
        client.setFirstName("john");

        // The first name should be converted to uppercase
        assertEquals("JOHN", client.getFirstName());
    }

    @Test
    void testSetLastName() {
        // Set the last name to a lowercase value
        client.setLastName("doe");

        // The last name should be converted to uppercase
        assertEquals("DOE", client.getLastName());
    }

    @Test
    void testAddRole() {
        // Create a role
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        // Add the role to the client
        client.getRoles().add(role);

        // The client should have the added role
        assertTrue(client.getRoles().contains(role));
    }
    
    @Test
    void testAddAndRemoveRole() {
        // Create roles
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("ROLE_ADMIN");

        // Add roles to the client
        client.getRoles().add(role1);
        client.getRoles().add(role2);

        // Ensure roles are added
        assertTrue(client.getRoles().contains(role1));
        assertTrue(client.getRoles().contains(role2));

        // Remove one role
        client.getRoles().remove(role1);

        // Ensure the removed role is no longer present
        assertFalse(client.getRoles().contains(role1));
        assertTrue(client.getRoles().contains(role2));
    }
    @Test
    void testEnableDefault() {
        // By default, enable should be true
        assertEquals(true, client.getEnable());
    }

    @Test
    void testEnableConversionTrue() {
        // Set enable as true (converted from BooleanConverter)
        client.enable();
        assertEquals(true, client.getEnable());
    }

    @Test
    void testEnableConversionFalse() {
        // Set enable as false (converted from BooleanConverter)
        client.disable();
        assertEquals(false, client.getEnable());
    }

}
