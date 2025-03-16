/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcv.entidades;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Ramos
 */
@Entity
@Table(name = "CompraInsumo")
public class CompraInsumo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompraInsumo", nullable = false)
    private long id;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @OneToOne(mappedBy = "compraInsumo", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetalleCompraInsumo detalleCompraInsumo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public CompraInsumo() {
    }

    public CompraInsumo(Date fecha, String motivo, int cantidad, DetalleCompraInsumo detalleCompraInsumo, Usuario usuario) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.cantidad = cantidad;
        this.detalleCompraInsumo = detalleCompraInsumo;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public DetalleCompraInsumo getDetalleCompraInsumo() {
        return detalleCompraInsumo;
    }

    public void setDetalleCompraInsumo(DetalleCompraInsumo detalleCompraInsumo) {
        this.detalleCompraInsumo = detalleCompraInsumo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + Objects.hashCode(this.fecha);
        hash = 37 * hash + Objects.hashCode(this.motivo);
        hash = 37 * hash + this.cantidad;
        hash = 37 * hash + Objects.hashCode(this.detalleCompraInsumo);
        hash = 37 * hash + Objects.hashCode(this.usuario);
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
        final CompraInsumo other = (CompraInsumo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.detalleCompraInsumo, other.detalleCompraInsumo)) {
            return false;
        }
        return Objects.equals(this.usuario, other.usuario);
    }
}
