package com.comandago.api.controllers;

import com.comandago.api.dtos.ComandaDTO;
import com.comandago.api.dtos.IdComandaDTO;
import com.comandago.api.models.Comanda;
import com.comandago.api.services.ComandaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comandas")
public class ComandaController {

    final ComandaService comandaService;

    ComandaController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }


    @GetMapping
    public ResponseEntity<List<Comanda>> listarComandas() {
        List<Comanda> comandas = comandaService.listarComandas();
        return ResponseEntity.ok(comandas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comanda> obterComandaPorId(@PathVariable Long id) {
        Comanda comanda = comandaService.obterComandaPorId(id);
        if (comanda != null) {
            return ResponseEntity.ok(comanda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<IdComandaDTO> criarComanda(@RequestBody ComandaDTO comanda) {
        Long idComanda = comandaService.criarComanda(comanda);
        if(idComanda > 0){
            return ResponseEntity.ok(new IdComandaDTO(idComanda));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comanda> atualizarComanda(@PathVariable Long id, @RequestBody Comanda comanda) {
        Comanda comandaAtualizada = comandaService.atualizarComanda(id, comanda);
        if (comandaAtualizada != null) {
            return ResponseEntity.ok(comandaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComanda(@PathVariable Long id) {
        boolean sucesso = comandaService.deletarComanda(id);
        if (sucesso) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
