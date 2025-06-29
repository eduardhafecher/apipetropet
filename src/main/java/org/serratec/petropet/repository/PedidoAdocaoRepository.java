package org.serratec.petropet.repository;

import org.serratec.petropet.entity.PedidoAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoAdocaoRepository extends JpaRepository<PedidoAdocao, Long> {
    List<PedidoAdocao> findBySolicitanteId(Long solicitanteId);
    List<PedidoAdocao> findByPetId(Long petId);
}
