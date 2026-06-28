package com.consultoria.ayudas_api.service;

import com.consultoria.ayudas_api.model.HistorialAuditoria;
import com.consultoria.ayudas_api.model.Solicitud;
import com.consultoria.ayudas_api.model.Usuario;
import com.consultoria.ayudas_api.repository.HistorialAuditoriaRepository;
import com.consultoria.ayudas_api.repository.SolicitudRepository;
import com.consultoria.ayudas_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistorialAuditoriaRepository auditoriaRepository;

    // Inyección de dependencias por constructor (Estándar de la industria)
    public SolicitudService(SolicitudRepository solicitudRepository,
                            UsuarioRepository usuarioRepository,
                            HistorialAuditoriaRepository auditoriaRepository) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.auditoriaRepository = auditoriaRepository;
    }

    public Solicitud crearSolicitud(String descripcion, Double importe, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        Solicitud nuevaSolicitud = new Solicitud(descripcion, importe, "RECIBIDA", usuario);

        return solicitudRepository.save(nuevaSolicitud);
    }

    public Solicitud cambiarEstado(Long solicitudId, String nuevoEstado, Long usuarioId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + solicitudId));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        String estadoAnterior = solicitud.getEstado();

        if ((nuevoEstado.equals("APROBADA") || nuevoEstado.equals("DENEGADA")) && estadoAnterior.equals("RECIBIDA")) {
            throw new RuntimeException("La solicitud debe ser revisada para obtener una resolución");
        }

            solicitud.setEstado(nuevoEstado);
            Solicitud solicitudActualizada = solicitudRepository.save(solicitud);

        HistorialAuditoria historialAuditoria = new HistorialAuditoria();

        historialAuditoria.setEstadoAnterior(estadoAnterior);
        historialAuditoria.setEstadoNuevo(nuevoEstado);
        historialAuditoria.setFechaHora(LocalDateTime.now());
        historialAuditoria.setSolicitud(solicitudActualizada);
        historialAuditoria.setUsuario(usuario);

        auditoriaRepository.save(historialAuditoria);

        return solicitudActualizada;

    }

    public List<Solicitud> obtenerSolicitudesAprobadasPorUsuario(Long usuarioId) {
        // 1. Traemos todas las solicitudes de la base de datos
        List<Solicitud> todasLasSolicitudes = solicitudRepository.findAll();

        // 2. Aplicamos la magia de los Streams para filtrar
        return todasLasSolicitudes.stream()
                .filter(solicitud -> solicitud.getUsuario().getId().equals(usuarioId))
                .filter(solicitud -> solicitud.getEstado().equals("APROBADA"))
                .collect(Collectors.toList());
    }
}