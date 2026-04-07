package com.turnos.automation.models;

public class DoctorRequest {

    private String name;
    private String documentId;
    private String office;
    private String shift;

    public DoctorRequest() {
    }

    public DoctorRequest(String name, String documentId) {
        this.name = name;
        this.documentId = documentId;
    }

    public DoctorRequest(String name, String documentId, String office, String shift) {
        this.name = name;
        this.documentId = documentId;
        this.office = office;
        this.shift = shift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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
