/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcventidades;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author Ramos
 */
@Entity
@Table(name = "Venta")
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenta", nullable = false)
    private long id;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "metodoPago", nullable = false)
    private String metodoPago;

    @Column(name = "estado", nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reembolso reembolso;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Envio> envios;
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detallesVenta;

    public Venta() {
    }

    public Venta(Date fecha, double total, String metodoPago, String estado, Usuario usuario, Reembolso reembolso, List<Envio> envios, List<DetalleVenta> detallesVenta) {
        this.fecha = fecha;
        this.total = total;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.usuario = usuario;
        this.reembolso = reembolso;
        this.envios = envios;
        this.detallesVenta = detallesVenta;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Reembolso getReembolso() {
        return reembolso;
    }

    public void setReembolso(Reembolso reembolso) {
        this.reembolso = reembolso;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 61 * hash + Objects.hashCode(this.fecha);
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.total) ^ (Double.doubleToLongBits(this.total) >>> 32));
        hash = 61 * hash + Objects.hashCode(this.metodoPago);
        hash = 61 * hash + Objects.hashCode(this.estado);
        hash = 61 * hash + Objects.hashCode(this.usuario);
        hash = 61 * hash + Objects.hashCode(this.reembolso);
        hash = 61 * hash + Objects.hashCode(this.envios);
        hash = 61 * hash + Objects.hashCode(this.detallesVenta);
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
        final Venta other = (Venta) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.total) != Double.doubleToLongBits(other.total)) {
            return false;
        }
        if (!Objects.equals(this.metodoPago, other.metodoPago)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.reembolso, other.reembolso)) {
            return false;
        }
        if (!Objects.equals(this.envios, other.envios)) {
            return false;
        }
        return Objects.equals(this.detallesVenta, other.detallesVenta);
    }
    
}
