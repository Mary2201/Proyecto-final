package com.udla.evaluaytor.businessdomain.evaluacion.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udla.evaluaytor.businessdomain.evaluacion.Models.DetalleFormularioEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.Documento;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.EstadoDetalle;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.EstadoFormulario;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.FormularioEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.Repositories.DetalleFormularioEvaluacionRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.Repositories.DocumentoRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.Repositories.EstadoDetalleRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.Repositories.EstadoFormularioRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.Repositories.FormularioEvaluacionRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.Services.FormularioEvaluacionService;

@RestController
@RequestMapping("/api/evaluacion")
public class EvaluacionController {
    @Autowired
    private DetalleFormularioEvaluacionRepository detalleFormularioEvaluacionRepository;

    @Autowired
    private FormularioEvaluacionRepository formularioEvaluacionRepository;

    @Autowired
    private EstadoFormularioRepository estadoFormularioRepository;

    @Autowired
    private EstadoDetalleRepository estadoDetalleRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    //CONECTAR EL MICROSERVICIO CON EL OTRO
    @Autowired
    private FormularioEvaluacionService formularioEvaluacionService;

    @GetMapping("/formulario/{formularioId}/proveedor/{proveedorId}/perito/{peritoId}")
    public FormularioEvaluacion getFormularioEvaluacion(@PathVariable Long formularioId, @PathVariable Long proveedorId, @PathVariable Long peritoId) {
        return formularioEvaluacionService.getFormularioEvaluacion(formularioId, proveedorId, peritoId);
    }
    @GetMapping("/formulario/{formularioId}/proveedor/{proveedorId}")
    public FormularioEvaluacion getFormularioEvaluacion(@PathVariable Long formularioId, @PathVariable Long proveedorId) {
        return formularioEvaluacionService.getFormularioEvaluacionWithProveedor(formularioId, proveedorId);
    }

    @GetMapping("/formulario/{formularioId}/perito/{peritoId}")
    public FormularioEvaluacion getFormularioEvaluacion1(@PathVariable Long formularioId, @PathVariable Long peritoId) {
        return formularioEvaluacionService.getFormularioEvaluacionWithPerito(formularioId, peritoId);
    }

    @GetMapping("/formulario/{formularioId}/categoria/{categoriaId}")
    public FormularioEvaluacion getFormularioEvaluacionWithCategoria(@PathVariable Long formularioId, @PathVariable Long categoriaId) {
        return formularioEvaluacionService.getFormularioEvaluacionWithCategoria(formularioId, categoriaId);
    }

    @GetMapping("/formulario/{formularioId}/matrizEvaluacion/{matrizId}")
    public FormularioEvaluacion getFormularioEvaluacionWithMatriizEvaluacion(@PathVariable Long formularioId, @PathVariable Long matrizId) {
        return formularioEvaluacionService.getFormularioEvaluacionWithMatrizEvaluacion(formularioId, matrizId);
    }


    ///////////////////////////////////////////// DetalleFormularioEvaluacion ///////////////////////////////////////////
    
    // Listar todos los DetalleFormularioEvaluacion
    @GetMapping("/detalleFormularioEvaluacion/findall")
    public List<DetalleFormularioEvaluacion> listarDetalleFormularioEvaluacion() {
        return detalleFormularioEvaluacionRepository.findAll();
    }

