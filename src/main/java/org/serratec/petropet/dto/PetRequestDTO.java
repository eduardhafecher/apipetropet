package org.serratec.petropet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDTO {

    @NotBlank (message = "Por favor, insira um nome")
    private String nome;
    @NotBlank (message = "Por favor, insira a especie")
    private String especie;
    @NotBlank (message = "Por favor, insira a raca")
    private String raca;
    @NotBlank (message = "Por favor, insira o gênero")
    private String genero;
    private String tag;
    @NotBlank (message = "Por favor, insira um bairro")
    private String bairro;
    @NotBlank
    private String foto;
    private String comentario;
    @NotNull
    private Long usuarioId;
}
