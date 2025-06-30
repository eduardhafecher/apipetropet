package org.serratec.petropet.service;

import org.serratec.petropet.dto.PetRequestDTO;
import org.serratec.petropet.dto.PetResponseDTO;
import org.serratec.petropet.dto.UsuarioResponseDTO;
import org.serratec.petropet.entity.Pet;
import org.serratec.petropet.exceptions.NotFoundException;
import org.serratec.petropet.repository.PetRepository;
import org.serratec.petropet.repository.UsuarioRepository;
import org.springframework.data.domain.Page; // Importe Page
import org.springframework.data.domain.Pageable; // Importe Pageable
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UsuarioRepository usuarioRepository;

    // Injeção de dependência via construtor (preferível ao @Autowired no campo)
    public PetService(PetRepository petRepository, UsuarioRepository usuarioRepository) {
        this.petRepository = petRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PetResponseDTO criarPet(PetRequestDTO dto) {
        Pet pet = new Pet();
        copyDtoToEntity(dto, pet); // Método auxiliar para copiar dados do DTO para a entidade

        // Busca o usuário pelo ID fornecido no DTO
        pet.setUsuario(usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + dto.getUsuarioId())));

        return toResponseDTO(petRepository.save(pet));
    }

    public Page<PetResponseDTO> listarTodosOsPets(Pageable pageable) {
        // O método findAll(Pageable) do JpaRepository já retorna um Page
        return petRepository.findAll(pageable)
                .map(this::toResponseDTO); // Mapeia cada Pet para PetResponseDTO dentro da página
    }

    public PetResponseDTO buscarPetPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado com ID: " + id));
        return toResponseDTO(pet);
    }

    public List<PetResponseDTO> buscarPorEspecie(String especie) {
        return petRepository.findByEspecie(especie).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PetResponseDTO atualizarPet(Long id, PetRequestDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado com ID: " + id));

        copyDtoToEntity(dto, pet); // Reutiliza o método auxiliar para copiar dados

        // Atualiza o usuário, se necessário
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

    /*
     metodo para copiar os campos do PetRequestDTO para a entidade Pet.
     * evita duplicação de código em criarPet e atualizarPet.
     */

    private void copyDtoToEntity(PetRequestDTO dto, Pet pet) {
        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setGenero(dto.getGenero());
        pet.setTag(dto.getTag());
        pet.setBairro(dto.getBairro());
        pet.setFoto(dto.getFoto());
        pet.setComentario(dto.getComentario());
    }
}