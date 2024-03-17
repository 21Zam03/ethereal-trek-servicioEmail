package com.hackacode.gestionEmail.repository;

import com.hackacode.gestionEmail.models.ClienteModel;
import com.hackacode.gestionEmail.models.EmpleadoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "gestionEmpCli")
public interface EmpCliRepository {

    @GetMapping("clientes/{param}")
    public ClienteModel obtenerCliente(@PathVariable String param);

    @GetMapping("clientes")
    public List<ClienteModel> listarClientes();

    @GetMapping("empleados/{param}")
    public EmpleadoModel obtenerEmpleado(@PathVariable String param);

    @GetMapping("empleados")
    public List<EmpleadoModel> listarEmpleados();

}
