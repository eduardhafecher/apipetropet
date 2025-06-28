package org.serratec.petropet.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoAdocaoResponseDTO {
    private Long id;
    private PetResponseDTO pet;
    private UsuarioResponseDTO usuario;
    private LocalDateTime data;
}
