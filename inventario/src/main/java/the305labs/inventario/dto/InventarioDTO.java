package the305labs.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import the305labs.inventario.entity.Inventario;

import java.time.LocalDateTime;

public class InventarioDTO {
    private LocalDateTime actualizadoEn;
    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    @NotNull(message = "Sucursal ID is required")
    private Integer sucursalId;

    @NotNull(message = "Producto ID is required")
    private Long productoId;

    @Min(value = 0, message = "Cantidad must be zero or positive")
    private Integer cantidad;

    @Min(value = 0, message = "Stock mínimo must be zero or positive")
    private Integer stockMinimo;

    public InventarioDTO() {}

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

    public Inventario toEntity() {
        Inventario inv = new Inventario();
        inv.setSucursalId(this.sucursalId);
        inv.setProductoId(this.productoId);
        if (this.cantidad != null) {
            inv.setCantidad(this.cantidad);
        }
        if (this.stockMinimo != null) {
            inv.setStockMinimo(this.stockMinimo);
        }
        return inv;
    }

    public static InventarioDTO fromEntity(Inventario inv) {
        InventarioDTO dto = new InventarioDTO();
        dto.setSucursalId(inv.getSucursalId());
        dto.setProductoId(inv.getProductoId());
        dto.setCantidad(inv.getCantidad());
        dto.setStockMinimo(inv.getStockMinimo());
        dto.setActualizadoEn(inv.getActualizadoEn());
        return dto;
    }
}
