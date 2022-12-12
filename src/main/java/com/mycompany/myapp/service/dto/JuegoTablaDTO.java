package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Fichero;
import com.mycompany.myapp.domain.enumeration.Pegi;

import java.util.List;

public class JuegoTablaDTO {

    private Long id;
    private String titulo;
    private Pegi pegi;
    private Double precio;
    private Integer stock;
    private Fichero caratula;
    private String compannia;
    private List<String> plataformas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pegi getPegi() {
        return pegi;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Fichero getCaratula() {
        return caratula;
    }

    public void setCaratula(Fichero caratula) {
        this.caratula = caratula;
    }

    public String getCompannia() {
        return compannia;
    }

    public void setCompannia(String compannia) {
        this.compannia = compannia;
    }

    public List<String> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<String> plataformas) {
        this.plataformas = plataformas;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
