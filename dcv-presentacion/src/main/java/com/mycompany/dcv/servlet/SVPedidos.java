/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.dcv.servlet;

import com.firework.gson.JsonArray;
import com.firework.gson.JsonObject;
import com.mycompany.dcventidades.Venta;
import com.mycompany.dcvnegocio.venta.IVentaBO;
import com.mycompany.dcvnegocio.venta.VentaBO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author haloa
 */
@WebServlet(name = "SVPedidos", urlPatterns = {"/SVPedidos"})
public class SVPedidos extends HttpServlet {

    private IVentaBO ventaBO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            System.out.println("SVPedidos: Inicializando servlet...");
            ventaBO = new VentaBO();
            System.out.println("SVPedidos: VentaBO inicializado correctamente");
        } catch (Exception e) {
            System.err.println("SVPedidos: Error en init(): " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n=== Iniciando procesamiento de pedidos ===");
        try {
            // Verificar si ventaBO está inicializado
            if (ventaBO == null) {
                throw new ServletException("VentaBO no está inicializado");
            }

            System.out.println("SVPedidos: Intentando obtener ventas...");
            List<Venta> ventas = ventaBO.obtenerTodasLasVentas();

            System.out.println("SVPedidos: Ventas obtenidas: " + (ventas != null ? ventas.size() : "null"));

            if (ventas == null) {
                ventas = new ArrayList<>();
                System.out.println("SVPedidos: Se creó una lista vacía porque el resultado era null");
            }

            // Verificar el contenido de las ventas
            for (Venta venta : ventas) {
                System.out.println("Venta ID: " + venta.getId()
                        + ", Cliente: " + (venta.getCliente() != null ? venta.getCliente().getNombreCompleto() : "null")
                        + ", Detalles: " + (venta.getDetallesVenta() != null ? venta.getDetallesVenta().size() : "null"));
            }

            request.setAttribute("ventas", ventas);

            if (!ventas.isEmpty()) {
                request.setAttribute("ventaSeleccionada", ventas.get(0));
                System.out.println("SVPedidos: Primera venta seleccionada, ID: " + ventas.get(0).getId());
            }

            System.out.println("SVPedidos: Redirigiendo a Pedidos.jsp");
            request.getRequestDispatcher("/Pedidos.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("SVPedidos: Error en doGet(): " + e.getMessage());
            e.printStackTrace();

            // Asegurar que siempre haya una lista vacía
            request.setAttribute("ventas", new ArrayList<>());
            request.setAttribute("error", "Error al cargar los pedidos: " + e.getMessage());

            System.out.println("SVPedidos: Redirigiendo a Pedidos.jsp con error");
            request.getRequestDispatcher("/Pedidos.jsp").forward(request, response);
        }
        System.out.println("=== Fin del procesamiento de pedidos ===\n");
    }

    private void cargarVistaPrincipal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("Cargando pedidos...");
            List<Venta> ventas = ventaBO.obtenerTodasLasVentas();
            System.out.println("Ventas encontradas: " + (ventas != null ? ventas.size() : 0));

            request.setAttribute("ventas", ventas);

            if (ventas != null && !ventas.isEmpty()) {
                request.setAttribute("ventaSeleccionada", ventas.get(0));
                System.out.println("Venta seleccionada ID: " + ventas.get(0).getId());
            }

            request.getRequestDispatcher("/Pedidos.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al cargar la vista principal", e);
        }
    }

    private void obtenerDetalleVenta(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Venta venta = ventaBO.obtenerVentaPorId(id);

            if (venta != null) {
                jsonResponse.addProperty("success", true);
                jsonResponse.add("venta", convertirVentaAJson(venta));
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Venta no encontrada");
            }
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error al obtener detalles: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
    }

    private void buscarVentas(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            String termino = request.getParameter("termino");
            List<Venta> ventas = ventaBO.buscarVentasPorCliente(termino);

            jsonResponse.addProperty("success", true);
            JsonArray ventasArray = new JsonArray();
            for (Venta venta : ventas) {
                ventasArray.add(convertirVentaAJson(venta));
            }
            jsonResponse.add("ventas", ventasArray);
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error en la búsqueda: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
    }

    private void filtrarVentas(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            String tipo = request.getParameter("tipo");
            // Implementar lógica de filtrado según el tipo
            List<Venta> ventas = ventaBO.obtenerTodasLasVentas(); // Modificar según el filtro

            jsonResponse.addProperty("success", true);
            JsonArray ventasArray = new JsonArray();
            for (Venta venta : ventas) {
                ventasArray.add(convertirVentaAJson(venta));
            }
            jsonResponse.add("ventas", ventasArray);
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error al filtrar: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("actualizarEstado".equals(action)) {
                actualizarEstadoVenta(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } catch (Exception e) {
            manejarError(response, e);
        }
    }

    private void actualizarEstadoVenta(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            Long ventaId = Long.parseLong(request.getParameter("ventaId"));
            String nuevoEstado = request.getParameter("estado");

            ventaBO.actualizarEstadoVenta(ventaId, nuevoEstado);

            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Estado actualizado correctamente");
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error al actualizar estado: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
    }

    private JsonObject convertirVentaAJson(Venta venta) {
        JsonObject ventaJson = new JsonObject();
        ventaJson.addProperty("id", venta.getId());
        ventaJson.addProperty("fecha", venta.getFecha().toString());
        ventaJson.addProperty("total", venta.getTotal());
        ventaJson.addProperty("estado", venta.getEstado());

        JsonObject clienteJson = new JsonObject();
        clienteJson.addProperty("nombreCompleto", venta.getCliente().getNombreCompleto());
        ventaJson.add("cliente", clienteJson);

        JsonArray detallesArray = new JsonArray();
        venta.getDetallesVenta().forEach(detalle -> {
            JsonObject detalleJson = new JsonObject();
            detalleJson.addProperty("id", detalle.getId());
            detalleJson.addProperty("subtotal", detalle.getSubtotal());
            detalleJson.addProperty("personalizacion", detalle.getPersonalizacion());

            JsonObject productoJson = new JsonObject();
            productoJson.addProperty("nombre", detalle.getProducto().getNombre());
            detalleJson.add("producto", productoJson);

            detallesArray.add(detalleJson);
        });
        ventaJson.add("detallesVenta", detallesArray);

        return ventaJson;
    }

    private void manejarError(HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", false);
        jsonResponse.addProperty("message", e.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(jsonResponse.toString());
    }
}
