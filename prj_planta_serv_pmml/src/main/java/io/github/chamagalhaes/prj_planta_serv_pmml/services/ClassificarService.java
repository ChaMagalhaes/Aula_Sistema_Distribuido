package io.github.chamagalhaes.prj_planta_serv_pmml.services;

import io.github.chamagalhaes.prj_planta_serv_pmml.dtos.ClasseRecordDto;
import io.github.chamagalhaes.prj_planta_serv_pmml.dtos.FlorRecordDto;
import org.pmml4s.model.Model;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClassificarService {

    public ClasseRecordDto classificar(FlorRecordDto florRecordDto) {

        final String URL= "C:\\Users\\Charles\\Dev\\Faculdade\\Sistema Distribuido\\Prj_Planta\\PMML";

        FileInputStream is = null;
        Map<String, Object> saida = null;

        try {
            if(florRecordDto.classifica().toLowerCase().equals("svm")){
                is = new FileInputStream(URL + "\\knimeSvmIris.pmml");
            }else if(florRecordDto.classifica().toLowerCase().equals("decision tree")){
                is = new FileInputStream(URL + "\\knimeDtIris.pmml");
            }else {
                is = new FileInputStream(URL + "\\knimeDtIris.pmml");
            }

            Model model = Model.fromInputStream(is);

            //Map
            saida = model.predict(new HashMap<String, Object>() {{
                put("SepalLengthCm", florRecordDto.comprimento_sepala());
                put("SepalWidthCm", florRecordDto.largura_sepala());
                put("PetalLengthCm", florRecordDto.comprimento_petala());
                put("PetalWidthCm", florRecordDto.largura_petala());
            }});
//            System.out.println(saida.toString());
//            System.out.println(saida.get("predicted_Species").toString());
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        String classe = saida.get("predicted_Species").toString();
        if (classe.contains("-")) {
            classe = classe.replace("-", "");
        }
        if (classe.contains("Iris")) {
            classe = classe.replace("Iris", "");
        }

        ClasseRecordDto classeRecordDto = new ClasseRecordDto(classe);

        return classeRecordDto;
    }
}
