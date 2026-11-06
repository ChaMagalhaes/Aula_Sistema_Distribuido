
package io.github.chamagalhaes.PrjServicoGestorJava_Python.services;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SistemaService {

    private RestTemplate restTemplate;

    private String url_serv1_postgres = "http://localhost:8000/api/donos/";
    private String url_serv2_mongoDb = "http://localhost:8000/api/donos/";
    private String url_serv3_flask_mongo = "http://localhost:5000/api/donos";

    public SistemaService() {
        restTemplate = new RestTemplate();
    }

    /* Métodos Serviço 1 - PostgreSQL */

    public ResponseEntity<String> getDonos() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url_serv1_postgres, String.class);
        return response;
    }

    public ResponseEntity<String> salvarDono(RequestEntity<String> dono) {
        ResponseEntity<String> response =
                restTemplate.postForEntity(url_serv1_postgres + "create/", dono, String.class);
        return response;
    }

    public void deletarDono(Long id) {
        String urlDeletar = url_serv1_postgres + id + "/";
        restTemplate.delete(urlDeletar);
    }

    /* Métodos Serviço 2 - Django MongoDB */

    public ResponseEntity<String> getDono_Mongo() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url_serv2_mongoDb, String.class);
        return response;
    }

    public ResponseEntity<String> salvarDados_Mongo(RequestEntity<String> dono) {
        ResponseEntity<String> response =
                restTemplate.postForEntity(url_serv2_mongoDb + "create/", dono, String.class);
        return response;
    }

    public void deletarDono_Mongo(String id) {
        String urlDeletar = url_serv2_mongoDb + id + "/";
        restTemplate.delete(urlDeletar);
    }

    /* Métodos Serviço 3 - Flask MongoDB */

    public ResponseEntity<String> getDono_Flask() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url_serv3_flask_mongo, String.class);
        return response;
    }

    public ResponseEntity<String> salvarDados_Flask(RequestEntity<String> dono) {
        ResponseEntity<String> response =
                restTemplate.postForEntity(url_serv3_flask_mongo, dono, String.class);
        return response;
    }

    public void deletarDono_Flask(String id) {
        String urlDeletar = url_serv3_flask_mongo + "/" + id;
        restTemplate.delete(urlDeletar);
    }

}
