package proyecto_practica.proyecto_practica.service;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import proyecto_practica.proyecto_practica.model.*;
import proyecto_practica.proyecto_practica.repository.CuentaRepository;
import proyecto_practica.proyecto_practica.repository.TransaccionRepository;
import proyecto_practica.proyecto_practica.repository.UsuarioRepository;
import proyecto_practica.proyecto_practica.utils.TipoTransaccion;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;
    private  final TransaccionRepository transaccionRepository;


    public Cuenta registrarCuenta(Integer id, Cuenta cuenta) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (cuenta.getSaldo() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Saldo no puede ser negativo");
        }

        cuenta.setUsuario(usuario);

        return cuentaRepository.save(cuenta);
    }
    @Transactional
    public Cuenta depositoCuenta(Integer id, RequestDepositar requestDepositar) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cuenta no encontrada"
                ));

        Double monto = requestDepositar.getMonto();

        if (monto == null || monto <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El monto debe ser mayor a 0"
            );
        }


        cuenta.setSaldo(cuenta.getSaldo() + monto);

        registroTrans(TipoTransaccion.DEPOSITO,monto,null,cuenta);

        return cuenta;


    }

    @Transactional
    public Cuenta retirarSaldo(Integer id, RequestDepositar requestRetirar) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cuenta no encontrada"
                ));

        Double monto = requestRetirar.getMonto();

        if (monto == null || monto <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El monto debe ser mayor a 0"
            );
        }

        if (monto > cuenta.getSaldo()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Saldo insuficiente"
            );
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);

        registroTrans(TipoTransaccion.RETIRO, monto, cuenta, null);

        return cuenta;
    }

    @Transactional
    public TransferenciasDTO tranferirCuenta(TransferenciasDTO dto) {

        Cuenta origen = cuentaRepository.findById(dto.getCuentaOrigenId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cuenta de origen no encontrada"
                ));

        Cuenta destino = cuentaRepository.findById(dto.getCuentaDestinoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cuenta de destino no encontrada"
                ));

        Double monto = dto.getMonto();

        if (monto == null || monto <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El monto debe ser mayor a 0"
            );
        }

        if (origen.getId().equals(destino.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No puedes transferirte a la misma cuenta"
            );
        }

        if (monto > origen.getSaldo()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Saldo insuficiente"
            );
        }

        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);

        registroTrans(TipoTransaccion.TRANSFERENCIA, monto, origen, destino);

        return dto;
    }

    public Transaccion registroTrans(
            TipoTransaccion tipo,
            Double monto,
            Cuenta origenCuenta,
            Cuenta destinoCuenta
    ) {

        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(tipo);
        transaccion.setMonto(monto);
        transaccion.setCuentaOrigen(origenCuenta);
        transaccion.setCuentaDestino(destinoCuenta);
        transaccion.setFecha(LocalDateTime.now());

        return transaccionRepository.save(transaccion);
    }




}
