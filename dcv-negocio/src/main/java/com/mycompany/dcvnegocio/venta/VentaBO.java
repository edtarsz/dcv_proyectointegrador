package com.mycompany.dcvnegocio.venta;

import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvconexion.Conexion;
import com.mycompany.dcvconexion.IConexion;
import com.mycompany.dcvconexion.ModelException;
import com.mycompany.dcvdao.DAOFactory;
import com.mycompany.dcvdao.venta.IVentaDAO;
import com.mycompany.dcventidades.Cliente;
import com.mycompany.dcventidades.DetalleVenta;
import com.mycompany.dcventidades.Usuario;
import com.mycompany.dcventidades.Venta;
import com.mycompany.dcvexceptions.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Persistence;
import java.sql.Date;
import java.util.List;

public class VentaBO implements IVentaBO {

    private static final Logger logger = Logger.getLogger(VentaBO.class.getName());
    private IVentaDAO ventaDAO;
    private IConexion conexion;

    public VentaBO() {
        try {
            System.out.println("VentaBO: Iniciando construcción...");
            conexion = new Conexion(Persistence.createEntityManagerFactory("detallesConVasosPU"));
            System.out.println("VentaBO: Conexión creada");

            this.ventaDAO = DAOFactory.instanciaVentaDAO(conexion);
            System.out.println("VentaBO: VentaDAO instanciado");
        } catch (Exception ex) {
            System.err.println("VentaBO: Error en constructor: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public Venta crearVenta(Venta venta) throws ControllerException {
        try {
            return ventaDAO.crearVenta(venta);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error al crear la venta", ex);
        }
    }

    @Override
    public Venta guardarVenta(double total, String metodoPago, Cliente clienteId, Usuario usuarioId) throws ControllerException {
        try {
            return ventaDAO.guardarVenta(total, metodoPago, clienteId, usuarioId);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error al guardar la venta", ex);
        }
    }

    @Override
    public void guardarDetalleVenta(Venta ventaId, DetalleVenta detalle) throws ControllerException {
        try {
            ventaDAO.guardarDetalleVenta(ventaId, detalle);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error al guardar el detalle de venta", ex);
        }
    }

    @Override
    public List<Venta> obtenerTodasLasVentas() throws ControllerException {
        try {
            System.out.println("VentaBO: Intentando obtener todas las ventas");
            if (ventaDAO == null) {
                throw new ControllerException("VentaDAO no está inicializado");
            }

            List<Venta> ventas = ventaDAO.obtenerTodasLasVentas();
            System.out.println("VentaBO: Ventas obtenidas: " + (ventas != null ? ventas.size() : "null"));
            return ventas;
        } catch (Exception ex) {
            System.err.println("VentaBO: Error al obtener ventas: " + ex.getMessage());
            ex.printStackTrace();
            throw new ControllerException("Error al obtener todas las ventas", ex);
        }
    }

    @Override
    public List<Venta> buscarVentasPorCliente(String nombreCliente) throws ControllerException {
        try {
            return ventaDAO.buscarVentasPorCliente(nombreCliente);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error al buscar ventas por cliente", ex);
        }
    }

    @Override
    public List<Venta> filtrarVentas(Date fechaInicio, Date fechaFin, String estado,
            Double precioMin, Double precioMax) throws ControllerException {
        try {
            return ventaDAO.filtrarVentas(fechaInicio, fechaFin, estado, precioMin, precioMax);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Venta obtenerVentaPorId(Long id) throws ControllerException {
        try {
            return ventaDAO.obtenerVentaPorId(id);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error al obtener venta por ID", ex);
        }
    }

    @Override
    public void actualizarEstadoVenta(Long ventaId, String nuevoEstado) throws ControllerException {
        try {
            ventaDAO.actualizarEstadoVenta(ventaId, nuevoEstado);
        } catch (ModelException ex) {
            Logger.getLogger(VentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error al actualizar estado de la venta", ex);
        }
    }
}
