package br.com.postech.soat.order.core.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Observation Tests")
class ObservationTest {

    @Test
    @DisplayName("Should create an observation with text")
    void shouldCreateObservationWithText() {
        String text = "No onions please";

        Observation observation = new Observation(text);

        assertNotNull(observation);
        assertEquals(text, observation.getText());
    }

    @Test
    @DisplayName("Should create an observation with empty text")
    void shouldCreateObservationWithEmptyText() {
        String text = "";

        Observation observation = new Observation(text);

        assertNotNull(observation);
        assertEquals(text, observation.getText());
    }

    @Test
    @DisplayName("Should create an observation with null text")
    void shouldCreateObservationWithNullText() {
        Observation observation = new Observation(null);

        assertNotNull(observation);
        assertNull(observation.getText());
    }
}