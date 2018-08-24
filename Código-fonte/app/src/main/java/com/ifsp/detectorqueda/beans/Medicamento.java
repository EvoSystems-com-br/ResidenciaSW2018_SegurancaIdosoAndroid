package com.ifsp.detectorqueda.beans;

import java.io.Serializable;

public class Medicamento implements Serializable {
    private Long id;
    private String nome;
    private Idoso idoso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Idoso getIdoso() {
        return idoso;
    }

    public void setIdoso(Idoso idoso) {
        this.idoso = idoso;
    }

    public boolean isEmpty(){
        if(this.nome != null){
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