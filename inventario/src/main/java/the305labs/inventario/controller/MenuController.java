package the305labs.inventario.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import the305labs.inventario.entity.MenuItem;

import java.util.List;

@Controller
public class MenuController {

    @GetMapping({"/menu"})
    public String mostrarMenu(Model model) {
        // Verificar si el usuario está autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            List<MenuItem> menuItems = List.of(
                    new MenuItem("Inicio",    "/inicio"),
                    new MenuItem("Productos", "/productos"),
                    new MenuItem("Usuarios",  "/usuarios"),
                    new MenuItem("Sucursales","/sucursales"),
                    new MenuItem("Inventario","/inventario")    // <-- Nuevo
            );
            model.addAttribute("menuItems", menuItems);
            return "menu"; // Redirige a la página del menú si está autenticado
        }
        // Si no está autenticado, redirigir al login
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/login?logout";
    }

    @GetMapping("/")
    public String Inicio() {
        return "redirect:/inicio";
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
