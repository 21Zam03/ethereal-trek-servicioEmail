package com.hackacode.gestionEmail.models;


import com.hackacode.gestionEmail.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class EmpleadoModel extends PersonaModel {

    private Cargo cargo;
    private Double sueldo;

}
