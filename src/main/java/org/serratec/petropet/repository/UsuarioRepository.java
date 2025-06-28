package org.serratec.petropet.repository;

import org.serratec.petropet.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


}
