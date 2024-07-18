package com.udla.evaluaytor.businessdomain.evaluacion.Models;

import lombok.Data;

@Data
public class MatrizEvaluacion {
    private Long id;
    private String pregunta;
    private int puntos;
    private boolean requiereDocumento;
}
