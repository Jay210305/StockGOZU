package the305labs.inventario.service;

import the305labs.inventario.dto.UsuarioDTO;
import the305labs.inventario.entity.Usuario;
import the305labs.inventario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder    = passwordEncoder;
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> getUsuarioById(Integer id) {
        return usuarioRepository.findById(Long.valueOf(id))
                .map(UsuarioDTO::fromEntity);
    }

    public UsuarioDTO createUsuario(UsuarioDTO dto) {
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El username ya está en uso");
        }
        // Convierte DTO a entidad codificando la contraseña
        Usuario entity = dto.toEntity(passwordEncoder);
        Usuario saved  = usuarioRepository.save(entity);
        return UsuarioDTO.fromEntity(saved);
    }

    public Optional<UsuarioDTO> updateUsuario(Integer id, UsuarioDTO dto) {
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

    public boolean deleteUsuario(Integer id) {
        if (usuarioRepository.existsById(Long.valueOf(id))) {
            usuarioRepository.deleteById(Long.valueOf(id));
            return true;
        }
        return false;
    }
}