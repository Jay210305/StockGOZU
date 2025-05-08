package the305labs.inventario.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import the305labs.inventario.entity.MenuItem;


import java.util.List;

@Controller
public class MenuController {

    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    @GetMapping({"/", "/menu"})
    public String mostrarMenu(Model model) {
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
        return "redirect:/login";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/login?logout";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inicio")
    public String redirigirInicio() {
        return "inicio";
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    @GetMapping("/productos")
    public String redirigirProductos() {
        return "productos";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios")
    public String redirigirUsuarios() {
        return "usuarios";
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    @GetMapping("/sucursales")
    public String redirigirSucursales() {
        return "sucursales";
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    @GetMapping("/inventario")
    public String redirigirInventario() {
        return "inventario";
    }
}