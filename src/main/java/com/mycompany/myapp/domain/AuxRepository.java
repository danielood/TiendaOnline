package com.mycompany.myapp.domain;

public class AuxRepository {

    private Long id;
    private String auxString;
    private Double auxDouble;
    private Long auxLong;

    public AuxRepository(Long id,String auxString){
        this.id = id;
        this.auxString = auxString;
    }

    public AuxRepository(Long id,Double auxDouble){
        this.id = id;
        this.auxDouble = auxDouble;
    }

    public AuxRepository(Long id,Long auxLong){
        this.id = id;
        this.auxLong = auxLong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuxString() {
        return auxString;
    }

    public void setAuxString(String auxString) {
        this.auxString = auxString;
    }

    public Double getAuxDouble() {
        return auxDouble;
    }

    public void setAuxDouble(Double auxDouble) {
        this.auxDouble = auxDouble;
    }

    public Long getAuxLong() {
        return auxLong;
    }

    public void setAuxLong(Long auxLong) {
        this.auxLong = auxLong;
    }

}
