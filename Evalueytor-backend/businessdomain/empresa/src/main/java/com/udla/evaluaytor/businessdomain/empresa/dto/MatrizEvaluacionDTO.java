package com.udla.evaluaytor.businessdomain.empresa.dto;

import lombok.Data;

@Data
public class MatrizEvaluacionDTO {
    private String pregunta;
    private int puntos;
    private boolean requiereDocumento;
    private Long categoriaId;
}
