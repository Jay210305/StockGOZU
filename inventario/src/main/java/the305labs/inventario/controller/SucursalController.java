package the305labs.inventario.controller;

import the305labs.inventario.dto.SucursalDTO;
import the305labs.inventario.service.SucursalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> getAllSucursales() {
        return ResponseEntity.ok(sucursalService.getAllSucursales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> getSucursalById(@PathVariable Integer id) {
        return sucursalService.getSucursalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> createSucursal(@Valid @RequestBody SucursalDTO input) {
        return ResponseEntity.ok(sucursalService.createSucursal(input));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> updateSucursal(@PathVariable Integer id,
                                                      @Valid @RequestBody SucursalDTO input) {
        return sucursalService.updateSucursal(id, input)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable Integer id) {
        if (sucursalService.deleteSucursal(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}