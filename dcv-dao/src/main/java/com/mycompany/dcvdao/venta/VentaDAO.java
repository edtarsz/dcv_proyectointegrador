/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcvdao.venta;

import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

/**
 * @author
 */
public class VentaDAO implements IVentaDAO {

    private static final Logger logger = Logger.getLogger(VentaDAO.class.getName());
    private EntityManager entityManager;

    public VentaDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("VentaDAO initialized with a new EntityManager.");
    }

    @Override
    public Venta crearVenta(Venta venta) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(venta);
            transaction.commit();
            logger.info("Venta creada con éxito.");
            return venta;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al crear la venta", e);
            throw new ModelException("Error al crear venta", e);
        }
    }

    @Override
    public Venta obtenerVentaPorId(long id) {
        return entityManager.find(Venta.class, id);
    }

    @Override
    public List<Venta> obtenerTodasLasVentas() throws ModelException {
        System.out.println("VentaDAO: Iniciando obtenerTodasLasVentas");
        EntityTransaction tx = null;
        try {
            // Primero verificar si hay ventas con una consulta simple
            String countQuery = "SELECT COUNT(v) FROM Venta v";
            Long ventasCount = entityManager.createQuery(countQuery, Long.class)
                    .getSingleResult();
            System.out.println("VentaDAO: Número total de ventas en BD: " + ventasCount);

            tx = entityManager.getTransaction();
            tx.begin();

            // Consulta principal con todos los joins necesarios
            String jpql = "SELECT DISTINCT v FROM Venta v "
                    + "LEFT JOIN FETCH v.cliente c "
                    + "LEFT JOIN FETCH v.detallesVenta d "
                    + "LEFT JOIN FETCH d.producto p "
                    + "WHERE v.id IN (SELECT v2.id FROM Venta v2) "
                    + "ORDER BY v.fecha DESC";

            System.out.println("VentaDAO: Ejecutando consulta principal");
            List<Venta> ventas = entityManager.createQuery(jpql, Venta.class)
                    .getResultList();

            System.out.println("VentaDAO: Ventas recuperadas: " + ventas.size());

            // Imprimir detalles de cada venta para debugging
            for (Venta venta : ventas) {
                System.out.println("Venta ID: " + venta.getId()
                        + ", Cliente: " + (venta.getCliente() != null ? venta.getCliente().getNombreCompleto() : "null")
                        + ", Detalles: " + (venta.getDetallesVenta() != null ? venta.getDetallesVenta().size() : "null")
                        + ", Fecha: " + venta.getFecha());
            }

            tx.commit();
            return ventas;
        } catch (Exception e) {
            System.err.println("VentaDAO: Error en obtenerTodasLasVentas: " + e.getMessage());
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception re) {
                    System.err.println("VentaDAO: Error en rollback: " + re.getMessage());
                }
            }
            throw new ModelException("Error al obtener ventas: " + e.getMessage(), e);
        }
    }

    @Override
    public Venta actualizarVenta(Venta venta) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Venta updatedVenta = entityManager.merge(venta);
            transaction.commit();
            logger.info("Venta actualizada con éxito.");
            return updatedVenta;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al actualizar la venta", e);
            throw new ModelException("Error al actualizar venta", e);
        }
    }

    @Override
    public Venta eliminarVenta(long id) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Venta venta = entityManager.find(Venta.class, id);
            if (venta != null) {
                entityManager.remove(venta);
                logger.info("Venta eliminada con éxito.");
            }
            transaction.commit();
            return venta;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al eliminar la venta", e);
            throw new ModelException("Error al eliminar venta", e);
        }
    }

    /**
     * Guarda una venta en la base de datos usando JPA.
     */
    public Venta guardarVenta(double total, String metodoPago, Cliente cliente, Usuario usuario) {

        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Venta venta = new Venta();
            venta.setFecha(Date.valueOf(LocalDate.now()));
            venta.setTotal(total);
            venta.setMetodoPago(metodoPago);
            venta.setEstado("Pendiente");  // O el estado que prefieras
            venta.setCliente(cliente);
            venta.setUsuario(usuario);

            entityManager.persist(venta);  // Guardar la venta
            tx.commit();  // Confirmar la transacción
            return venta;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Guarda un detalle de venta en la base de datos usando JPA.
     */
    @Override
    public void guardarDetalleVenta(Venta ventaId, DetalleVenta detalle) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            detalle.setVenta(ventaId);
            entityManager.persist(detalle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ModelException("Error al guardar detalle de venta", e);
        }
    }

    @Override
    public List<Venta> buscarVentasPorCliente(String nombreCliente) throws ModelException {
        try {
            String jpql = "SELECT v FROM Venta v JOIN v.cliente c "
                    + "WHERE LOWER(c.nombreCompleto) LIKE LOWER(:nombre) "
                    + "ORDER BY v.fecha DESC";

            return entityManager.createQuery(jpql, Venta.class)
                    .setParameter("nombre", "%" + nombreCliente + "%")
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar ventas por cliente", e);
            throw new ModelException("Error al buscar ventas por cliente", e);
        }
    }

    @Override
    public List<Venta> filtrarVentas(Date fechaInicio, Date fechaFin, String estado,
            Double precioMin, Double precioMax) throws ModelException {
        try {
            StringBuilder jpql = new StringBuilder("SELECT v FROM Venta v WHERE 1=1");

            if (fechaInicio != null && fechaFin != null) {
                jpql.append(" AND v.fecha BETWEEN :fechaInicio AND :fechaFin");
            }
            if (estado != null && !estado.isEmpty()) {
                jpql.append(" AND v.estado = :estado");
            }
            if (precioMin != null) {
                jpql.append(" AND v.total >= :precioMin");
            }
            if (precioMax != null) {
                jpql.append(" AND v.total <= :precioMax");
            }

            jpql.append(" ORDER BY v.fecha DESC");

            var query = entityManager.createQuery(jpql.toString(), Venta.class);

            if (fechaInicio != null && fechaFin != null) {
                query.setParameter("fechaInicio", fechaInicio);
                query.setParameter("fechaFin", fechaFin);
            }
            if (estado != null && !estado.isEmpty()) {
                query.setParameter("estado", estado);
            }
            if (precioMin != null) {
                query.setParameter("precioMin", precioMin);
            }
            if (precioMax != null) {
                query.setParameter("precioMax", precioMax);
            }

            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al filtrar ventas", e);
            throw new ModelException("Error al filtrar ventas", e);
        }
    }

    @Override
    public Venta obtenerVentaPorId(Long id) throws ModelException {
        try {
            Venta venta = entityManager.find(Venta.class, id);
            if (venta != null) {
                // Inicializar colecciones lazy
                venta.getDetallesVenta().size();
                if (venta.getEnvios() != null) {
                    venta.getEnvios().size();
                }
            }
            return venta;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener venta por ID", e);
            throw new ModelException("Error al obtener venta por ID", e);
        }
    }

    @Override
    public void actualizarEstadoVenta(Long ventaId, String nuevoEstado) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Venta venta = entityManager.find(Venta.class, ventaId);
            if (venta == null) {
                throw new ModelException("Venta no encontrada con ID: " + ventaId);
            }

            venta.setEstado(nuevoEstado);
            entityManager.merge(venta);

            transaction.commit();
            logger.info("Estado de venta actualizado exitosamente. ID: " + ventaId);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al actualizar estado de venta", e);
            throw new ModelException("Error al actualizar estado de venta", e);
        }
    }

// Método auxiliar para cerrar recursos
    private void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

// Método auxiliar para manejar transacciones
    private void executeInTransaction(Runnable action) throws ModelException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            action.run();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ModelException("Error en la transacción", e);
        }
    }

    @Override
    public void actualizarPersonalizacionDetalle(Long detalleId, String nuevaPersonalizacion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
