package org.serratec.petropet.services;

import org.serratec.petropet.dto.PetRequestDTO;
import org.serratec.petropet.dto.PetResponseDTO;
import org.serratec.petropet.dto.UsuarioResponseDTO;
import org.serratec.petropet.entity.Pet;
import org.serratec.petropet.exceptions.NotFoundException;
import org.serratec.petropet.repository.PetRepository;
import org.serratec.petropet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PetResponseDTO criarPet(PetRequestDTO dto) {
        Pet pet = new Pet();

        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setGenero(dto.getGenero());
        pet.setTag(dto.getTag());
        pet.setBairro(dto.getBairro());
        pet.setFoto(dto.getFoto());
        pet.setComentario(dto.getComentario());
        // Busca o usuário pelo ID fornecido no DTO
        pet.setUsuario(usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + dto.getUsuarioId())));
        return toResponseDTO(petRepository.save(pet));
    }

    // listar todos os pets
    public List<PetResponseDTO> listarTodosOsPets() {
        return petRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // buscar um pet por ID e retornar o DTO
    public PetResponseDTO buscarPetPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado com ID: " + id));
        return toResponseDTO(pet);
    }

    public List<PetResponseDTO> buscarPorEspecie(String especie) {
        return petRepository.findByEspecie(especie)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList()); // Usar Collectors.toList() é mais explícito
    }

    public PetResponseDTO atualizarPet(Long id, PetRequestDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado com ID: " + id));

        // Atualiza os campos do pet com base nos dados do DTO
        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setGenero(dto.getGenero());
        pet.setTag(dto.getTag());
        pet.setBairro(dto.getBairro());
        pet.setFoto(dto.getFoto());
        pet.setComentario(dto.getComentario());
        pet.setUsuario(usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + dto.getUsuarioId())));

        return toResponseDTO(petRepository.save(pet));
    }

    public void deletarPet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet não encontrado com ID: " + id);
        }
        petRepository.deleteById(id);
    }

    // converte o Pet em PetResponseDTO
    private PetResponseDTO toResponseDTO(Pet pet) {
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setNome(pet.getNome());
        dto.setEspecie(pet.getEspecie());
        dto.setRaca(pet.getRaca());
        dto.setGenero(pet.getGenero());
        dto.setTag(pet.getTag());
        dto.setBairro(pet.getBairro());
        dto.setFoto(pet.getFoto());
        dto.setComentario(pet.getComentario());

        if (pet.getUsuario() != null) {
            UsuarioResponseDTO usuarioDto = new UsuarioResponseDTO();
            usuarioDto.setId(pet.getUsuario().getId());
            usuarioDto.setNome(pet.getUsuario().getNome());
            dto.setUsuario(usuarioDto);
        }
        return dto;
    }




}
