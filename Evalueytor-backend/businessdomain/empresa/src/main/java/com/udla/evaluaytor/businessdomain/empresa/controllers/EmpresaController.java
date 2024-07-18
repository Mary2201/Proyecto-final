package com.udla.evaluaytor.businessdomain.empresa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udla.evaluaytor.businessdomain.empresa.dto.CategoriaDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionResponseDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorResponseDTO;
import com.udla.evaluaytor.businessdomain.empresa.models.Categoria;
import com.udla.evaluaytor.businessdomain.empresa.models.Perito;
import com.udla.evaluaytor.businessdomain.empresa.models.Proveedor;
import com.udla.evaluaytor.businessdomain.empresa.repositories.CategoriaRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.EmpresaRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.MatrizEvaluacionRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.PeritoRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.ProveedorRepository;
import com.udla.evaluaytor.businessdomain.empresa.services.CategoriaService;
import com.udla.evaluaytor.businessdomain.empresa.services.MatrizEvaluacionService;
import com.udla.evaluaytor.businessdomain.empresa.services.ProveedorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired 
    CategoriaRepository categoriaRepository;

    @Autowired
    PeritoRepository peritoRepository;

    @Autowired
    MatrizEvaluacionRepository matrizEvaluacionRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MatrizEvaluacionService matrizEvaluacionService;

