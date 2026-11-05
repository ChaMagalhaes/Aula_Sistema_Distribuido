package io.github.chamagalhaes.prj_planta_serv_1.controllers;

import io.github.chamagalhaes.prj_planta_serv_1.dtos.FlorRecordDto;
import io.github.chamagalhaes.prj_planta_serv_1.models.Flor;
import io.github.chamagalhaes.prj_planta_serv_1.services.FlorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flores")
public class FlorController {

    @Autowired
    private FlorService florService;

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Flor> getFlores() {
        return florService.getFlores();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public Flor getFlores(@PathVariable Long id) {
        return florService.getFlor(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Flor salvarFlor(@RequestBody FlorRecordDto florRecordDto) {
        return florService.salvarFlor(florRecordDto);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping
    public void deletarFlor(@RequestBody Flor flor) {
        florService.excluirFlor(flor);
    }
}
