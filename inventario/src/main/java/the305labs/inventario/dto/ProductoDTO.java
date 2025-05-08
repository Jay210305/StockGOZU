package the305labs.inventario.dto;

import jakarta.validation.constraints.*;
import org.springframework.cglib.core.internal.Function;
import org.springframework.http.ResponseEntity;
import the305labs.inventario.entity.Producto;

import java.time.LocalDateTime;
import java.util.Optional;

public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 50, message = "El código no puede tener más de 50 caracteres")
    private String codigo;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "La unidad de medida es obligatoria")
    private Producto.Unidad unidadMedida;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de compra debe ser al menos 0.01")
    private Double precioCompra;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de venta debe ser al menos 0.01")
    private Double precioVenta;

    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    public ProductoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Producto.Unidad getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(Producto.Unidad unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    public Producto toEntity() {
        Producto p = new Producto();
        if (this.id != null) p.setId(this.id);
        p.setNombre(this.nombre);
        p.setDescripcion(this.descripcion);
        p.setCodigo(this.codigo);
        p.setCategoria(this.categoria);
        p.setUnidadMedida(this.unidadMedida);
        p.setPrecioCompra(this.precioCompra);
        p.setPrecioVenta(this.precioVenta);
        return p;
    }

    public static ProductoDTO fromEntity(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setCodigo(p.getCodigo());
        dto.setCategoria(p.getCategoria());
        dto.setUnidadMedida(p.getUnidadMedida());
        dto.setPrecioCompra(p.getPrecioCompra());
        dto.setPrecioVenta(p.getPrecioVenta());
        dto.setCreadoEn(p.getCreadoEn());
        dto.setActualizadoEn(p.getActualizadoEn());
        return dto;
    }
}