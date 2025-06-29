package org.serratec.petropet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;

    @Column(unique = true)
    private String email;
    private String senha;
    private String telefone;
    private String foto;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Pet> pets;
}
