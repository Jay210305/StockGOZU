package the305labs.inventario.repository;

import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.InventarioPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, InventarioPK> {
}