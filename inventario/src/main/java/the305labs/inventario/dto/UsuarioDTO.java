package the305labs.inventario.dto;

import the305labs.inventario.entity.Usuario;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class UsuarioDTO {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 20, message = "El username debe tener entre 4 y 20 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Usuario.Rol rol;

    private LocalDateTime creadoEn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario.Rol getRol() {
        return rol;
    }

    public void setRol(Usuario.Rol rol) {
        this.rol = rol;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Usuario toEntity(org.springframework.security.crypto.password.PasswordEncoder encoder) {
        Usuario u = new Usuario();
        if (this.id != null) u.setId(this.id);
        u.setNombre(this.nombre);
        u.setUsername(this.username);
        u.setPasswordHash(encoder.encode(this.password));
        u.setRol(this.rol);
        u.setCreadoEn(this.creadoEn != null ? this.creadoEn : LocalDateTime.now());
        return u;
    }

    public static UsuarioDTO fromEntity(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setUsername(u.getUsername());
        dto.setRol(u.getRol());
        dto.setCreadoEn(u.getCreadoEn());
        return dto;
    }
}