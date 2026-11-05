package io.github.chamagalhaes.prj_planta_serv_1.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "flor")
public class Flor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idflor;

    @Column(nullable = false)
    private double comprimento_sepala;

    @Column(nullable = false)
    private double largura_sepala;

    @Column(nullable = false)
    private double comprimento_pepala;

    @Column(nullable = false)
    private double largura_petala;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    private String especieTipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idplanta", nullable = false)
    private Planta planta;

    public long getIdflor() {
        return idflor;
    }

    public void setIdflor(long idflor) {
        this.idflor = idflor;
    }

    public double getComprimento_sepala() {
        return comprimento_sepala;
    }

    public void setComprimento_sepala(double comprimento_sepala) {
        this.comprimento_sepala = comprimento_sepala;
    }

    public double getLargura_sepala() {
        return largura_sepala;
    }

    public void setLargura_sepala(double largura_sepala) {
        this.largura_sepala = largura_sepala;
    }

    public double getComprimento_pepala() {
        return comprimento_pepala;
    }

    public void setComprimento_pepala(double comprimento_pepala) {
        this.comprimento_pepala = comprimento_pepala;
    }

    public double getLargura_petala() {
        return largura_petala;
    }

    public void setLargura_petala(double largura_petala) {
        this.largura_petala = largura_petala;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getEspecieTipo() {
        return especieTipo;
    }

    public void setEspecieTipo(String especieTipo) {
        this.especieTipo = especieTipo;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }
}
