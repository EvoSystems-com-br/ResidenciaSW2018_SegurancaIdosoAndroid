package com.ifsp.detectorqueda.beans;

import java.io.Serializable;

public class ContatoEmergencia implements Serializable {
    private Long id;
    private String nome;
    private Long numero;
    private String parentesco;
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

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
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
        }else if(this.numero != null){
            return false;
        }else if(this.parentesco != null){
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