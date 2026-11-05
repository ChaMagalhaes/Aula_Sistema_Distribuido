package io.github.chamagalhaes.prj_planta_serv_pmml.controllers;

import io.github.chamagalhaes.prj_planta_serv_pmml.dtos.ClasseRecordDto;
import io.github.chamagalhaes.prj_planta_serv_pmml.dtos.FlorRecordDto;
import io.github.chamagalhaes.prj_planta_serv_pmml.services.ClassificarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pmml")
public class ClassificadorController {

    @Autowired
    private ClassificarService classificarService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ClasseRecordDto salvarClassificador(@RequestBody FlorRecordDto florRecordDto) {
        return classificarService.classificar(florRecordDto);
    }
}
