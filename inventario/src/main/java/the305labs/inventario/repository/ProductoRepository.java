package the305labs.inventario.repository;

import the305labs.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto, Long> {}
