package net.ugurkartal.backend.services.concretes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdManagerTest {

    @InjectMocks
    private IdManager idManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateProductIdReturnsUniqueId() {
        String id1 = idManager.generateProductId();
        String id2 = idManager.generateProductId();

        assertNotEquals(id1, id2);
    }

    @Test
    void generateCategoryIdReturnsUniqueId() {
        String id1 = idManager.generateCategoryId();
        String id2 = idManager.generateCategoryId();

        assertNotEquals(id1, id2);
    }

    @Test
    void generateProductIdReturnsIdWithCorrectPrefix() {
        String id = idManager.generateProductId();

        assertTrue(id.startsWith("PRD-"));
    }

    @Test
    void generateCategoryIdReturnsIdWithCorrectPrefix() {
        String id = idManager.generateCategoryId();

        assertTrue(id.startsWith("CAT-"));
    }
}