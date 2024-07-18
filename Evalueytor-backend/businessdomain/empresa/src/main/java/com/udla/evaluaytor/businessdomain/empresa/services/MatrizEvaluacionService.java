package com.udla.evaluaytor.businessdomain.empresa.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udla.evaluaytor.businessdomain.empresa.dto.CategoriaDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionResponseDTO;
import com.udla.evaluaytor.businessdomain.empresa.models.Categoria;
import com.udla.evaluaytor.businessdomain.empresa.models.MatrizEvaluacion;
import com.udla.evaluaytor.businessdomain.empresa.repositories.CategoriaRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.MatrizEvaluacionRepository;

@Service
public class MatrizEvaluacionService {
    @Autowired
    private MatrizEvaluacionRepository matrizEvaluacionRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<MatrizEvaluacionResponseDTO> getAllMatrizEvaluaciones() {
        List<MatrizEvaluacion> matricesEvaluacion = matrizEvaluacionRepository.findAll();
        return matricesEvaluacion.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public MatrizEvaluacionResponseDTO getMatrizEvaluacionById(Long id) {
        MatrizEvaluacion matrizEvaluacion = matrizEvaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matriz de evaluación no encontrada"));
        return convertToResponseDTO(matrizEvaluacion);
    }

    public MatrizEvaluacionResponseDTO createMatrizEvaluacion(MatrizEvaluacionDTO matrizEvaluacionDTO) {
        MatrizEvaluacion matrizEvaluacion = new MatrizEvaluacion();
        matrizEvaluacion.setPregunta(matrizEvaluacionDTO.getPregunta());
        matrizEvaluacion.setPuntos(matrizEvaluacionDTO.getPuntos());
        matrizEvaluacion.setRequiereDocumento(matrizEvaluacionDTO.isRequiereDocumento());

        Categoria categoria = categoriaRepository.findById(matrizEvaluacionDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        matrizEvaluacion.setCategoria(categoria);

        MatrizEvaluacion matrizGuardada = matrizEvaluacionRepository.save(matrizEvaluacion);
        return convertToResponseDTO(matrizGuardada);
    }

    public MatrizEvaluacionResponseDTO updateMatrizEvaluacion(Long id, MatrizEvaluacionDTO matrizEvaluacionDTO) {
        MatrizEvaluacion matrizEvaluacion = matrizEvaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matriz de evaluación no encontrada"));

        matrizEvaluacion.setPregunta(matrizEvaluacionDTO.getPregunta());
        matrizEvaluacion.setPuntos(matrizEvaluacionDTO.getPuntos());
        matrizEvaluacion.setRequiereDocumento(matrizEvaluacionDTO.isRequiereDocumento());

        Categoria categoria = categoriaRepository.findById(matrizEvaluacionDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        matrizEvaluacion.setCategoria(categoria);

        MatrizEvaluacion matrizActualizada = matrizEvaluacionRepository.save(matrizEvaluacion);
        return convertToResponseDTO(matrizActualizada);
    }

    public void deleteMatrizEvaluacionById(Long id) {
        if (matrizEvaluacionRepository.existsById(id)) {
            matrizEvaluacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Matriz de evaluación no encontrada");
        }
    }

    private MatrizEvaluacionResponseDTO convertToResponseDTO(MatrizEvaluacion matrizEvaluacion) {
        MatrizEvaluacionResponseDTO responseDTO = new MatrizEvaluacionResponseDTO();
        responseDTO.setId(matrizEvaluacion.getId());
        responseDTO.setPregunta(matrizEvaluacion.getPregunta());
        responseDTO.setPuntos(matrizEvaluacion.getPuntos());
        responseDTO.setRequiereDocumento(matrizEvaluacion.isRequiereDocumento());

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(matrizEvaluacion.getCategoria().getId());
        categoriaDTO.setDescripcion(matrizEvaluacion.getCategoria().getDescripcion());
        responseDTO.setCategoria(categoriaDTO);

        return responseDTO;
    }
}
