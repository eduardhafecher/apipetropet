package org.serratec.petropet.service;

import org.serratec.petropet.dto.PedidoAdocaoRequestDTO;
import org.serratec.petropet.dto.PedidoAdocaoResponseDTO;
import org.serratec.petropet.dto.PetResponseDTO;
import org.serratec.petropet.dto.UsuarioResponseDTO;
import org.serratec.petropet.entity.PedidoAdocao;
import org.serratec.petropet.entity.Pet;
import org.serratec.petropet.entity.Usuario;
import org.serratec.petropet.enums.StatusPedidoAdocao;
import org.serratec.petropet.exceptions.NotFoundException;
import org.serratec.petropet.repository.PedidoAdocaoRepository;
import org.serratec.petropet.repository.PetRepository;
import org.serratec.petropet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoAdocaoService {

    @Autowired
    private PedidoAdocaoRepository pedidoAdocaoRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // metodos de Conversão para DTOs
    private PetResponseDTO toPetResponseDTO(Pet pet) {
        if (pet == null) return null;
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
        if (pet.getUsuario() != null) { dto.setUsuario(toUsuarioResponseDTO(pet.getUsuario())); }
        return dto;
    }

    private UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        return dto;
    }


    public PedidoAdocaoResponseDTO criarPedido(PedidoAdocaoRequestDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new NotFoundException("Pet não encontrado com ID: " + dto.getPetId()));

        Usuario solicitante = usuarioRepository.findById(dto.getSolicitanteId())
                .orElseThrow(() -> new NotFoundException("Usuário solicitante não encontrado com ID: " + dto.getSolicitanteId()));

        PedidoAdocao pedido = new PedidoAdocao();
        pedido.setPet(pet);
        pedido.setSolicitante(solicitante);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedidoAdocao.EM_ANALISE);

        return toResponseDTO(pedidoAdocaoRepository.save(pedido));
    }

    public PedidoAdocaoResponseDTO buscarPorId(Long id) {
        PedidoAdocao pedido = pedidoAdocaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido de adoção não encontrado com ID: " + id));
        return toResponseDTO(pedido);
    }

    // metodo para listar todos os pedidos
    public List<PedidoAdocaoResponseDTO> listarTodosPedidos() {
        return pedidoAdocaoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletarPedido(Long id) {
        if (!pedidoAdocaoRepository.existsById(id)) {
            throw new NotFoundException("Pedido de adoção não encontrado com ID: " + id);
        }
        pedidoAdocaoRepository.deleteById(id);
    }

    // metodo para converter PedidoAdocao em PedidoAdocaoResponseDTO
    private PedidoAdocaoResponseDTO toResponseDTO(PedidoAdocao pedido) {
        PedidoAdocaoResponseDTO dto = new PedidoAdocaoResponseDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());

        dto.setStatus(pedido.getStatus());

        if (pedido.getPet() != null) {
            dto.setPet(toPetResponseDTO(pedido.getPet()));
        }

        if (pedido.getSolicitante() != null) {
            dto.setSolicitante(toUsuarioResponseDTO(pedido.getSolicitante()));
        }

        return dto;
    }

    public PedidoAdocaoResponseDTO atualizarStatusPedido(Long id, StatusPedidoAdocao novoStatus) {
        PedidoAdocao pedido = pedidoAdocaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido de adoção não encontrado com ID: " + id));

        pedido.setStatus(novoStatus);

        return toResponseDTO(pedidoAdocaoRepository.save(pedido));
    }
}
