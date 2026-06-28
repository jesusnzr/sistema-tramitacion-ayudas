package com.consultoria.ayudas_api.controller;

import com.consultoria.ayudas_api.model.Solicitud;
import com.consultoria.ayudas_api.service.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //esto sirve para indicar que es un punto de entrada para peticiones HTTP y lo que devuelvan sus metodos debe convertirse en JSON
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;


    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;

    }

    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestParam String descripcion,
                                           @RequestParam Double importe,
                                           @RequestParam Long usuarioId) {
        Solicitud nueva = solicitudService.crearSolicitud(descripcion, importe, usuarioId);
        return ResponseEntity.ok(nueva);
    }


    @PutMapping("/{id}/estado")
    public ResponseEntity<Solicitud> cambiarEstado(@PathVariable Long id,
                                                   @RequestParam String nuevoEstado,
                                                   @RequestParam Long usuarioId) {
        Solicitud actualizada = solicitudService.cambiarEstado(id, nuevoEstado, usuarioId);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/aprobadas/usuario/{usuarioId}")
    public ResponseEntity<List<Solicitud>> obtenerAprobadas(@PathVariable Long usuarioId) {
        List<Solicitud> aprobadas = solicitudService.obtenerSolicitudesAprobadasPorUsuario(usuarioId);
        return ResponseEntity.ok(aprobadas);
    }
}