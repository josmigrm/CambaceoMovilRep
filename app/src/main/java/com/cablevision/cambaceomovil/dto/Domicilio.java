package com.cablevision.cambaceomovil.dto;

import java.io.Serializable;

/**
 * Created by Mike on 6/25/15.
 */
public class Domicilio implements Serializable{

    String Num_Ext;
    String Num_Int;
    String Edificio;
    String Departamento;
    String Orientacion;
    String Accont_No;
    String Estatus;
    String F_Activacion;
    String F_Suspension;
    String F_Cancelacion;
    String Oferta_Comercial;
    String Convetidor;
    String MTA;
    String CM;
    long Saldo_Total;
    long Saldo_Incobrable;

    public Domicilio(String num_Ext, String num_Int, String edificio, String departamento, String orientacion, String accont_No, String estatus, String f_Activacion, String f_Suspension, String f_Cancelacion, String oferta_Comercial, String convetidor, String MTA, String CM, Integer saldo_Total, Integer saldo_Incobrable) {
        Num_Ext = num_Ext;
        Num_Int = num_Int;
        Edificio = edificio;
        Departamento = departamento;
        Orientacion = orientacion;
        Accont_No = accont_No;
        Estatus = estatus;
        F_Activacion = f_Activacion;
        F_Suspension = f_Suspension;
        F_Cancelacion = f_Cancelacion;
        Oferta_Comercial = oferta_Comercial;
        Convetidor = convetidor;
        this.MTA = MTA;
        this.CM = CM;
        Saldo_Total = saldo_Total;
        Saldo_Incobrable = saldo_Incobrable;
    }

    public Domicilio(){}

    public String getNum_Ext() {
        return Num_Ext;
    }

    public void setNum_Ext(String num_Ext) {
        Num_Ext = num_Ext;
    }

    public String getNum_Int() {
        return Num_Int;
    }

    public void setNum_Int(String num_Int) {
        Num_Int = num_Int;
    }

    public String getEdificio() {
        return Edificio;
    }

    public void setEdificio(String edificio) {
        Edificio = edificio;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getOrientacion() {
        return Orientacion;
    }

    public void setOrientacion(String orientacion) {
        Orientacion = orientacion;
    }

    public String getAccont_No() {
        return Accont_No;
    }

    public void setAccont_No(String accont_No) {
        Accont_No = accont_No;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    public String getF_Activacion() {
        return F_Activacion;
    }

    public void setF_Activacion(String f_Activacion) {
        F_Activacion = f_Activacion;
    }

    public String getF_Suspension() {
        return F_Suspension;
    }

    public void setF_Suspension(String f_Suspension) {
        F_Suspension = f_Suspension;
    }

    public String getF_Cancelacion() {
        return F_Cancelacion;
    }

    public void setF_Cancelacion(String f_Cancelacion) {
        F_Cancelacion = f_Cancelacion;
    }

    public String getOferta_Comercial() {
        return Oferta_Comercial;
    }

    public void setOferta_Comercial(String oferta_Comercial) {
        Oferta_Comercial = oferta_Comercial;
    }

    public String getConvetidor() {
        return Convetidor;
    }

    public void setConvetidor(String convetidor) {
        Convetidor = convetidor;
    }

    public String getMTA() {
        return MTA;
    }

    public void setMTA(String MTA) {
        this.MTA = MTA;
    }

    public String getCM() {
        return CM;
    }

    public void setCM(String CM) {
        this.CM = CM;
    }

    public long getSaldo_Total() {
        return Saldo_Total;
    }

    public void setSaldo_Total(long saldo_Total) {
        Saldo_Total = saldo_Total;
    }

    public long getSaldo_Incobrable() {
        return Saldo_Incobrable;
    }

    public void setSaldo_Incobrable(long saldo_Incobrable) {
        Saldo_Incobrable = saldo_Incobrable;
    }

    @Override
    public String toString() {
        return Num_Ext + " " +Num_Int + " " + Edificio + " " + Departamento + " " +
                Orientacion + " "+ Accont_No + " " + Estatus + " " + F_Activacion + " " +
                F_Suspension + " " +
                F_Cancelacion + " " +
                Oferta_Comercial + " " +
                Convetidor + " " +
                MTA + " " +
                CM + " " +
                Saldo_Total + " " +
                Saldo_Incobrable;
    }
}
