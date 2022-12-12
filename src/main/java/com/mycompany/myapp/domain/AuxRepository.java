package com.mycompany.myapp.domain;

public class AuxRepository {

    private Long id;
    private String auxString;

    public AuxRepository(Long id,String auxString){
        this.id = id;
        this.auxString = auxString;
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
}
