
package io.github.chamagalhaes.prj_planta_serv_gestor.controllers;

import io.github.chamagalhaes.prj_planta_serv_gestor.services.SistemaService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sistemas")
public class SistemasController {

    private final SistemaService sistemaService;

    public SistemasController(SistemaService sistemaService) {
        this.sistemaService = sistemaService;
    }

    // Plantas

    @GetMapping("/plantas")
    public ResponseEntity<String> getPlantas() {
        return sistemaService.getPlantas();
    }

    @PostMapping("/plantas")
    public ResponseEntity<String> salvarPlanta(
            RequestEntity<String> planta) {
        return sistemaService.salvarPlanta(planta);
    }

    @DeleteMapping("/plantas")
    public ResponseEntity<Void> deletarPlanta(
            RequestEntity<String> planta) {
        sistemaService.deletarPlanta(planta);
        return ResponseEntity.noContent().build();
    }

    // Flores

    @GetMapping("/flores")
    public ResponseEntity<String> getFlores() {
        return sistemaService.getFlores();
    }

    @PostMapping("/flores")
    public ResponseEntity<String> salvarFlores(
            RequestEntity<String> flor) {
        return sistemaService.salvarFlores(flor);
    }

    @DeleteMapping("/flores")
    public ResponseEntity<Void> deletarFlores(
            RequestEntity<String> flor) {
        sistemaService.deletarFlores(flor);
        return ResponseEntity.noContent().build();
    }

    // Classificação PMML

    @PostMapping("/classificar")
    public ResponseEntity<String> classificador(
            RequestEntity<String> dadosFlor) {
        return sistemaService.classificar(dadosFlor);
    }
}