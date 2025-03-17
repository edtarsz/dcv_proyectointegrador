/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcventidades;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Ramos
 */
@Entity
@Table(name = "Envio")
public class Envio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEnvio", nullable = false)
    private long id;

    @Column(name = "fechaEnvio", nullable = false)
    private Date fechaEnvio;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "direccionEntrega", nullable = false)
    private String direccionEntrega;
    
    @Column(name = "costo", nullable = false)
    private double costo;

    @Column(name = "metodoPago", nullable = false)
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    public Envio() {
    }

    public Envio(Date fechaEnvio, String estado, String direccionEntrega, double costo, String metodoPago, Venta venta) {
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        this.direccionEntrega = direccionEntrega;
        this.costo = costo;
        this.metodoPago = metodoPago;
        this.venta = venta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.fechaEnvio);
        hash = 83 * hash + Objects.hashCode(this.estado);
        hash = 83 * hash + Objects.hashCode(this.direccionEntrega);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.costo) ^ (Double.doubleToLongBits(this.costo) >>> 32));
        hash = 83 * hash + Objects.hashCode(this.metodoPago);
        hash = 83 * hash + Objects.hashCode(this.venta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Envio other = (Envio) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.costo) != Double.doubleToLongBits(other.costo)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.direccionEntrega, other.direccionEntrega)) {
            return false;
        }
        if (!Objects.equals(this.metodoPago, other.metodoPago)) {
            return false;
        }
        if (!Objects.equals(this.fechaEnvio, other.fechaEnvio)) {
            return false;
        }
        return Objects.equals(this.venta, other.venta);
    }
    
}
