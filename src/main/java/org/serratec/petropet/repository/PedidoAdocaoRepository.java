package org.serratec.petropet.repository;

import org.serratec.petropet.entity.PedidoAdocao;
import org.serratec.petropet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoAdocaoRepository  extends JpaRepository<PedidoAdocao,Long> {

}
