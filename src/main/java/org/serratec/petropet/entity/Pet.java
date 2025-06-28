package org.serratec.petropet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
public class Pet {

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
