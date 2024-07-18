package com.udla.evaluaytor.businessdomain.evaluacion.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Documento {
    @jakarta.persistence.Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private String path;

    @OneToMany(mappedBy = "documento",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleFormularioEvaluacion> documentos;
}
