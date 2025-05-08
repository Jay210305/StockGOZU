package the305labs.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import the305labs.inventario.entity.MenuItem;
import the305labs.inventario.entity.Usuario;
import the305labs.inventario.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class MenuController {

    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    @GetMapping({"/menu"})
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
            return "menu";
        }
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
    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/")
    public String Inicio() {
        return "redirect:/inicio";
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
    @GetMapping("/movimientoInv")
    public String redirigirMovimientoInv() {
        return "movimientoInv";
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    @GetMapping("/inventario")
    public String redirigirInventario(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(nombreUsuario);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            model.addAttribute("usuarioActual", usuario.getUsername());
            model.addAttribute("IdusuarioActual", usuario.getId());
        } else {
            model.addAttribute("usuarioActual", nombreUsuario);
            model.addAttribute("IdusuarioActual", 0);
        }

        return "inventario";
    }
}