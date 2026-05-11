package model;

import java.util.Date;

public class ClienteVacunado {

    private int idCliente;
    private String nombreCompleto;
    private String documento;
    private Date fechaNacimiento;
    private String contacto;

    public ClienteVacunado() {
    }

    public ClienteVacunado(int idCliente,
                           String nombreCompleto,
                           String documento,
                           Date fechaNacimiento,
                           String contacto) {

        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.contacto = contacto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}