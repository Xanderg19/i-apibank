package proyecto_practica.proyecto_practica.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import proyecto_practica.proyecto_practica.model.Cuenta;
import proyecto_practica.proyecto_practica.model.RequestDepositar;
import proyecto_practica.proyecto_practica.model.TransferenciasDTO;
import proyecto_practica.proyecto_practica.service.CuentaService;

@RestController
@RequestMapping("/cuenta")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;



    @PostMapping("/usuario/{id}")
    public Cuenta registrarCuenta(@PathVariable("id") Integer id,
                                 @Valid @RequestBody Cuenta cuenta) {
        return cuentaService.registrarCuenta(id, cuenta);

    }

    @PostMapping("/{id}/depositar")
    public Cuenta depositoCuenta(@PathVariable("id") Integer id, @Valid @RequestBody RequestDepositar requestDepositar) {
        return  cuentaService.depositoCuenta(id,requestDepositar);
    }

    @PostMapping("/{id}/retirar")
    public Cuenta retiroSaldo(@PathVariable("id") Integer id, @Valid @RequestBody RequestDepositar requestRetirar) {
        return cuentaService.retirarSaldo(id,requestRetirar);
    }

    @PostMapping("transferir")
    public TransferenciasDTO transferirCuenta(@Valid @RequestBody TransferenciasDTO transferenciasDTO) {

        return cuentaService.tranferirCuenta(transferenciasDTO);
    }


}
