package the305labs.inventario.service;

import the305labs.inventario.entity.Sucursal;
import the305labs.inventario.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> getAllSucursales() {
        return sucursalRepository.findAll();
    }

    public Sucursal getSucursalById(Integer id) {
        return sucursalRepository.findById(id).orElse(null);
    }

    public Sucursal createSucursal(Sucursal input) {
        input.setCreadoEn(LocalDateTime.now());
        return sucursalRepository.save(input);
    }

    public Sucursal updateSucursal(Integer id, Sucursal input) {
        Optional<Sucursal> optionalSucursal = sucursalRepository.findById(id);
        if (optionalSucursal.isEmpty()) {
            return null;
        }

        Sucursal existing = optionalSucursal.get();
        existing.setNombre(input.getNombre());
        existing.setUbicacion(input.getUbicacion());
        // No modificamos creadoEn para mantener la fecha original
        return sucursalRepository.save(existing);
    }

    public boolean deleteSucursal(Integer id) {
        if (sucursalRepository.existsById(id)) {
            sucursalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
