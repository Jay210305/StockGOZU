package the305labs.inventario.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import the305labs.inventario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.active = true")
    List<Usuario> findAllActivos();

    @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.active = true")
    Optional<Usuario> findByIdAndActiveTrue(@Param("id") Integer id);

    Optional<Usuario> findByUsername(String username);
}