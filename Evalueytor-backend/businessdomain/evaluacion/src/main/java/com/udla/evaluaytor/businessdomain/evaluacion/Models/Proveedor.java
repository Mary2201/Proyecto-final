package com.udla.evaluaytor.businessdomain.evaluacion.Models;

import lombok.Data;

@Data
public class Proveedor {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
}
