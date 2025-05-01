package the305labs.inventario.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(InventarioPK.class)
@Table(name = "inventario")
public class Inventario {
    @Id
    private Integer sucursalId;
    @Id
    private Long productoId;
    private Integer cantidad = 0;
    private Integer stockMinimo = 0;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    @PreUpdate
    public void setTimestamp() {
        actualizadoEn = LocalDateTime.now();
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
