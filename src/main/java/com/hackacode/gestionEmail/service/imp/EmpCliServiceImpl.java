package com.hackacode.gestionEmail.service.imp;

import com.hackacode.gestionEmail.models.ClienteModel;
import com.hackacode.gestionEmail.models.EmpleadoModel;
import com.hackacode.gestionEmail.repository.EmpCliRepository;
import com.hackacode.gestionEmail.service.EmpCliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpCliServiceImpl implements EmpCliService {

    @Autowired
    private EmpCliRepository empCliRepository;

    @Override
    public ClienteModel obtenerCliente(String param) {
        return empCliRepository.obtenerCliente(param);
    }

    @Override
    public List<ClienteModel> listarClientes() {
        return empCliRepository.listarClientes();
    }

    @Override
    public EmpleadoModel obtenerEmpleado(String param) {
        return empCliRepository.obtenerEmpleado(param);
    }

    @Override
    public List<EmpleadoModel> listarEmpleados() {
        return empCliRepository.listarEmpleados();
    }

}
