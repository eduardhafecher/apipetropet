package org.serratec.petropet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.serratec.petropet.dto.UsuarioResponseDTO;

@Data // Anotação gera Getters, Setters, toString, equals e hashCode
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