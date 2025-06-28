package org.serratec.petropet.dto;

import jakarta.persistence.Column;
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
public class UsusarioRequestDTO {

    @NotBlank(message = "Por favor, insira um nome")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Por favor, insira um sobrenome")
    private String sobrenome;

    @NotBlank(message = "Por favor, insira um email")
    @Email(message = "Email inválido")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Por favor, insira uma senha")
    @NotBlank
    private String senha;

    @NotBlank(message = "Por favor, insira um telefone")
    @Pattern(regexp = "\\d{11}", message = "Telefone inválido. Formato valido apenas digitos: xxxxxxxxxx")
    private String telefone;

    private String foto;

}
