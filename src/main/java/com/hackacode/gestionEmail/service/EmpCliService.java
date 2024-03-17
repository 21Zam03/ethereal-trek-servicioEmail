package com.hackacode.gestionEmail.service;

import com.hackacode.gestionEmail.models.ClienteModel;
import com.hackacode.gestionEmail.models.EmpleadoModel;

import java.util.List;

public interface EmpCliService {

    public ClienteModel obtenerCliente(String param);

    public List<ClienteModel> listarClientes();

    public EmpleadoModel obtenerEmpleado(String param);

    public List<EmpleadoModel> listarEmpleados();

}
