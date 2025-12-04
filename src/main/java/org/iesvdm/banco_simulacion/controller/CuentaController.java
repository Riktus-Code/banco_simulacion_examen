package org.iesvdm.banco_simulacion.controller;

import jakarta.validation.Valid;
import org.iesvdm.banco_simulacion.dto.Paso1DTO;
import org.iesvdm.banco_simulacion.dto.Paso2DTO;
import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.iesvdm.banco_simulacion.service.CuentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SessionAttributes("transferencia")
@RequestMapping("/transferencia")
public class CuentaController {
    private final CuentaService cuentaService;
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }


    @ModelAttribute("transferencia")
    public TransferenciaProgramada transferenciaProgramada(){
        return new TransferenciaProgramada();
    }


    @GetMapping("/transferir/paso1")
    public String paso1(Model model,@ModelAttribute("transferencia")TransferenciaProgramada transferenciaProgramada){
        List<CuentaOrigen> cuentas = cuentaService.listarCuentas();
        //creamos el dto para las validaciones
        Paso1DTO paso1DTO = new Paso1DTO();
        //rellenamos cada atributo de las validaciones con los valores correspondiente
        paso1DTO.setCuentaOrigenId(transferenciaProgramada().getCuentaOrigenId());
        paso1DTO.setIbanDestino(transferenciaProgramada.getIbanDestino());
        paso1DTO.setImporte(transferenciaProgramada.getImporte());
        paso1DTO.setConcepto(transferenciaProgramada.getConcepto());
        paso1DTO.setFechaProgramada(transferenciaProgramada.getFechaProgramada());
        model.addAttribute("cuentas",cuentas);
        model.addAttribute("paso1DTO",paso1DTO);
        return "paso1";
    }

    @PostMapping("/transferir/paso1")
    public  String paso1Post(Model model,
                             @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada
    , @Valid @ModelAttribute ("paso1DTO") Paso1DTO paso1DTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<CuentaOrigen> cuentas = cuentaService.listarCuentas();
            return "paso1";
        }
        transferenciaProgramada.setCuentaOrigenId(paso1DTO.getCuentaOrigenId());
        transferenciaProgramada.setIbanDestino(paso1DTO.getIbanDestino());
        transferenciaProgramada.setImporte(paso1DTO.getImporte());
        transferenciaProgramada.setConcepto(paso1DTO.getConcepto());
        transferenciaProgramada.setFechaProgramada(paso1DTO.getFechaProgramada());
        transferenciaProgramada.setFechaCreacion(LocalDateTime.now());
        transferenciaProgramada.setFechaProgramada(paso1DTO.getFechaProgramada());
        transferenciaProgramada.setEstado("PROGRAMADA");
        cuentaService.crearTransferencia(transferenciaProgramada);
        return "redirect:/transferencia/transferir/paso2";
    }

    @GetMapping("/transferir/paso2")
    public String paso2(Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada){
        CuentaOrigen cuentaOrigen = cuentaService.buscarCuentaId(transferenciaProgramada.getCuentaOrigenId());
        model.addAttribute("cuentaOrigen", cuentaOrigen);
        Paso2DTO paso2DTO = new Paso2DTO();

        model.addAttribute("paso2DTO",paso2DTO);
        return "paso2";
    }

    @PostMapping("/transferir/paso2")
    public String paso2Post (Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada,
                   @Valid @ModelAttribute("paso2DTO") Paso2DTO paso2DTO, BindingResult bindingResult , @RequestParam Integer codigo){
        if (bindingResult.hasErrors()){
            return "paso2";
        }
        paso2DTO.setCodigo(codigo);
        return "redirect:/transferencia/transferir/final";
    }

    @GetMapping("/transferir/final")
    public String finalGet(Model model, @ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada){
        CuentaOrigen cuentaOrigen = cuentaService.buscarCuentaId(transferenciaProgramada.getCuentaOrigenId());
        model.addAttribute("cuenta",cuentaOrigen);
        model.addAttribute("transferencia", transferenciaProgramada);
        return "final";
    }

}
