package org.serratec.petropet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
public class PedidoAdocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private LocalDate data;

}
