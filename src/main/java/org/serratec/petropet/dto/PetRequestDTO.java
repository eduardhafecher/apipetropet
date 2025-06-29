package org.serratec.petropet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDTO {


    @NotBlank(message = "Por favor, insira um nome")
    public String nome;

    @NotBlank(message = "Por favor, insira a espécie")
    public String especie;

    @NotBlank(message = "Por favor, insira a raça")
    public String raca;

    @NotBlank(message = "Por favor, insira o gênero")
    public String genero;

    @NotBlank(message = "Por favor, insira a tag")
    public String tag;

    @NotBlank(message = "Por favor, insira o bairro")
    public String bairro;

    @NotBlank(message = "Por favor, insira a foto")
    public String foto;

    @NotBlank(message = "Por favor, insira um comentário")
    public String comentario;

    @NotNull(message = "Usuário obrigatório")
    public Long usuarioId;
}
