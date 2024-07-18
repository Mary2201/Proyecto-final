package com.udla.evaluaytor.businessdomain.empresa.dto;

import lombok.Data;

@Data
public class MatrizEvaluacionResponseDTO {
    private Long id;
    private String pregunta;
    private int puntos;
    private boolean requiereDocumento;
    private CategoriaDTO categoria;
}
