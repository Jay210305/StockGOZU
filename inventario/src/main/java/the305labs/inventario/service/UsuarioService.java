package the305labs.inventario.service;


import the305labs.inventario.entity.Usuario;
import the305labs.inventario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario createUsuario(Usuario userInput) {
        if (usuarioRepository.findByUsername(userInput.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        userInput.setPasswordHash(passwordEncoder.encode(userInput.getPasswordHash()));
        return usuarioRepository.save(userInput);
    }

    public Usuario updateUsuario(Long id, Usuario userInput) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return null;
        }

        Usuario usuario = optionalUsuario.get();

        usuario.setNombre(userInput.getNombre());
        usuario.setUsername(userInput.getUsername());
        usuario.setRol(userInput.getRol());

        if (userInput.getPasswordHash() != null && !userInput.getPasswordHash().isBlank()) {
            usuario.setPasswordHash(passwordEncoder.encode(userInput.getPasswordHash()));
        }

        return usuarioRepository.save(usuario);
    }

    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
