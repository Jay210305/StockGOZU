package the305labs.inventario.repository;

import the305labs.inventario.entity.Inventario;
import the305labs.inventario.entity.InventarioPK;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, InventarioPK> {
    List<Inventario> findBySucursalId(Integer sucursalId);
}