package proyecto_practica.proyecto_practica.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.server.ResponseStatusException;
import proyecto_practica.proyecto_practica.model.*;
import proyecto_practica.proyecto_practica.repository.CuentaRepository;
import proyecto_practica.proyecto_practica.repository.TransaccionRepository;
import proyecto_practica.proyecto_practica.repository.UsuarioRepository;
import proyecto_practica.proyecto_practica.utils.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;
    private  final TransaccionRepository transaccionRepository;

    public CuentaService(CuentaRepository cuentaRepository, UsuarioRepository usuarioRepository, TransaccionRepository transaccionRepository) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
        this.transaccionRepository = transaccionRepository;
    }


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


    public List<Transaccion> historialTransacciones(Integer id, LocalDate fechaInicio, LocalDate fechaFin, TipoTransaccion tipoTransaccion) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Cuenta no encontrada"));
        LocalDateTime inicio = (fechaInicio != null)
                ? fechaInicio.atStartOfDay()
                : LocalDateTime.of(2000, 1, 1, 0, 0);

        LocalDateTime fin = (fechaFin != null)
                ? fechaFin.plusDays(1).atStartOfDay()
                : LocalDateTime.now().plusDays(1);

        return transaccionRepository.filtrarPorCuentaYTipo(id, tipoTransaccion);
    }

    public EstadoCuentaDTO estadoCuenta(Integer id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cuenta no encontrada"
                ));
        EstadoCuentaDTO estadoCuentaDTO = new EstadoCuentaDTO();

        estadoCuentaDTO.setCuentaId(cuenta.getId());
        estadoCuentaDTO.setSaldoActual(cuenta.getSaldo());

        List<Transaccion>  transacciones = historialTransacciones(cuenta.getId(), null, null, null);
        double totalDepositos = 0;
        double totalRetiro= 0;

        for (Transaccion transaccion : transacciones) {

            if (transaccion.getTipo() == TipoTransaccion.DEPOSITO ||
                    transaccion.getTipo() == TipoTransaccion.TRANSFERENCIA) {

                totalDepositos += transaccion.getMonto();
            }
            if(transaccion.getTipo() == TipoTransaccion.RETIRO) {
                totalRetiro += transaccion.getMonto();
            }
        }
        estadoCuentaDTO.setTotalDepositos(BigDecimal.valueOf(totalDepositos));
        estadoCuentaDTO.setTotalRetiros(BigDecimal.valueOf( totalRetiro));
        estadoCuentaDTO.setNumeroTransacciones(transacciones.size());
        Pageable pageable = PageRequest.of(0, 2);

        List<Transaccion> ultimas = transaccionRepository
                .findUltimasTransacciones(cuenta.getId(), pageable);

        List<TransaccionResumenDTO> ultimosMovimientos = ultimas.stream()
                .map(t -> new TransaccionResumenDTO(
                        t.getTipo().name(),
                        BigDecimal.valueOf(t.getMonto()),
                        t.getFecha().toLocalDate()
                ))
                .toList();

        estadoCuentaDTO.setUltimosMovimientos(ultimosMovimientos);

        return estadoCuentaDTO;





        }

    }

