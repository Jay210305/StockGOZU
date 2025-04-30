package the305labs.inventario.entity;

import java.io.Serializable;
import java.util.Objects;

public class InventarioPK implements Serializable {
    private Integer sucursalId;
    private Long productoId;

    public InventarioPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InventarioPK that)) return false;
        return Objects.equals(sucursalId, that.sucursalId) && Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sucursalId, productoId);
    }
}
