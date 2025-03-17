/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcventidades;

import jakarta.persistence.CascadeType;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Ramos
 */
@Entity
@Table(name = "DetalleVenta")
public class DetalleVenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleVenta", nullable = false)
    private long id;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precioUnitario", nullable = false)
    private int precioUnitario;

    @Column(name = "subtotal", nullable = false)
    private int subtotal;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @Column(name = "esPersonalizado", nullable = false)
    private boolean esPersonalizado;
    
    @Column(name = "personalizacion")
    private String personalizacion;
    
    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    public DetalleVenta() {
    }

    public DetalleVenta(int cantidad, int precioUnitario, int subtotal, Producto producto, boolean esPersonalizado, String personalizacion, Venta venta) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.producto = producto;
        this.esPersonalizado = esPersonalizado;
        this.personalizacion = personalizacion;
        this.venta = venta;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public boolean isEsPersonalizado() {
        return esPersonalizado;
    }

    public void setEsPersonalizado(boolean esPersonalizado) {
        this.esPersonalizado = esPersonalizado;
    }

    public String getPersonalizacion() {
        return personalizacion;
    }

    public void setPersonalizacion(String personalizacion) {
        this.personalizacion = personalizacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + this.cantidad;
        hash = 37 * hash + this.precioUnitario;
        hash = 37 * hash + this.subtotal;
        hash = 37 * hash + Objects.hashCode(this.producto);
        hash = 37 * hash + (this.esPersonalizado ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.personalizacion);
        hash = 37 * hash + Objects.hashCode(this.venta);
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
        final DetalleVenta other = (DetalleVenta) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (this.precioUnitario != other.precioUnitario) {
            return false;
        }
        if (this.subtotal != other.subtotal) {
            return false;
        }
        if (this.esPersonalizado != other.esPersonalizado) {
            return false;
        }
        if (!Objects.equals(this.personalizacion, other.personalizacion)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return Objects.equals(this.venta, other.venta);
    }

}
