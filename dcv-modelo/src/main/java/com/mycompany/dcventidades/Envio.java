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

    @Column(name = "metodoPago", nullable = false)
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Envio() {
    }

    public Envio(Date fechaEnvio, String estado, String direccionEntrega, String metodoPago, Pedido pedido) {
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        this.direccionEntrega = direccionEntrega;
        this.metodoPago = metodoPago;
        this.pedido = pedido;
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 67 * hash + Objects.hashCode(this.fechaEnvio);
        hash = 67 * hash + Objects.hashCode(this.estado);
        hash = 67 * hash + Objects.hashCode(this.direccionEntrega);
        hash = 67 * hash + Objects.hashCode(this.metodoPago);
        hash = 67 * hash + Objects.hashCode(this.pedido);
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
        return Objects.equals(this.pedido, other.pedido);
    }

}
