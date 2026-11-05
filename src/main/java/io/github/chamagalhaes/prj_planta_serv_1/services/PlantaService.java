package io.github.chamagalhaes.prj_planta_serv_1.services;

import io.github.chamagalhaes.prj_planta_serv_1.models.Planta;
import io.github.chamagalhaes.prj_planta_serv_1.repositories.PlantaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantaService {
    private final PlantaRepository plantaRepository;

    public PlantaService(PlantaService plantaService){
        this.plantaRepository = plantaService.plantaRepository;
    }

    public List<Planta> getPlantas(){
        return plantaRepository.findAll();
    }

    public Planta salvarPlanta(Planta planta) {
        return plantaRepository.save(planta);
    }

    public void excluirPlanta(Planta planta) {
        plantaRepository.deleteById(planta.getIdplanta());
    }
}
