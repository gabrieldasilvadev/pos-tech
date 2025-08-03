package br.com.postech.soat.commons.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pagination Tests")
class PaginationTest {

    @Test
    @DisplayName("Should create pagination successfully when valid parameters are provided")
    void givenValidParameters_whenCreatePagination_thenReturnPagination() {
        // Arrange & Act
        Pagination pagination = new Pagination(0, 10);

        // Assert
        assertEquals(0, pagination.page());
        assertEquals(10, pagination.size());
        assertEquals(0, pagination.getOffset());
    }

    @Test
    @DisplayName("Should calculate offset correctly when page and size are provided")
    void givenPageAndSize_whenGetOffset_thenReturnCorrectOffset() {
        // Arrange & Act
        Pagination pagination1 = new Pagination(0, 10);
        Pagination pagination2 = new Pagination(1, 10);
        Pagination pagination3 = new Pagination(2, 5);

        // Assert
        assertEquals(0, pagination1.getOffset());
        assertEquals(10, pagination2.getOffset());
        assertEquals(10, pagination3.getOffset());
    }

    @Test
    @DisplayName("Should throw exception when page is negative")
    void givenNegativePage_whenCreatePagination_thenThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Pagination(-1, 10)
        );
        assertEquals("Page must be >= 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when size is zero")
    void givenZeroSize_whenCreatePagination_thenThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Pagination(0, 0)
        );
        assertEquals("Size must be > 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when size is negative")
    void givenNegativeSize_whenCreatePagination_thenThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Pagination(0, -1)
        );
        assertEquals("Size must be > 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should create pagination with minimum valid values")
    void givenMinimumValidValues_whenCreatePagination_thenReturnPagination() {
        // Arrange & Act
        Pagination pagination = new Pagination(0, 1);

        // Assert
        assertEquals(0, pagination.page());
        assertEquals(1, pagination.size());
        assertEquals(0, pagination.getOffset());
    }
}