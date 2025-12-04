package org.iesvdm.banco_simulacion.controller;

import org.iesvdm.banco_simulacion.model.TransferenciaProgramada;
import org.iesvdm.banco_simulacion.repository.CuentaRepository;
import org.iesvdm.banco_simulacion.service.CuentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("transferencia")
@RequestMapping("/transferencia")
public class AdminController {
    private final CuentaService cuentaService;
    private final CuentaRepository cuentaRepository;

    public AdminController(CuentaService cuentaService, CuentaRepository cuentaRepository) {
        this.cuentaService = cuentaService;
        this.cuentaRepository = cuentaRepository;
    }
    @ModelAttribute("transferencia")
    public TransferenciaProgramada transferenciaProgramada(){
        return new TransferenciaProgramada();
    }

    @GetMapping("/index")
    public String index (Model model, @ModelAttribute("transferencia")TransferenciaProgramada transferenciaProgramada){
        List<TransferenciaProgramada> transferencias = cuentaService.listarTrans();
        model.addAttribute("transferencias", transferencias);
        return "index";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        TransferenciaProgramada transferenciaProgramada = cuentaService.transPorId(id);
        model.addAttribute("transferencia", transferenciaProgramada);
        return "editar";
    }

    @PostMapping("/editar")
    public  String editarPost(@ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada){
        cuentaRepository.updateTrans(transferenciaProgramada);
        return "redirect:/transferencia/index";

    }

    @GetMapping("/borrar/{id}")
    public String borrar(Model model,@PathVariable Long id ){
        TransferenciaProgramada transferenciaProgramada = cuentaService.transPorId(id);
        model.addAttribute("transferencia", transferenciaProgramada);
        return "borrar";
    }

    @PostMapping("/borrar")
    public String borrarPost(@ModelAttribute("transferencia") TransferenciaProgramada transferenciaProgramada){
        cuentaRepository.eliminarTrans(transferenciaProgramada.getId());
        return "redirect:/transferencia/index";
    }
}