    // Listar DetalleFormularioEvaluacion por ID
    @GetMapping("/detalleFormularioEvaluacion/findbyid/{id}")
    public ResponseEntity<DetalleFormularioEvaluacion> obtenerDetalleFormularioEvaluacionPorId(@PathVariable Long id) {
        Optional<DetalleFormularioEvaluacion> detalleOptional = detalleFormularioEvaluacionRepository.findById(id);
        return detalleOptional.map(detalle -> new ResponseEntity<>(detalle, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo DetalleFormularioEvaluacion
    @PostMapping("/detalleFormularioEvaluacion/save")
    public DetalleFormularioEvaluacion crearDetalleFormularioEvaluacion(@RequestBody DetalleFormularioEvaluacion detalleFormularioEvaluacion) {
        return detalleFormularioEvaluacionRepository.save(detalleFormularioEvaluacion);
    }
    // Actualizar un DetalleFormularioEvaluacion
    @PutMapping("/detalleFormularioEvaluacion/updatebyid/{id}")
    public ResponseEntity<DetalleFormularioEvaluacion> actualizarDetalleFormularioEvaluacion(@PathVariable Long id, @RequestBody DetalleFormularioEvaluacion detalleActualizado) {
        Optional<DetalleFormularioEvaluacion> detalleOptional = detalleFormularioEvaluacionRepository.findById(id);
        return detalleOptional.map(detalle -> {
            detalle.setCumplimiento(detalleActualizado.isCumplimiento());
            detalle.setObservacion(detalleActualizado.getObservacion());
            //detalle.setFormularioEvaluacion(detalleActualizado.getFormularioEvaluacion());
            //detalle.setDocumento(detalleActualizado.getDocumento());
            detalle.setEstadoDetalle(detalleActualizado.getEstadoDetalle());
            DetalleFormularioEvaluacion detalleGuardado = detalleFormularioEvaluacionRepository.save(detalle);
            return new ResponseEntity<>(detalleGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Eliminar un DetalleFormularioEvaluacion por ID
    @DeleteMapping("/detalleFormularioEvaluacion/deletebyid/{id}")
    public ResponseEntity<Void> eliminarDetalleFormularioEvaluacion(@PathVariable Long id) {
        Optional<DetalleFormularioEvaluacion> detalleOptional = detalleFormularioEvaluacionRepository.findById(id);
        if (detalleOptional.isPresent()) {
            detalleFormularioEvaluacionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    ///////////////////////////////////////////// FormularioEvaluacion ///////////////////////////////////////////
    // Listar todos los FormularioEvaluacion
    @GetMapping("/formularioEvaluacion/findall")
    public List<FormularioEvaluacion> listarFormularioEvaluacion() {
        return formularioEvaluacionRepository.findAll();
    }
    // Listar FormularioEvaluacion por ID
    @GetMapping("/formularioEvaluacion/findbyid/{id}")
    public ResponseEntity<FormularioEvaluacion> obtenerFormularioEvaluacionPorId(@PathVariable Long id) {
        Optional<FormularioEvaluacion> formularioOptional = formularioEvaluacionRepository.findById(id);
        return formularioOptional.map(formulario -> new ResponseEntity<>(formulario, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Crear un nuevo FormularioEvaluacion
    @PostMapping("/formularioEvaluacion/save")
    public FormularioEvaluacion crearFormularioEvaluacion(@RequestBody FormularioEvaluacion formularioEvaluacion) {
        return formularioEvaluacionRepository.save(formularioEvaluacion);
    }
    // Actualizar un FormularioEvaluacion
    @PutMapping("/formularioEvaluacion/updatebyid/{id}")
    public ResponseEntity<FormularioEvaluacion> actualizarFormularioEvaluacion(@PathVariable Long id, @RequestBody FormularioEvaluacion formularioActualizado) {
        Optional<FormularioEvaluacion> formularioOptional = formularioEvaluacionRepository.findById(id);
        return formularioOptional.map(formulario -> {
            formulario.setNumero(formularioActualizado.getNumero());
            formulario.setFecha(formularioActualizado.getFecha());
            formulario.setEvaluacion(formularioActualizado.getEvaluacion());
            //formulario.setPerito(formularioActualizado.getPerito());
            formulario.setEstadoFormulario(formularioActualizado.getEstadoFormulario());
            //formulario.setDetallesFormularioEvaluacion(formularioActualizado.getDetallesFormularioEvaluacion());
            FormularioEvaluacion formularioGuardado = formularioEvaluacionRepository.save(formulario);
            return new ResponseEntity<>(formularioGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Eliminar un FormularioEvaluacion por ID
    @DeleteMapping("/formularioEvaluacion/deletebyid/{id}")
    public ResponseEntity<Void> eliminarFormularioEvaluacion(@PathVariable Long id) {
        Optional<FormularioEvaluacion> formularioOptional = formularioEvaluacionRepository.findById(id);
        if (formularioOptional.isPresent()) {
            formularioEvaluacionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    ///////////////////////////////////////////// EstadoFormulario ///////////////////////////////////////////

    // Listar todos los EstadoFormulario
    @GetMapping("/estadoFormulario/findall")
    public List<EstadoFormulario> listarEstadoFormulario() {
        return estadoFormularioRepository.findAll();
    }

    // Listar EstadoFormulario por ID
    @GetMapping("/estadoFormulario/findbyid/{id}")
    public ResponseEntity<EstadoFormulario> obtenerEstadoFormularioPorId(@PathVariable Long id) {
        Optional<EstadoFormulario> estadoOptional = estadoFormularioRepository.findById(id);
        return estadoOptional.map(estado -> new ResponseEntity<>(estado, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo EstadoFormulario
    @PostMapping("/estadoFormulario/save")
    public EstadoFormulario crearEstadoFormulario(@RequestBody EstadoFormulario estadoFormulario) {
        return estadoFormularioRepository.save(estadoFormulario);
    }

    // Actualizar un EstadoFormulario
    @PutMapping("/estadoFormulario/updatebyid/{id}")
    public ResponseEntity<EstadoFormulario> actualizarEstadoFormulario(@PathVariable Long id, @RequestBody EstadoFormulario estadoActualizado) {
        Optional<EstadoFormulario> estadoOptional = estadoFormularioRepository.findById(id);
        return estadoOptional.map(estado -> {
            estado.setNombre(estadoActualizado.getNombre());
            EstadoFormulario estadoGuardado = estadoFormularioRepository.save(estado);
            return new ResponseEntity<>(estadoGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un EstadoFormulario por ID
    @DeleteMapping("/estadoFormulario/deletebyid/{id}")
    public ResponseEntity<Void> eliminarEstadoFormulario(@PathVariable Long id) {
        Optional<EstadoFormulario> estadoOptional = estadoFormularioRepository.findById(id);
        if (estadoOptional.isPresent()) {
            estadoFormularioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    ///////////////////////////////////////////// EstadoDetalle ///////////////////////////////////////////
    // Listar todos los EstadoDetalle
    @GetMapping("/estadoDetalle/findall")
    public List<EstadoDetalle> listarEstadoDetalle() {
        return estadoDetalleRepository.findAll();
    }
    // Listar EstadoDetalle por ID
    @GetMapping("/estadoDetalle/findbyid/{id}")
    public ResponseEntity<EstadoDetalle> obtenerEstadoDetallePorId(@PathVariable Long id) {
        Optional<EstadoDetalle> estadoOptional = estadoDetalleRepository.findById(id);
        return estadoOptional.map(estado -> new ResponseEntity<>(estado, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Crear un nuevo EstadoDetalle
    @PostMapping("/estadoDetalle/save")
    public EstadoDetalle crearEstadoDetalle(@RequestBody EstadoDetalle estadoDetalle) {
        return estadoDetalleRepository.save(estadoDetalle);
    }
    // Actualizar un EstadoDetalle
    @PutMapping("/estadoDetalle/updatebyid/{id}")
    public ResponseEntity<EstadoDetalle> actualizarEstadoDetalle(@PathVariable Long id, @RequestBody EstadoDetalle estadoActualizado) {
        Optional<EstadoDetalle> estadoOptional = estadoDetalleRepository.findById(id);
        return estadoOptional.map(estado -> {
            estado.setNombre(estadoActualizado.getNombre());
            EstadoDetalle estadoGuardado = estadoDetalleRepository.save(estado);
            return new ResponseEntity<>(estadoGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un EstadoDetalle por ID
    @DeleteMapping("/estadoDetalle/deletebyid/{id}")
    public ResponseEntity<Void> eliminarEstadoDetalle(@PathVariable Long id) {
        Optional<EstadoDetalle> estadoOptional = estadoDetalleRepository.findById(id);
        if (estadoOptional.isPresent()) {
            estadoDetalleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    ///////////////////////////////////////////// Documento ///////////////////////////////////////////
    // Listar todos los documentos
    @GetMapping("/documento/findall")
    public List<Documento> listarDocumento() {
        return documentoRepository.findAll();
    }
    // Listar Documetno por ID
    @GetMapping("/documento/findbyid/{id}")
    public ResponseEntity<Documento> obtenerDocumentoPorId(@PathVariable Long id) {
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        return documentoOptional.map(documento -> new ResponseEntity<>(documento, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Crear un nuevo documento
    @PostMapping("/documento/save")
    public Documento crearDocumento(@RequestBody Documento documento) {
        return documentoRepository.save(documento);
    }
    // Actualizar un Documento
    @PutMapping("/documento/updatebyid/{id}")
    public ResponseEntity<Documento> actualizarDocumento(@PathVariable Long id, @RequestBody Documento documentoActualizado) {
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        return documentoOptional.map(documento -> {
            documento.setNombre(documentoActualizado.getNombre());
            documento.setPath(documentoActualizado.getPath());
            Documento docGuardado = documentoRepository.save(documento);
            return new ResponseEntity<>(docGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un Documento por ID
    @DeleteMapping("/documento/deletebyid/{id}")
    public ResponseEntity<Void> eliminarDocumento(@PathVariable Long id) {
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        if (documentoOptional.isPresent()) {
            documentoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
