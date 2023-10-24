package com.comandago.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.comandago.api.models.Mesa;
import com.comandago.api.repositories.MesaRepository;

@Service
public class MesaService {

    final MesaRepository mesaRepository;

    MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public List<Mesa> listarTodasMesas() {
        return mesaRepository.findAll();
    }

    public Optional<Mesa> buscarMesaPorId(Long id) {
        return mesaRepository.findById(id);
    }

    public Mesa criarMesa(Mesa mesa) {
        if(mesa != null)
            return mesaRepository.save(mesa);
        return null;
    }

    public Optional<Mesa> atualizarMesa(Long id, Mesa mesaAtualizada) {
        Optional<Mesa> mesaExistente = mesaRepository.findById(id);
        if (mesaExistente.isPresent()) {
            Mesa mesa = mesaExistente.get();
            mesa.setEstado(mesaAtualizada.isEstado());
            mesa.setEstaAtiva(mesaAtualizada.isEstaAtiva());
            return Optional.of(mesaRepository.save(mesa));
        } else {
            return Optional.empty();
        }
    }

    public boolean excluirMesa(Long id) {
        Optional<Mesa> mesaExistente = mesaRepository.findById(id);
        if (mesaExistente.isPresent()) {
            mesaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
