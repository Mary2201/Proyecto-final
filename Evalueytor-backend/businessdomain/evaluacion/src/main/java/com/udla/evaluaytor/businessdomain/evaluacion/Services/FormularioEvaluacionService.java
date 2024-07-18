package com.udla.evaluaytor.businessdomain.evaluacion.Services;

import com.udla.evaluaytor.businessdomain.evaluacion.Models.FormularioEvaluacion;

public interface FormularioEvaluacionService {

    public FormularioEvaluacion getFormularioEvaluacion(Long formularioId, Long proveedorId, Long peritoId);
    public FormularioEvaluacion getFormularioEvaluacionWithProveedor(Long formularioId, Long proveedorId);
    public FormularioEvaluacion getFormularioEvaluacionWithPerito(Long formularioId, Long peritoId);
    public FormularioEvaluacion getFormularioEvaluacionWithCategoria(Long formularioId,Long categoriaId);
    public FormularioEvaluacion getFormularioEvaluacionWithMatrizEvaluacion(Long formularioId, Long matrizId);
}

