package com.hackacode.gestionEmail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteModel {

    private Integer idPaquete;
    private String nombre;
    private String descripcion;
    private Double precio;

}
