package io.github.chamagalhaes.PrjServicoGestorJava_Python.controllers;

import io.github.chamagalhaes.PrjServicoGestorJava_Python.services.SistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistemas")
public class SistemaController {

    private SistemaService sistemaService;

    public SistemaController(SistemaService sistemaService) {
        this.sistemaService = sistemaService;
    }

    /*Métodos Serviço 1 - Postgres*/
    @CrossOrigin(origins = "*")
    @GetMapping("/srv1/donos")
    public ResponseEntity<String> getDonos() {
        return sistemaService.getDonos();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/srv1/donos")
    public ResponseEntity<String> salvarDono(RequestEntity<String> dono) {
        return sistemaService.salvarDono(dono);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/srv1/donos/{id}")
    public void deletarDonos(@PathVariable Long id) {
        sistemaService.deletarDono(id);
    }

    /*Métodos Serviço 2 - Django MongoDb*/
    @CrossOrigin(origins = "*")
    @GetMapping("/srv2/donos")
    public ResponseEntity<String> getDonos_Mongo() {
        return sistemaService.getDono_Mongo();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/srv2/donos")
    public ResponseEntity<String> salvarDono_Mongo(RequestEntity<String> dono) {
        return sistemaService.salvarDados_Mongo(dono);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/srv2/donos/{id}")
    public void deletarDonos_Mongo(@PathVariable String id) {
        sistemaService.deletarDono_Mongo(id);
    }

    /*Métodos Serviço 3 - Flask MongoDB*/
    @CrossOrigin(origins = "*")
    @GetMapping("/srv3/donos")
    public ResponseEntity<String> getDonos_Flask() {
        return sistemaService.getDono_Flask();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/srv3/donos")
    public ResponseEntity<String> salvarDono_Flask(RequestEntity<String> dono) {
        return sistemaService.salvarDados_Flask(dono);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/srv3/donos/{id}")
    public void deletarDonos_Flask(@PathVariable String id) {
        sistemaService.deletarDono_Flask(id);
    }

}
