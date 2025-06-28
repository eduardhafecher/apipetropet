package org.serratec.petropet.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetResponseDTO {
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String genero;
    private String tag;
    private String bairro;
    private String foto;
    private String comentario;
    private UsuarioResponseDTO usuario;
}
