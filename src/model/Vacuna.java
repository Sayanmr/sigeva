package model;

public class Vacuna {

    private int idVacuna;
    private String nombre;
    private String tipo;
    private String fabricante;

    public Vacuna() {
    }

    public Vacuna(int idVacuna, String nombre, String tipo, String fabricante) {
        this.idVacuna = idVacuna;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    public int getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(int idVacuna) {
        this.idVacuna = idVacuna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}