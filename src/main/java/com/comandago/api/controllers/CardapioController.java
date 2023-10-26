package com.comandago.api.controllers;

import com.comandago.api.models.Cardapio;
import com.comandago.api.services.CardapioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cardapios")
public class CardapioController {

    final CardapioService cardapioService;

    CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping
    public ResponseEntity<List<Cardapio>> listarCardapios() {
        List<Cardapio> cardapios = cardapioService.listarCardapios();
        return ResponseEntity.ok(cardapios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> obterCardapioPorId(@PathVariable Long id) {
        Cardapio cardapio = cardapioService.obterCardapioPorId(id);
        if (cardapio != null) {
            return ResponseEntity.ok(cardapio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cardapio> criarCardapio(@Valid @RequestBody Cardapio cardapio) {
        if(cardapio != null){
            Cardapio novoCardapio = cardapioService.criarCardapio(cardapio);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCardapio);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cardapio> atualizarCardapio(@PathVariable Long id, @Valid @RequestBody Cardapio cardapio) {
        Cardapio cardapioAtualizado = cardapioService.atualizarCardapio(id, cardapio);
        if (cardapioAtualizado != null) {
            return ResponseEntity.ok(cardapioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCardapio(@PathVariable Long id) {
        boolean sucesso = cardapioService.excluirCardapio(id);
        if (sucesso) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
