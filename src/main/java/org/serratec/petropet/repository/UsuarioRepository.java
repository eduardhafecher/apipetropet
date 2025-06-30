package org.serratec.petropet.repository;

import org.serratec.petropet.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional <Usuario> findByEmail(String email);
    //verificar se um email já existe antes de criar
    boolean existsByEmail(String email);
}
