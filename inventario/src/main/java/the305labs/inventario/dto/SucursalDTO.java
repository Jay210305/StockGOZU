package the305labs.inventario.dto;

import the305labs.inventario.entity.Sucursal;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class SucursalDTO {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    private LocalDateTime creadoEn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Sucursal toEntity() {
        Sucursal entity = new Sucursal();
        entity.setId(this.id);
        entity.setNombre(this.nombre);
        entity.setUbicacion(this.ubicacion);
        entity.setCreadoEn(this.creadoEn != null ? this.creadoEn : LocalDateTime.now());
        return entity;
    }

    public static SucursalDTO fromEntity(Sucursal s) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(s.getId());
        dto.setNombre(s.getNombre());
        dto.setUbicacion(s.getUbicacion());
        dto.setCreadoEn(s.getCreadoEn());
        return dto;
    }
}