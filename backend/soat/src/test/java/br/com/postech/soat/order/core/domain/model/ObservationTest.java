package br.com.postech.soat.order.core.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Observation Tests")
class ObservationTest {

    @Test
    @DisplayName("Should create an observation with text")
    void shouldCreateObservationWithText() {
        // Arrange
        String text = "No onions please";

        // Act
        Observation observation = new Observation(text);

        // Assert
        assertNotNull(observation);
        assertEquals(text, observation.getText());
    }

    @Test
    @DisplayName("Should create an observation with empty text")
    void shouldCreateObservationWithEmptyText() {
        // Arrange
        String text = "";

        // Act
        Observation observation = new Observation(text);

        // Assert
        assertNotNull(observation);
        assertEquals(text, observation.getText());
    }

    @Test
    @DisplayName("Should create an observation with null text")
    void shouldCreateObservationWithNullText() {
        // Act
        Observation observation = new Observation(null);

        // Assert
        assertNotNull(observation);
        assertNull(observation.getText());
    }
}