package com.comandago.api.services;

import com.comandago.api.models.Pedido;
import com.comandago.api.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    final PedidoRepository pedidoRepository;

    PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obterPedidoPorId(Long id) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        return optionalPedido.orElse(null);
    }

    public Pedido criarPedido(Pedido pedido) {
        if(pedido != null)
            return pedidoRepository.save(pedido);
        return null;
    }

    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        if (optionalPedido.isPresent()) {
            Pedido pedidoExistente = optionalPedido.get();
            pedidoExistente.setDataHora(pedidoAtualizado.getDataHora());
            pedidoExistente.setUsuario(pedidoAtualizado.getUsuario());
            pedidoExistente.setMesa(pedidoAtualizado.getMesa());
            pedidoExistente.setEstado(pedidoAtualizado.getEstado());
            pedidoExistente.setItens(pedidoAtualizado.getItens());
            return pedidoRepository.save(pedidoAtualizado);
        } else {
            return null;
        }
    }

    public void excluirPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
