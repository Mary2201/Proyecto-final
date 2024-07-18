package com.udla.evaluaytor.businessdomain.evaluacion.Models;

import java.util.Date;
//import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
//import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class FormularioEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numero;
    private Date fecha;
    private String evaluacion;

    @Transient
    private Proveedor proveedor;

    @Transient
    private Perito perito;

    @Transient
    private Categoria categoria;

    @Transient
    private MatrizEvaluacion matrizEvaluacion;

    /*@ManyToOne
    @JoinColumn(name = "perito_id")
    private Perito perito;*/

    @ManyToOne
    @JoinColumn(name = "estado_formulario_id")
    @JsonBackReference
    private EstadoFormulario estadoFormulario;

    //@OneToMany(mappedBy = "formulario")
    //private List<DetalleFormularioEvaluacion> detallesFormularioEvaluacion;

}
