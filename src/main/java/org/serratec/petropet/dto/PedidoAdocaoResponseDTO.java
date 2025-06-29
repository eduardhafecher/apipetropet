package org.serratec.petropet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.serratec.petropet.enums.StatusPedidoAdocao;

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
    private StatusPedidoAdocao status;

}