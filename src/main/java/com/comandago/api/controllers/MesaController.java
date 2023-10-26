package com.comandago.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comandago.api.models.Mesa;
import com.comandago.api.repositories.MesaRepository;

@RestController
@RequestMapping("/mesas")
@CrossOrigin(origins = "*")
public class MesaController {

    final MesaRepository mesaRepository;

    MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Mesa>> listarTodasMesas() {
        List<Mesa> mesas = mesaRepository.findAll();
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> buscarMesaPorId(@PathVariable Long id) {
        Optional<Mesa> mesa = mesaRepository.findById(id);
        if (mesa.isPresent()) {
            return new ResponseEntity<>(mesa.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Mesa> criarMesa(@RequestBody Mesa mesa) {
        if(mesa != null){
            Mesa novaMesa = mesaRepository.save(mesa);
            return new ResponseEntity<>(novaMesa, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> atualizarMesa(@PathVariable Long id, @RequestBody Mesa mesaAtualizada) {
        Optional<Mesa> mesaExistente = mesaRepository.findById(id);
        if (mesaExistente.isPresent()) {
            Mesa mesa = mesaExistente.get();
            mesa.setEstado(mesaAtualizada.isEstado());
            mesa.setEstaAtiva(mesaAtualizada.isEstaAtiva());
            mesaRepository.save(mesa);
            return new ResponseEntity<>(mesa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMesa(@PathVariable Long id) {
        Optional<Mesa> mesaExistente = mesaRepository.findById(id);
        if (mesaExistente.isPresent()) {
            mesaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
