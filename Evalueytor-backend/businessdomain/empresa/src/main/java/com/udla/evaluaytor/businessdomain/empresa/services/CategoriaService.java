package com.udla.evaluaytor.businessdomain.empresa.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udla.evaluaytor.businessdomain.empresa.dto.CategoriaDTO;
import com.udla.evaluaytor.businessdomain.empresa.models.Categoria;
import com.udla.evaluaytor.businessdomain.empresa.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> listarTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoriaDTO> obtenerPorId(Long id) {
        return categoriaRepository.findById(id).map(this::convertirAResponseDTO);
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirAResponseDTO(categoriaGuardada);
    }

    public Optional<CategoriaDTO> actualizarCategoria(Long id, Categoria categoria) {
        return categoriaRepository.findById(id).map(cat -> {
            cat.setDescripcion(categoria.getDescripcion());
            Categoria categoriaActualizada = categoriaRepository.save(cat);
            return convertirAResponseDTO(categoriaActualizada);
        });
    }

    public boolean eliminarCategoria(Long id) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoriaRepository.delete(categoria);
            return true;
        }).orElse(false);
    }

    private CategoriaDTO convertirAResponseDTO(Categoria categoria) {
        CategoriaDTO responseDTO = new CategoriaDTO();
        responseDTO.setId(categoria.getId());
        responseDTO.setDescripcion(categoria.getDescripcion());
        return responseDTO;
    }
}
