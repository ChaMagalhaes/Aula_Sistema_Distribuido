package io.github.chamagalhaes.prj_planta_serv_1.dtos;

public record FlorRecordDto(Long idFlor,
                            Double comprimento_sepala,
                            Double largura_sepala,
                            Double comprimento_petala,
                            Double largura_petala,
                            String cor,
                            String especieTipo,
                            Long idPlanta) {
}
