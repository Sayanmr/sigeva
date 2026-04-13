package model;

public class Lote {
    private int idLote;
    private String numeroLote;

    public Lote(int idLote, String numeroLote) {
        this.idLote = idLote;
        this.numeroLote = numeroLote;
    }

    public int getIdLote() { return idLote; }
    public String getNumeroLote() { return numeroLote; }

    @Override
    public String toString() {
        return numeroLote;
    }
}