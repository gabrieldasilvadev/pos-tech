package br.com.postech.soat.customer.core.application.dto;


public record CreateCustomerRequest(
    String name,
    String email,
    String cpf,
    String phone
) {
}