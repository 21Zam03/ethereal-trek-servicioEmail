package com.hackacode.gestionEmail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioModel {

    private String idServicio;
    private String nombre;
    private String descripcion;
    private String destiono;
    private Date fecha_servicio;
    private Double costo;
    private String tipoServicio;

}