/////////////////////////////////////////////Proveedor//////////////////////////////////////////////////////// 
    // Crear una nuevo proveedor
    @PostMapping("/proveedor/save")
    public ResponseEntity<ProveedorResponseDTO> createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        ProveedorResponseDTO proveedorGuardado = proveedorService.createProveedor(proveedorDTO);
        return new ResponseEntity<>(proveedorGuardado, HttpStatus.CREATED);
    }
    // Actualizar un proveedor  
    @PutMapping("/proveedor/update/{id}")
    public ResponseEntity<ProveedorResponseDTO> updateProveedor(@PathVariable Long id, @RequestBody ProveedorDTO proveedorUpdateDTO) {
        ProveedorResponseDTO updatedProveedor = proveedorService.updateProveedor(id, proveedorUpdateDTO);
        return ResponseEntity.ok(updatedProveedor);
    }
    // Mostrar todos los proveedores
    @GetMapping("/proveedor/findall")
    public ResponseEntity<List<ProveedorResponseDTO>> getAllProveedores() {
        List<ProveedorResponseDTO> proveedores = proveedorService.getAllProveedores();
        return ResponseEntity.ok(proveedores);
    }
    // Mostrar proveedor por id
    @GetMapping("/proveedor/findbyid/{id}")
     public ResponseEntity<ProveedorResponseDTO> getProveedorById(@PathVariable Long id) {
        ProveedorResponseDTO proveedor = proveedorService.getProveedorById(id);
        return ResponseEntity.ok(proveedor);
    }
    //Elimar proveedor por id
    @DeleteMapping("/proveedor/deletebyid/{id}")
    public ResponseEntity<Void> deleteProveedorById(@PathVariable Long id) {
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
        if (proveedorOptional.isPresent()) {
            empresaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
/////////////////////////////////////////////////Perito///////////////////////////////////////////////////////
    //Listar perito
    @GetMapping("/perito/findall")
    public List<Perito> ListPeritos(){
        return peritoRepository.findAll();
    }
    // Listar perito por Id
    @GetMapping("/perito/findbyid/{id}")
    public ResponseEntity<Perito> obtenerPeritoPorId(@PathVariable Long id) {
        Optional<Perito> peritoOptional = peritoRepository.findById(id);
        return peritoOptional.map(perito -> new ResponseEntity<>(perito, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Crear una nuevo perito
    @PostMapping("/perito/save")
    public Perito createPerito(@RequestBody Perito nuevoPerito) {
        Perito perito = new Perito();
        perito.setNombre(nuevoPerito.getNombre());
        perito.setDireccion(nuevoPerito.getDireccion());
        perito.setTelefono(nuevoPerito.getTelefono());
        return peritoRepository.save(perito);
    }
    //Actualizar perito
    @PutMapping("/perito/updatebyid/{id}")
    public ResponseEntity<Perito> actualizarPerito(@PathVariable Long id, @RequestBody Perito peritoActual){
        Optional<Perito> peritoOptional = peritoRepository.findById(id);
        return peritoOptional.map(perito -> {
            perito.setId(id);
            perito.setNombre(peritoActual.getNombre());
            perito.setDireccion(peritoActual.getDireccion());
            perito.setTelefono(peritoActual.getTelefono());
            Perito peritoActualGuardado = peritoRepository.save(perito);
            return new ResponseEntity<>(peritoActualGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Eliminar un perito por id
    @DeleteMapping("/perito/deletebyid/{id}")
    public ResponseEntity<Void> eliminarPerito(@PathVariable Long id){
        Optional<Perito> peritoOptional = peritoRepository.findById(id);
        if (peritoOptional.isPresent()){
            peritoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//////////////////////////////////////////////////Categoria //////////////////////////////////////////////////
    //Listar Categorias
    @GetMapping("/categoria/findall")
    public List<CategoriaDTO> listarTodas(){
        return categoriaService.listarTodas();
    }
    // Listar categoria por Id
    @GetMapping("/categoria/findbyid/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        Optional<CategoriaDTO> categoria = categoriaService.obtenerPorId(id);
        return categoria.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }
    // Crear una nuevo categoria
    @PostMapping("/categoria/save")
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.crearCategoria(categoriaDTO);
    }
    //Actualizar categoria
    @PutMapping("/categoria/updatebyid/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Optional<CategoriaDTO> categoriaActualizada = categoriaService.actualizarCategoria(id, categoria);
        return categoriaActualizada.map(ResponseEntity::ok)
                                   .orElse(ResponseEntity.notFound().build());
    }
    // Eliminar un categoria por id
    @DeleteMapping("/categoria/deletebyid/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        boolean eliminado = categoriaService.eliminarCategoria(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
////////////////////////////////////////////Matriz de Evaluacion///////////////////////////////////////////////
    // Listar todas las matrices de evaluación
    @GetMapping("/matrizEvaluacion/findall")
    public ResponseEntity<List<MatrizEvaluacionResponseDTO>> getAllMatrizEvaluaciones() {
        List<MatrizEvaluacionResponseDTO> matricesEvaluacion = matrizEvaluacionService.getAllMatrizEvaluaciones();
        return ResponseEntity.ok(matricesEvaluacion);
    }

    // Listar una matriz de evaluación por ID
    @GetMapping("/matrizEvaluacion/findbyid/{id}")
    public ResponseEntity<MatrizEvaluacionResponseDTO> getMatrizEvaluacionById(@PathVariable Long id) {
        MatrizEvaluacionResponseDTO matrizEvaluacion = matrizEvaluacionService.getMatrizEvaluacionById(id);
        return ResponseEntity.ok(matrizEvaluacion);
    }

    // Crear una nueva matriz de evaluación
    @PostMapping("/matrizEvaluacion/save")
    public ResponseEntity<MatrizEvaluacionResponseDTO> createMatrizEvaluacion(@RequestBody MatrizEvaluacionDTO matrizEvaluacionDTO) {
        MatrizEvaluacionResponseDTO nuevaMatrizEvaluacion = matrizEvaluacionService.createMatrizEvaluacion(matrizEvaluacionDTO);
        return new ResponseEntity<>(nuevaMatrizEvaluacion, HttpStatus.CREATED);
    }

    // Actualizar una matriz de evaluación
    @PutMapping("/matrizEvaluacion/update/{id}")
    public ResponseEntity<MatrizEvaluacionResponseDTO> updateMatrizEvaluacion(@PathVariable Long id, @RequestBody MatrizEvaluacionDTO matrizEvaluacionDTO) {
        MatrizEvaluacionResponseDTO matrizActualizada = matrizEvaluacionService.updateMatrizEvaluacion(id, matrizEvaluacionDTO);
        return ResponseEntity.ok(matrizActualizada);
    }

    // Eliminar una matriz de evaluación
    @DeleteMapping("/matrizEvaluacion/deletebyid/{id}")
    public ResponseEntity<Void> deleteMatrizEvaluacionById(@PathVariable Long id) {
        matrizEvaluacionService.deleteMatrizEvaluacionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
