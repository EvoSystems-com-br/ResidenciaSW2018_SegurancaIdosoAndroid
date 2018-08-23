package com.ifsp.detectorqueda.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Idoso implements Serializable{
    private Long id;
    private String nome;
    private String dataNascimento;
    private String grupoSanguineo;
    private Double altura;
    private Double peso;
    private String logradouro;
    private String numero;
    private Integer cep;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;

    private List<Alergia> alergias;
    private List<Medicamento> medicamentos;
    private List<ContatoEmergencia> contatosEmergencia;

    public Idoso(){
        this.alergias = new ArrayList<Alergia>();
        this.medicamentos = new ArrayList<Medicamento>();
        this.contatosEmergencia = new ArrayList<ContatoEmergencia>();
    }

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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public List<Alergia> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<ContatoEmergencia> getContatosEmergencia() {
        return contatosEmergencia;
    }

    public void setContatosEmergencia(List<ContatoEmergencia> contatosEmergencia) {
        this.contatosEmergencia = contatosEmergencia;
    }

    public boolean isEmpty(){
        if(this.nome != null){
            return false;
        }else if(this.dataNascimento != null){
            return false;
        }else if(this.grupoSanguineo != null){
            return false;
        }else if(this.altura != null){
            return false;
        }else if(this.peso != null){
            return false;
        }else if(this.logradouro != null){
            return false;
        }else if(this.numero != null){
            return false;
        }else if(this.cep != null){
            return false;
        }else if(this.bairro != null){
            return false;
        }else if(this.cidade != null){
            return false;
        }else if(this.estado != null){
            return false;
        }else if(this.complemento != null){
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

