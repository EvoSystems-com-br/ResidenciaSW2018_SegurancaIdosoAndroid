package com.ifsp.detectorqueda.beans;

import java.io.Serializable;

public class Alergia implements Serializable {
    private Long id;
    private String descricao;
    private Idoso idoso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Idoso getIdoso() {
        return idoso;
    }

    public void setIdoso(Idoso idoso) {
        this.idoso = idoso;
    }

    public boolean isEmpty(){
        if(this.descricao != null){
            return false;
        }else if(!this.idoso.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public boolean isEntity(){
        if(this.id >= 0 && this.id != null){
            return true;
        }else{
            return false;
        }
    }
}

