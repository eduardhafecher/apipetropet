package org.serratec.petropet.service;

import org.serratec.petropet.dto.UsuarioRequestDTO;
import org.serratec.petropet.dto.UsuarioResponseDTO;
import org.serratec.petropet.entity.Usuario;
import org.serratec.petropet.exceptions.EmailException;
import org.serratec.petropet.exceptions.NotFoundException;
import org.serratec.petropet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        // Verifica se o email já existe antes de tentar salvar
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailException("O email " + dto.getEmail() + " já está em uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setEmail(dto.getEmail());
        //  Hashear a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setTelefone(dto.getTelefone());
        usuario.setFoto(dto.getFoto());

        try {
            return toResponseDTO(usuarioRepository.save(usuario));
        } catch (DataIntegrityViolationException e) {
            throw new EmailException("Falha ao criar usuário: email já existe ou outro dado é duplicado.");
        }
    }

    // listar todos os usuários
    public List<UsuarioResponseDTO> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));
        return toResponseDTO(usuario);
    }


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
            return toResponseDTO(usuarioRepository.save(usuario));
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

    //  converte Usuario em UsuarioResponseDTO
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setSobrenome(usuario.getSobrenome());
        dto.setEmail(usuario.getEmail());
        dto.setFoto(usuario.getFoto());
        return dto;
    }


}
