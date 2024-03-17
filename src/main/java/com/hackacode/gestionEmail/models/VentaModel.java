package com.hackacode.gestionEmail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaModel {

    private Integer idVenta;
    private ClienteModel cliente;
    private EmpleadoModel empleado;
    private ServicioModel Servicio;
    private PaqueteModel paquete;
    private String tipoVenta;
    private Date fechaVenta;
    private String medioPago;

}
