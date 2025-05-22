package the305labs.inventario.service;

import the305labs.inventario.dto.SucursalDTO;
import the305labs.inventario.entity.Sucursal;
import the305labs.inventario.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public List<SucursalDTO> getAllSucursales() {
        return sucursalRepository.findAll()
                .stream()
                .map(SucursalDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<SucursalDTO> getSucursalById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de sucursal es obligatorio");
        }
        return sucursalRepository.findById(id)
                .map(SucursalDTO::fromEntity);
    }

    public SucursalDTO createSucursal(SucursalDTO input) {
        if (input == null) {
            throw new IllegalArgumentException("Los datos de la sucursal son obligatorios");
        }
        if (input.getNombre() == null || input.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la sucursal es obligatorio");
        }
        if (input.getUbicacion() == null || input.getUbicacion().isBlank()) {
            throw new IllegalArgumentException("La ubicación de la sucursal es obligatoria");
        }
        Sucursal entity = input.toEntity();
        entity.setCreadoEn(LocalDateTime.now());
        Sucursal saved = sucursalRepository.save(entity);
        return SucursalDTO.fromEntity(saved);
    }

    public Optional<SucursalDTO> updateSucursal(Integer id, SucursalDTO input) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de sucursal es obligatorio");
        }
        if (input == null) {
            throw new IllegalArgumentException("Los datos de la sucursal son obligatorios");
        }
        return sucursalRepository.findById(id).map(existing -> {
            if (input.getNombre() == null || input.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre de la sucursal es obligatorio");
            }
            if (input.getUbicacion() == null || input.getUbicacion().isBlank()) {
                throw new IllegalArgumentException("La ubicación de la sucursal es obligatoria");
            }
            existing.setNombre(input.getNombre());
            existing.setUbicacion(input.getUbicacion());
            Sucursal updated = sucursalRepository.save(existing);
            return SucursalDTO.fromEntity(updated);
        });
    }

    public void deleteSucursal(Integer id) {
        Sucursal s = sucursalRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NoSuchElementException("No existe sucursal " + id));
        s.setActive(false);
        sucursalRepository.save(s);
    }

}