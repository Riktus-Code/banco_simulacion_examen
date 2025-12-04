package org.iesvdm.banco_simulacion.service;

import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.iesvdm.banco_simulacion.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<CuentaOrigen> listarCuentas(){
        return cuentaRepository.findAll();
    }

    public void crearTransferencia(TransferenciaProgramada transferenciaProgramada){
        cuentaRepository.crearTrans(transferenciaProgramada);
    }

    public CuentaOrigen buscarCuentaId (Long id){
        return cuentaRepository.findByIdCuenta(id);
    }

    public TransferenciaProgramada transPorId(Long id){
        return cuentaRepository.findById(id);
    }

    public List<TransferenciaProgramada> listarTrans(){
        return cuentaRepository.findAllTrans();
    }
}
