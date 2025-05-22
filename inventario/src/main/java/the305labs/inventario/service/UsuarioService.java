package the305labs.inventario.service;

import the305labs.inventario.dto.UsuarioDTO;
import the305labs.inventario.entity.Usuario;
import the305labs.inventario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> getUsuarioById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del usuario es obligatorio");
        }
        return usuarioRepository.findById(Long.valueOf(id))
                .map(UsuarioDTO::fromEntity);
    }

    public UsuarioDTO createUsuario(UsuarioDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos del usuario son obligatorios");
        }
        if (dto.getUsername() == null || dto.getUsername().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario entity = dto.toEntity(passwordEncoder);
        Usuario saved = usuarioRepository.save(entity);
        return UsuarioDTO.fromEntity(saved);
    }

    public Optional<UsuarioDTO> updateUsuario(Integer id, UsuarioDTO dto) {
        if (id == null || dto == null) {
            throw new IllegalArgumentException("ID y datos del usuario son obligatorios");
        }
        return usuarioRepository.findById(Long.valueOf(id)).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setUsername(dto.getUsername());
            existing.setRol(dto.getRol());
            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                existing.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
            }
            Usuario updated = usuarioRepository.save(existing);
            return UsuarioDTO.fromEntity(updated);
        });
    }

    public void deleteUsuario(Integer id) {
        if (id == null) throw new IllegalArgumentException("El ID del usuario es obligatorio");
        Usuario u = usuarioRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID " + id));
        u.setActive(false);
        usuarioRepository.save(u);
    }
}