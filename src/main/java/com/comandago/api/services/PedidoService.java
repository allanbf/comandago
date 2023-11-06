package com.comandago.api.services;

import com.comandago.api.dtos.ItemPedidoDTO;
import com.comandago.api.models.Cardapio;
import com.comandago.api.models.Pedido;
import com.comandago.api.models.PedidosCardapio;
import com.comandago.api.repositories.CardapioRepository;
import com.comandago.api.repositories.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CardapioRepository cardapioRepository;

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

    public void adicionarItens(Pedido pedido, List<ItemPedidoDTO> itens){
        List<PedidosCardapio> itensPedido = new ArrayList<>();
        for(ItemPedidoDTO item : itens){
            Optional<Cardapio> cardapio = cardapioRepository.findById(item.CardapioId());
            if(cardapio.isPresent()){
                var itemPedido = new PedidosCardapio();
                itemPedido.setPedido(pedido);
                itemPedido.setCardapio(cardapio.get());
                itemPedido.setQuantidade(item.quantidade());
                itemPedido.setObservacoes(item.observacoes());
                itensPedido.add(itemPedido);
            }
        }
        pedido.addItens(itensPedido);
        pedidoRepository.save(pedido);
    }

    public void adicionarItem(Pedido pedido, ItemPedidoDTO item){
        Optional<Cardapio> cardapioOptional = cardapioRepository.findById(item.CardapioId());
        if(cardapioOptional.isPresent()){
            var pedidoCardapio = new PedidosCardapio();
            pedidoCardapio.setPedido(pedido);
            pedidoCardapio.setCardapio(cardapioOptional.get());
            pedidoCardapio.setQuantidade(item.quantidade());
            pedidoCardapio.setObservacoes(item.observacoes());
            pedido.addItem(pedidoCardapio);
            pedidoRepository.save(pedido);
        }
    }
}
