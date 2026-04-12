package proyecto_practica.proyecto_practica.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import proyecto_practica.proyecto_practica.model.*;
import proyecto_practica.proyecto_practica.service.CuentaService;
import proyecto_practica.proyecto_practica.utils.TipoTransaccion;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }


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


    @GetMapping("historial-transacciones/{id}")
    public List<Transaccion> historialTransacciones(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) LocalDate fechaInicio,
            @RequestParam(required = false) LocalDate fechaFin,
            @RequestParam(required = false) TipoTransaccion tipoTransaccion
    ) {
        return cuentaService.historialTransacciones(id, fechaInicio, fechaFin, tipoTransaccion);
    }

    @GetMapping("id/{id}/estado")
    public EstadoCuentaDTO estadoCuenta(@PathVariable("id") Integer id) {
        return cuentaService.estadoCuenta(id);
    }


}
