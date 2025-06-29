package org.serratec.petropet.service;

import org.serratec.petropet.dto.PetResponseDTO;
import org.serratec.petropet.dto.UsuarioRequestDTO;
import org.serratec.petropet.dto.UsuarioResponseDTO;
import org.serratec.petropet.entity.Pet;
import org.serratec.petropet.entity.Usuario;
import org.serratec.petropet.exceptions.EmailException;
import org.serratec.petropet.exceptions.NotFoundException;
import org.serratec.petropet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailException("O email " + dto.getEmail() + " já está em uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setTelefone(dto.getTelefone());
        usuario.setFoto(dto.getFoto());

        try {
            usuario = usuarioRepository.save(usuario);
            return toResponseDTO(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new EmailException("Falha ao criar usuário: email já existe ou outro dado é duplicado.");
        }
    }

    @Transactional(readOnly = true) // Garante que a sessão fique aberta para carregar os pets
    public List<UsuarioResponseDTO> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true) // Garante que a sessão fique aberta para carregar os pets
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));
        return toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailException("O email " + dto.getEmail() + " já está em uso por outro usuário.");
        }

        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        usuario.setTelefone(dto.getTelefone());
        usuario.setFoto(dto.getFoto());

        try {
            usuario = usuarioRepository.save(usuario);
            return toResponseDTO(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new EmailException("Falha ao atualizar usuário: email já existe ou outro dado é duplicado.");
        }
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // metodo conversor principal para Usuario -> UsuarioResponseDTO
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setSobrenome(usuario.getSobrenome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone()); // CORRIGIDO
        dto.setFoto(usuario.getFoto());

        // Mapeia a lista de entidades Pet para uma lista de PetResponseDTO
        if (usuario.getPets() != null) {
            dto.setPets(usuario.getPets().stream()
                    .map(this::toPetResponseDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setPets(Collections.emptyList());
        }

        return dto;
    }

    // metodo auxiliar para converter Pet -> PetResponseDTO
    private PetResponseDTO toPetResponseDTO(Pet pet) {
        PetResponseDTO petDto = new PetResponseDTO();
        petDto.setId(pet.getId());
        petDto.setNome(pet.getNome());
        petDto.setRaca(pet.getRaca());
        return petDto;
    }
}