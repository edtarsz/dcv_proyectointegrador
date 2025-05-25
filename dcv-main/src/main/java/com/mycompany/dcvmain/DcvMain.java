package com.mycompany.dcvmain;

import com.mycompany.dcvconexion.InitialConfig;
import com.mycompany.dcventidades.*;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.detalleventa.DetalleVentaBO;
import com.mycompany.dcvnegocio.detalleventa.IDetalleVentaBO;
import com.mycompany.dcvnegocio.envio.EnvioBO;
import com.mycompany.dcvnegocio.envio.IEnvioBO;
import com.mycompany.dcvnegocio.producto.IProductoBO;
import com.mycompany.dcvnegocio.producto.ProductoBO;
import com.mycompany.dcvnegocio.usuario.IUsuarioBO;
import com.mycompany.dcvnegocio.usuario.UsuarioBO;
import com.mycompany.dcvnegocio.venta.IVentaBO;
import com.mycompany.dcvnegocio.venta.VentaBO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ramos
 */
public class DcvMain {

    private static IUsuarioBO usuarioBO;
    private static IVentaBO ventaBO;
    private static IProductoBO productoBO;
    private static IDetalleVentaBO detalleVentaBO;
    private static IEnvioBO envioBO;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ControllerException {
        InitialConfig.iniciarConexion();

//        usuarioBO = new UsuarioBO();
//        ventaBO = new VentaBO();
//        productoBO = new ProductoBO();
//        detalleVentaBO = new DetalleVentaBO();
//        envioBO = new EnvioBO();
//
//        // Crear productos de ejemplo
//        List<Producto> productos = List.of(
//                new Producto("Taza", "Taza personalizada", 100.0, new ArrayList<>(), new ArrayList<>()),
//                new Producto("Vaso de Plastico", "Vaso de Plastico personalizado", 100.0, new ArrayList<>(), new ArrayList<>()),
//                new Producto("Sobre", "Sobre personalizado", 50.0, new ArrayList<>(), new ArrayList<>()),
//                new Producto("Bolsa", "Bolsa personalizada", 80.0, new ArrayList<>(), new ArrayList<>()),
//                new Producto("Termo", "Termo personalizado", 300.0, new ArrayList<>(), new ArrayList<>()),
//                new Producto("Vaso de Cristal", "Vaso de Cristal personalizado", 250.0, new ArrayList<>(), new ArrayList<>())
//        );
//
//        Venta venta = new Venta();
//
//        for (Producto p : productos) {
//            productoBO.crearProducto(p);
//        }
//
//        // Crear usuario
//        Usuario usuario = new Usuario("cliente01", "1234", "Admin", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        usuarioBO.crearUsuario(usuario);
//
//        List<DetalleVenta> detallesVenta = new ArrayList<>();
//        boolean agregarMas;
//
//        do {
//            System.out.println("Seleccione un producto para la venta:");
//            for (int i = 0; i < productos.size(); i++) {
//                System.out.println((i + 1) + ". " + productos.get(i).getNombre() + " - $" + productos.get(i).getPrecio());
//            }
//
//            System.out.print("Ingrese el número del producto: ");
//            int productoIndex = scanner.nextInt() - 1;
//            scanner.nextLine();
//
//            Producto productoSeleccionado = productos.get(productoIndex);
//            System.out.print("Ingrese la cantidad: ");
//            int cantidad = scanner.nextInt();
//            scanner.nextLine();
//
//            System.out.print("¿Es personalizado? (s/n): ");
//            boolean personalizado = scanner.nextLine().equalsIgnoreCase("s");
//            String personalizacion = "";
//            if (personalizado) {
//                System.out.print("Ingrese la personalización: ");
//                personalizacion = scanner.nextLine();
//            }
//
//            DetalleVenta detalle = new DetalleVenta(1, productoSeleccionado.getPrecio(), cantidad * productoSeleccionado.getPrecio(), productoSeleccionado, personalizado, personalizacion, venta);
//            detallesVenta.add(detalle);
//
//            System.out.print("¿Desea agregar otro producto? (s/n): ");
//            agregarMas = scanner.nextLine().equalsIgnoreCase("s");
//        } while (agregarMas);
//
//        venta.setDetallesVenta(detallesVenta);
//        venta.setFecha(Date.valueOf(LocalDate.now()));
//        venta.setUsuario(usuario);
//        venta.setEstado("En proceso");
//        venta.setMetodoPago("Efectivo");
//
//        // Preguntar sobre el envío
//        System.out.print("¿La venta requiere envío? (s/n): ");
//        boolean requiereEnvio = scanner.nextLine().equalsIgnoreCase("s");
//        List<Envio> envios = new ArrayList<>();
//
//        System.out.print("Ingrese el nombre del cliente: ");
//        String nombreCliente = scanner.nextLine();
//        System.out.print("Ingrese el teléfono del cliente: ");
//        String telefonoCliente = scanner.nextLine();
//        System.out.print("Ingrese el correo del cliente (opcional): ");
//        String correoCliente = scanner.nextLine();
//        if (correoCliente == "") {
//            correoCliente = null;
//        }
//        Cliente cliente1 = new Cliente(nombreCliente, telefonoCliente, correoCliente);
//
//        if (requiereEnvio) {
//            System.out.print("Ingrese la dirección de entrega: ");
//            String direccion = scanner.nextLine();
//            System.out.print("Ingrese el costo del envío: ");
//            double costoEnvio = scanner.nextDouble();
//            scanner.nextLine();
//
//            Envio envio = new Envio();
//            envio.setDireccionEntrega(direccion);
//            envio.setCliente(cliente1);
//            envio.setCosto(costoEnvio);
//            envio.setVenta(venta);
//            envios.add(envio);
//        }
//
//        venta.setEnvios(envios);
//        venta.setCliente(cliente1);
//        
//        double totalDetalles = 0;
//        for (DetalleVenta detalle : detallesVenta) {
//            totalDetalles += detalle.getSubtotal();
//        }
//
//        double costoEnvio = 0;
//        if (!envios.isEmpty()) {
//            for (Envio envio : envios) {
//                costoEnvio += envio.getCosto();
//            }
//        }
//        
//        venta.setTotal(totalDetalles + costoEnvio);
//        ventaBO.crearVenta(venta);
//
//        System.out.println("Venta registrada exitosamente.");
//    }
    }
}
