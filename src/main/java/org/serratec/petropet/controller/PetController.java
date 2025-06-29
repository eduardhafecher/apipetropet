package org.serratec.petropet.controller;

import jakarta.validation.Valid;
import org.serratec.petropet.dto.PetRequestDTO;
import org.serratec.petropet.dto.PetResponseDTO;
import org.serratec.petropet.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<PetResponseDTO> criarPet(@Valid @RequestBody PetRequestDTO petRequestDTO) {
        PetResponseDTO novoPet = petService.criarPet(petRequestDTO);
        return new ResponseEntity<>(novoPet, HttpStatus.CREATED);
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<PetResponseDTO>> buscarPorEspecie(@PathVariable String especie) {
        List<PetResponseDTO> pets = petService.buscarPorEspecie(especie);
        if (pets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 se a lista estiver vazia
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content para deleção bem-sucedida
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listarTodosPets() {
        List<PetResponseDTO> pets = petService.listarTodosOsPets();
        if (pets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> buscarPetPorId(@PathVariable Long id) {
        PetResponseDTO pet = petService.buscarPetPorId(id);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }
}