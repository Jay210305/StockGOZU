package the305labs.inventario.repository;

import the305labs.inventario.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {}