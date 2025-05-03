package the305labs.inventario.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
public class Producto {
    public enum Unidad { kg, litro, unidad, metro, caja, otro }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    @Column(unique = true, nullable = false)
    private String codigo;
    private String categoria;
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private Unidad unidadMedida = Unidad.unidad;
    private Double precioCompra;
    private Double precioVenta;

    @Transient
    private Integer stockInicial = 0;
    
    public Integer getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
    }

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist @PreUpdate
    public void setTimestamp() {
        actualizadoEn = LocalDateTime.now();
    }

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

    public Unidad getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(Unidad unidadMedida) {
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

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}