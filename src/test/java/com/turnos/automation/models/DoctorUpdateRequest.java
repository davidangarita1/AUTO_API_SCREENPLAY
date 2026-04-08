package com.turnos.automation.models;

public class DoctorUpdateRequest {

    private String name;
    private String office;
    private String shift;

    public DoctorUpdateRequest() {
    }

    public DoctorUpdateRequest(String office, String shift) {
        this.office = office;
        this.shift = shift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
