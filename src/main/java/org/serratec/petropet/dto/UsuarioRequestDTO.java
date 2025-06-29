package org.serratec.petropet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "Por favor, insira um nome")
    private String nome;

    @NotBlank(message = "Por favor, insira um sobrenome")
    private String sobrenome;

    @Email(message = "Email inválido")
    @NotBlank(message = "Por favor, insira um email")
    private String email;

    @NotBlank(message = "Por favor, insira uma senha")
    private String senha;

    @NotBlank(message = "Por favor, insira um telefone")
    @Pattern(regexp = "\\d{11}", message = "Telefone inválido. Formato válido: apenas dígitos (ex: 21999999999)")
    private String telefone;

    @NotBlank(message = "Por favor, insira uma foto")
    private String foto;
}
