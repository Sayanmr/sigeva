package model;

public class Vacuna {
    private String nombre;
    private int cantidad;

    public Vacuna(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}