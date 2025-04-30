package the305labs.inventario.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_inventario")
public class MovimientoInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sucursalId;
    private Long productoId;
    private Integer usuarioId;

    @Enumerated(EnumType.STRING)
    private Tipo tipo; // INGRESO, SALIDA

    private Integer cantidad;
    private LocalDateTime fecha = LocalDateTime.now();
    private String comentario;

    public enum Tipo { INGRESO, SALIDA }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
