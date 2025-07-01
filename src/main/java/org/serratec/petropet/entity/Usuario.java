package org.serratec.petropet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections; // Importe Collections para retornar uma lista vazia
import java.util.List;

@Data
@Entity
@Getter
@Setter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Roni diz que no caso de não usar perfis, pode retornar uma coleção vazia
      // return Collections.emptyList();
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

    }

}