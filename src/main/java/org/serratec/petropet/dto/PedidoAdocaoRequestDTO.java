package org.serratec.petropet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoAdocaoRequestDTO {

    @NotNull
    private Long petId;
    @NotNull
    private Long usuarioId;
}
