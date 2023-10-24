package com.comandago.api.services;

import com.comandago.api.models.Comanda;
import com.comandago.api.repositories.ComandaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaService {

    final ComandaRepository comandaRepository;

    ComandaService(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }


    public List<Comanda> listarComandas() {
        return comandaRepository.findAll();
    }

    public Comanda obterComandaPorId(Long id) {
        Optional<Comanda> comanda = comandaRepository.findById(id);
        return comanda.orElse(null);
    }

    public Comanda criarComanda(Comanda comanda) {
        if(comanda != null)
            return comandaRepository.save(comanda);
        return null;
    }

    public Comanda atualizarComanda(Long id, Comanda comandaAtualizada) {
        Optional<Comanda> comandaExistente = comandaRepository.findById(id);
        if (comandaExistente.isPresent()) {
            comandaAtualizada.setId(comandaExistente.get().getId());
            return comandaRepository.save(comandaAtualizada);
        } else {
            return null;
        }
    }

    public boolean deletarComanda(Long id) {
        Optional<Comanda> comanda = comandaRepository.findById(id);
        if (comanda.isPresent()) {
            comandaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
