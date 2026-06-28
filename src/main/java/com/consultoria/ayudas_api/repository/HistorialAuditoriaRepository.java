package com.consultoria.ayudas_api.repository;

import com.consultoria.ayudas_api.model.HistorialAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialAuditoriaRepository extends JpaRepository<HistorialAuditoria, Long> {
}