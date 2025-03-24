
import com.mycompany.dcventidades.Producto;
import com.mycompany.dcvexceptions.ControllerException;
import com.mycompany.dcvnegocio.producto.IProductoBO;
import com.mycompany.dcvnegocio.producto.ProductoBO;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author crist
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ControllerException {
        // TODO code application logic here
            ProductoBO productoBO = new ProductoBO(); // Ajusta según tu estructura
            List<Producto> productos = productoBO.obtenerTodosLosProductos();
            System.out.println("Productos encontrados: " + productos);

    }
    
}
