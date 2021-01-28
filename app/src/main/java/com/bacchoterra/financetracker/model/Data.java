package com.bacchoterra.financetracker.model;

public class Data {

    private float valor;
    private float porcentagem_variacao_dia;
    private float minimo_dia;
    private float maximo_dia;

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getPorcentagem_variacao_dia() {
        return porcentagem_variacao_dia;
    }

    public void setPorcentagem_variacao_dia(float porcentagem_variacao_dia) {
        this.porcentagem_variacao_dia = porcentagem_variacao_dia;
    }

    public float getMinimo_dia() {
        return minimo_dia;
    }

    public void setMinimo_dia(float minimo_dia) {
        this.minimo_dia = minimo_dia;
    }

    public float getMaximo_dia() {
        return maximo_dia;
    }

    public void setMaximo_dia(float maximo_dia) {
        this.maximo_dia = maximo_dia;
    }
}
