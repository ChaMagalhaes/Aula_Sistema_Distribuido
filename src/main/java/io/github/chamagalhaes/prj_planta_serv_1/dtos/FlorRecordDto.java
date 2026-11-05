package io.github.chamagalhaes.prj_planta_serv_1.dtos;

public record FlorRecordDto(long idFlor,
                            double comprimento_sepala,
                            double largura_sepala,
                            double comprimento_petala,
                            double largura_petala,
                            String cor,
                            String especieTipo,
                            long idPlanta) {
}
