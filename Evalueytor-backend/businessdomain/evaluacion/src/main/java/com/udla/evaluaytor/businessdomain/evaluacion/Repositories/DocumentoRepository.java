package com.udla.evaluaytor.businessdomain.evaluacion.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udla.evaluaytor.businessdomain.evaluacion.Models.Documento;

public interface DocumentoRepository extends JpaRepository<Documento,Long>{

}
