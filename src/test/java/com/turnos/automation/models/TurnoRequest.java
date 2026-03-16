package com.turnos.automation.models;

public class TurnoRequest {

    private long cedula;
    private String nombre;
    private String priority;

    public TurnoRequest() {
    }

    public TurnoRequest(long cedula, String nombre, String priority) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.priority = priority;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
