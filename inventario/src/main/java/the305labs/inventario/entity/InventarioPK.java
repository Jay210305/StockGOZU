package the305labs.inventario.entity;

import java.io.Serializable;
import java.util.Objects;

public class InventarioPK implements Serializable {
    private Integer sucursalId;
    private Long productoId;

    public InventarioPK() {
    }

    public InventarioPK(Integer sucursalId, Long productoId) {
        this.sucursalId = sucursalId;
        this.productoId = productoId;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventarioPK that)) return false;
        return Objects.equals(sucursalId, that.sucursalId)
                && Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sucursalId, productoId);
    }
}