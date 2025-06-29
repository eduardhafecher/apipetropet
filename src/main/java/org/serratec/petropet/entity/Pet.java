package org.serratec.petropet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String genero;
    private String tag;
    private String bairro;
    private String foto;
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "usuario_id")
    private Usuario usuario;
}


