package org.serratec.petropet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoAdocaoResponseDTO {

    private Long id;
    private PetResponseDTO pet;
    private UsuarioResponseDTO solicitante;
    private LocalDateTime dataPedido;
    private String status;
}

