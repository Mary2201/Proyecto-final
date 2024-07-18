package com.udla.evaluaytor.businessdomain.evaluacion.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class EstadoFormulario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    //eexplicacion xd
    @OneToMany(mappedBy = "estadoFormulario",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<FormularioEvaluacion> formularios;
}