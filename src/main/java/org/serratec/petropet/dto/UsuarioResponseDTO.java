package org.serratec.petropet.dto;

public record UsuarioResponseDTO(Long id, String nome, String sobrenome,String email, String senha, String telefone, String foto) {
}
