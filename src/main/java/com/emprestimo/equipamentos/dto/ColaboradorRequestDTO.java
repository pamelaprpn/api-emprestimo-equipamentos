package com.emprestimo.equipamentos.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record ColaboradorRequestDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String cargo
) {

}
