package org.serratec.petropet.controller;

import jakarta.validation.Valid;
import org.serratec.petropet.dto.PetRequestDTO;
import org.serratec.petropet.dto.PetResponseDTO;
import org.serratec.petropet.service.PetService; // Corrigido para .services
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> criarPet(@Valid @RequestBody PetRequestDTO petRequestDTO) {
        PetResponseDTO novoPet = petService.criarPet(petRequestDTO);
        return new ResponseEntity<>(novoPet, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PetResponseDTO>> listarTodosPets(Pageable pageable) {
        Page<PetResponseDTO> petsPage = petService.listarTodosOsPets(pageable);
        if (petsPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(petsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> buscarPetPorId(@PathVariable Long id) {
        PetResponseDTO pet = petService.buscarPetPorId(id);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<PetResponseDTO>> buscarPorEspecie(@PathVariable String especie) {
        //adicionar pageable aqui tb
        List<PetResponseDTO> pets = petService.buscarPorEspecie(especie);
        if (pets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> atualizarPet(@PathVariable Long id, @Valid @RequestBody PetRequestDTO petRequestDTO) {
        PetResponseDTO petAtualizado = petService.atualizarPet(id, petRequestDTO);
        return new ResponseEntity<>(petAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPet(@PathVariable Long id) {
        petService.deletarPet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}