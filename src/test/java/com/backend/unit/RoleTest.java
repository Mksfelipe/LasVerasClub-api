package com.backend.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.backend.domain.model.Role;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
    }

    @Test
    void testCreateRole() {
        assertNotNull(role);
        assertEquals(1L, role.getId());
        assertEquals("ROLE_USER", role.getName());
    }

    @Test
    void testGetAuthority() {
        assertEquals("ROLE_USER", role.getAuthority());
    }

    @Test
    void testEqualsAndHashCode() {
        Role sameRole = new Role();
        sameRole.setId(1L);
        sameRole.setName("ROLE_USER");

        Role differentRole = new Role();
        differentRole.setId(2L);
        differentRole.setName("ROLE_ADMIN");

        // Test equals method
        assertEquals(role, sameRole);
        assertNotEquals(role, differentRole);

        // Test hashCode method
        assertEquals(role.hashCode(), sameRole.hashCode());
        assertNotEquals(role.hashCode(), differentRole.hashCode());
    }

    @Test
    void testNullFields() {
        Role nullRole = new Role();
        role.setId(null);
        role.setName(null);
        
        // Test equals method with null fields
        assertEquals(role, nullRole);

        assertEquals(role.hashCode(), nullRole.hashCode());
    }
}

