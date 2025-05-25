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
@Table(name = "CompraInsumo")
public class CompraInsumo implements Serializable {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompraInsumo", nullable = false)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "compraInsumo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompraInsumo> detallesCompra;

    public CompraInsumo() {
    }

    public CompraInsumo(Date fecha, String motivo, Usuario usuario, List<DetalleCompraInsumo> detallesCompra) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.usuario = usuario;
        this.detallesCompra = detallesCompra;
    }

    public List<DetalleCompraInsumo> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompraInsumo> detallesCompra) {
        this.detallesCompra = detallesCompra;
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
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.usuario, other.usuario);
    }
}
