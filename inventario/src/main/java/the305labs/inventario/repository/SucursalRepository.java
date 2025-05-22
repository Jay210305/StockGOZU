package the305labs.inventario.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import the305labs.inventario.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    @Query("SELECT s FROM Sucursal s WHERE s.active = true")
    List<Sucursal> findAllActivas();

    @Query("SELECT s FROM Sucursal s WHERE s.id = :id AND s.active = true")
    Optional<Sucursal> findByIdAndActiveTrue(@Param("id") Integer id);
}
