package the305labs.inventario.service;

import the305labs.inventario.dto.SucursalDTO;
import the305labs.inventario.entity.Sucursal;
import the305labs.inventario.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        return sucursalRepository.findById(id).map(SucursalDTO::fromEntity);
    }

    public SucursalDTO createSucursal(SucursalDTO input) {
        Sucursal entity = input.toEntity();
        entity.setCreadoEn(LocalDateTime.now());
        return SucursalDTO.fromEntity(sucursalRepository.save(entity));
    }

    public Optional<SucursalDTO> updateSucursal(Integer id, SucursalDTO input) {
        return sucursalRepository.findById(id).map(existing -> {
            existing.setNombre(input.getNombre());
            existing.setUbicacion(input.getUbicacion());
            return SucursalDTO.fromEntity(sucursalRepository.save(existing));
        });
    }

    public boolean deleteSucursal(Integer id) {
        if (sucursalRepository.existsById(id)) {
            sucursalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
