package org.serratec.petropet.repository;

import org.serratec.petropet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findByEspecie(String especie);


}
