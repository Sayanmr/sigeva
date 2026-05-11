package model;

import java.util.Date;

public class HistorialVacuna {

    private String vacuna;
    private Date fechaAplicacion;
    private String lote;
    private String dosis;

    public HistorialVacuna() {
    }

    public HistorialVacuna(String vacuna,
                           Date fechaAplicacion,
                           String lote,
                           String dosis) {
        this.vacuna = vacuna;
        this.fechaAplicacion = fechaAplicacion;
        this.lote = lote;
        this.dosis = dosis;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}