package com.udla.evaluaytor.businessdomain.empresa.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    @JsonManagedReference
    private List<MatrizEvaluacion> matricesEvaluacion;

    @ManyToMany(mappedBy = "categorias")
    private List<Proveedor> proveedores;
 
}
