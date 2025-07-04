package org.serratec.petropet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.serratec.petropet.enums.StatusPedidoAdocao;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "pedidos_adocao")
public class PedidoAdocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedidoAdocao status;
}