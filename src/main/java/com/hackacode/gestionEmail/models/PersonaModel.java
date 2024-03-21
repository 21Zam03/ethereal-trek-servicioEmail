package com.hackacode.gestionEmail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonaModel {

    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNac;
    private String pais;
    private String celular;
    private String email;
    private String password;
    private String direccion;
    private Boolean enabled;

}
