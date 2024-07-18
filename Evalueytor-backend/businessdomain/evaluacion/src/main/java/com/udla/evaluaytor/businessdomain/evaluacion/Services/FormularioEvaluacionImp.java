package com.udla.evaluaytor.businessdomain.evaluacion.Services;

import com.udla.evaluaytor.businessdomain.evaluacion.Models.Categoria;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.FormularioEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.MatrizEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.Perito;
import com.udla.evaluaytor.businessdomain.evaluacion.Models.Proveedor;
import com.udla.evaluaytor.businessdomain.evaluacion.Repositories.FormularioEvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FormularioEvaluacionImp implements FormularioEvaluacionService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private FormularioEvaluacionRepository formularioEvaluacionRepository;

    public FormularioEvaluacion getFormularioEvaluacion(Long formularioId, Long proveedorId, Long peritoId) {
        // Obtén el FormularioEvaluacion desde el repositorio
        FormularioEvaluacion formularioEvaluacion = formularioEvaluacionRepository.findById(formularioId)
            .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));

        // Llama al microservicio de proveedor para obtener la información del proveedor
        WebClient webClient = webClientBuilder.build();
        Mono<Proveedor> proveedorMono = webClient.get()
            .uri("http://localhost:8081/api/empresa/proveedor/findbyid/{id}", proveedorId)
            .retrieve()
            .bodyToMono(Proveedor.class);

        Proveedor proveedor = proveedorMono.block();

        Mono<Perito> peritoMono = webClient.get()
            .uri("http://localhost:8081/api/empresa/perito/findbyid/{id}", peritoId)
            .retrieve()
            .bodyToMono(Perito.class);

        Perito perito = peritoMono.block();

        formularioEvaluacion.setPerito(perito);
        formularioEvaluacion.setProveedor(proveedor);

        return formularioEvaluacion;
    }

    public FormularioEvaluacion getFormularioEvaluacionWithProveedor(Long formularioId, Long proveedorId) {
        // Obtén el FormularioEvaluacion desde el repositorio
        FormularioEvaluacion formularioEvaluacion = formularioEvaluacionRepository.findById(formularioId)
            .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));

        // Llama al microservicio de proveedor para obtener la información del proveedor
        WebClient webClient = webClientBuilder.build();
        Mono<Proveedor> proveedorMono = webClient.get()
            .uri("http://localhost:8081/api/empresa/proveedor/findbyid/{id}", proveedorId)
            .retrieve()
            .bodyToMono(Proveedor.class);

        Proveedor proveedor = proveedorMono.block();

        formularioEvaluacion.setProveedor(proveedor);

        return formularioEvaluacion;
    }

    public FormularioEvaluacion getFormularioEvaluacionWithPerito(Long formularioId, Long peritoId) {
        // Obtén el FormularioEvaluacion desde el repositorio
        FormularioEvaluacion formularioEvaluacion = formularioEvaluacionRepository.findById(formularioId)
            .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));

        // Llama al microservicio de proveedor para obtener la información del proveedor
        WebClient webClient = webClientBuilder.build();

        Mono<Perito> peritoMono = webClient.get()
            .uri("http://localhost:8081/api/empresa/perito/findbyid/{id}", peritoId)
            .retrieve()
            .bodyToMono(Perito.class);

        Perito perito = peritoMono.block();

        formularioEvaluacion.setPerito(perito);

        return formularioEvaluacion;
    }

    public FormularioEvaluacion getFormularioEvaluacionWithCategoria(Long formularioId, Long categoriaId){
        FormularioEvaluacion formularioEvaluacion = formularioEvaluacionRepository.findById(formularioId)
        .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));
        WebClient webClient = webClientBuilder.build();

        Mono<Categoria> categoriaMono = webClient.get()
            .uri("http://localhost:8081/api/empresa/categoria/findbyid/{id}", categoriaId)
            .retrieve()
            .bodyToMono(Categoria.class);
        Categoria categoria = categoriaMono.block();

        formularioEvaluacion.setCategoria(categoria);
        return formularioEvaluacion;
    }

    public FormularioEvaluacion getFormularioEvaluacionWithMatrizEvaluacion(Long formularioId, Long matrizId){
        FormularioEvaluacion formularioEvaluacion = formularioEvaluacionRepository.findById(formularioId)
        .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));
        WebClient webClient = webClientBuilder.build();

        Mono<MatrizEvaluacion> matrizMono = webClient.get()
            .uri("http://localhost:8081/api/empresa/matrizEvaluacion/findbyid/{id}", matrizId)
            .retrieve()
            .bodyToMono(MatrizEvaluacion.class);
        MatrizEvaluacion matrizEvaluacion = matrizMono.block();

        formularioEvaluacion.setMatrizEvaluacion(matrizEvaluacion);
        return formularioEvaluacion;
    }
}
