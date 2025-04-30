package the305labs.inventario.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario")
@IdClass(InventarioPK.class)
public class Inventario {
    @Id
    private Integer sucursalId;

    @Id
    private Long productoId;

    private Integer cantidad;

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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
