package io.github.chamagalhaes.prj_planta_serv_1.services;

import io.github.chamagalhaes.prj_planta_serv_1.dtos.FlorRecordDto;
import io.github.chamagalhaes.prj_planta_serv_1.models.Flor;
import io.github.chamagalhaes.prj_planta_serv_1.repositories.FlorRepository;
import io.github.chamagalhaes.prj_planta_serv_1.repositories.PlantaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlorService {
    private final FlorRepository florRepository;
    private final PlantaRepository plantaRepository;

    public FlorService(FlorRepository florRepository, PlantaRepository plantaRepository) {
        this.florRepository = florRepository;
        this.plantaRepository = plantaRepository;
    }

    public List<Flor> getFlores() {
        return florRepository.findAll();
    }

    public Flor getFlor(Long id){
        return florRepository.findById(id).get();
    }

    public Flor salvarFlor(FlorRecordDto florRecordDto) {
        Flor flor = new Flor();
        // Sem idFlor o banco gera um novo id; com idFlor a flor existente é atualizada
        if (florRecordDto.idFlor() != null) {
            flor.setIdflor(florRecordDto.idFlor());
        }
        flor.setComprimento_sepala(florRecordDto.comprimento_sepala());
        flor.setLargura_sepala(florRecordDto.largura_sepala());
        flor.setComprimento_pepala(florRecordDto.comprimento_petala());
        flor.setLargura_petala(florRecordDto.largura_petala());
        flor.setCor(florRecordDto.cor());
        flor.setEspecieTipo(florRecordDto.especieTipo());
        flor.setPlanta(plantaRepository.findById(florRecordDto.idPlanta())
                .orElseThrow(() -> new RuntimeException("Planta não encontrada: " + florRecordDto.idPlanta())));

        return florRepository.save(flor);
    }

    public void excluirFlor(Flor flor) {
        florRepository.deleteById(flor.getIdflor());
    }
}
