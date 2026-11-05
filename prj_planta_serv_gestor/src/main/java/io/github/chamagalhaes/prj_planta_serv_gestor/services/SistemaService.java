package io.github.chamagalhaes.prj_planta_serv_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SistemaService {
    private RestTemplate restTemplate;

    private final String urlServ1Plantas = "http://localhost:8081/api/plantas";
    private final String urlServ1Flores = "http://localhost:8081/api/flores";
    private final String urlServ3Pmml = "http://localhost:8083/api/pmml";

    public SistemaService() {
        restTemplate = new RestTemplate();
    }

    /*Métodos Serviço 1 - API Plantas*/

    public ResponseEntity<String> getPlantas() {
        ResponseEntity<String> response = restTemplate.getForEntity(urlServ1Plantas, String.class);
        return response;
    }

    public ResponseEntity<String> salvarPlanta(RequestEntity<String> planta) {
        ResponseEntity<String> response = restTemplate.postForEntity(urlServ1Plantas, planta, String.class);
        return response;
    }

    public void deletarPlanta(RequestEntity<String> planta) {
        restTemplate.exchange(urlServ1Plantas, HttpMethod.DELETE, planta, String.class);
    }

    /*Métodos Serviço 1 - API Flores*/

    public ResponseEntity<String> getFlores() {
        ResponseEntity<String> response = restTemplate.getForEntity(urlServ1Flores, String.class);
        return response;
    }

    public ResponseEntity<String> salvarFlores(RequestEntity<String> flor) {
        ResponseEntity<String> response = restTemplate.postForEntity(urlServ1Flores, flor, String.class);
        return response;
    }

    public void deletarFlores(RequestEntity<String> flor) {
        restTemplate.exchange(urlServ1Flores, HttpMethod.DELETE, flor, String.class);
    }

    /*Método Serviço 3 - API PMML*/

    public ResponseEntity<String> classificar(RequestEntity<String> dadosFlor) {
        ResponseEntity<String> response = restTemplate.postForEntity(urlServ3Pmml, dadosFlor, String.class);
        return response;
    }
}
