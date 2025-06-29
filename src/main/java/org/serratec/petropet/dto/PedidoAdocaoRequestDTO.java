package org.serratec.petropet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoAdocaoRequestDTO {

    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;

    @NotNull(message = "ID do solicitante é obrigatório")
    private Long solicitanteId;

    public Long getUsuarioId() {
        return this.solicitanteId;
    }

}

