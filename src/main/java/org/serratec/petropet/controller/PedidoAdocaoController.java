package org.serratec.petropet.controller;

import jakarta.validation.Valid;
import org.serratec.petropet.dto.PedidoAdocaoRequestDTO;
import org.serratec.petropet.dto.PedidoAdocaoResponseDTO;
import org.serratec.petropet.enums.StatusPedidoAdocao;
import org.serratec.petropet.service.PedidoAdocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos-adocao")
public class PedidoAdocaoController {

    @Autowired
    private PedidoAdocaoService pedidoAdocaoService;

    @PostMapping
    public ResponseEntity<PedidoAdocaoResponseDTO> criarPedido(@Valid @RequestBody PedidoAdocaoRequestDTO pedidoRequestDTO) {
        PedidoAdocaoResponseDTO novoPedido = pedidoAdocaoService.criarPedido(pedidoRequestDTO);
        return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoAdocaoResponseDTO> buscarPorId(@PathVariable Long id) {
        PedidoAdocaoResponseDTO pedido = pedidoAdocaoService.buscarPorId(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoAdocaoService.deletarPedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<PedidoAdocaoResponseDTO>> listarTodosPedidos() {
        List<PedidoAdocaoResponseDTO> pedidos = pedidoAdocaoService.listarTodosPedidos();
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoAdocaoResponseDTO> atualizarStatusPedido(@PathVariable Long id, @RequestParam String novoStatus) {
        StatusPedidoAdocao statusEnum;
        try {
            statusEnum = StatusPedidoAdocao.valueOf(novoStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PedidoAdocaoResponseDTO pedidoAtualizado = pedidoAdocaoService.atualizarStatusPedido(id, statusEnum);
        return new ResponseEntity<>(pedidoAtualizado, HttpStatus.OK);
    }
}