package com.mariana.parcial1ramirezmariana;

import java.io.Serializable;

public class Solicitud implements Serializable {
    String curp;
    String nombre;
    String apellidos;
    String domicilio;
    double ingresos;
    String tipoTarjeta; //Tradicional, Oro, Platino

    public Solicitud(String curp, String nombre, String apellidos, String domicilio, double ingresos, String tipoTarjeta) {
        this.curp = curp;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.ingresos = ingresos;
        this.tipoTarjeta = tipoTarjeta;
    }

    public Solicitud() {
        this.curp = "";
        this.nombre = "";
        this.apellidos = "";
        this.domicilio = "";
        this.ingresos = 0.0;
        this.tipoTarjeta = "";
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }



    public boolean validar(double cantidad, String tipo){
        if(tipo.equals("Tradicional")){
            if(cantidad >= 6000){
                return true;
            }
            return false;
        }
        if(tipo.equals("Oro")){
            if(cantidad >= 15000){
                return true;
            }
            return false;
        }
        if(tipo.equals("Platino")){
            if(cantidad >= 24000){
                return true;
            }
            return false;
        }
        return false;
    }
}
