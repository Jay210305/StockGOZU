package the305labs.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import the305labs.inventario.entity.MenuItem;

import java.util.List;

@Controller
public class MenuController {

    @GetMapping({"/", "/menu"})
    public String mostrarMenu(Model model) {
        List<MenuItem> menuItems = List.of(
                new MenuItem("Inicio",    "/inicio"),
                new MenuItem("Productos", "/productos"),
                new MenuItem("Usuarios",  "/usuarios"),
                new MenuItem("Sucursales","/sucursales"),
                new MenuItem("Inventario","/inventario")    // <-- Nuevo
        );
        model.addAttribute("menuItems", menuItems);
        return "menu";
    }

    @GetMapping("/inicio")
    public String redirigirInicio() {
        return "inicio";
    }

    @GetMapping("/productos")
    public String redirigirProductos() {
        return "productos";
    }

    @GetMapping("/usuarios")
    public String redirigirUsuarios() {
        return "usuarios";
    }

    @GetMapping("/sucursales")
    public String redirigirSucursales() {
        return "sucursales";
    }

    // Nuevo mapping para inventario
    @GetMapping("/inventario")
    public String redirigirInventario() {
        return "inventario";
    }
}
