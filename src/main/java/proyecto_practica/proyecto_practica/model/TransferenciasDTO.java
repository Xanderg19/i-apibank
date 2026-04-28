package proyecto_practica.proyecto_practica.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferenciasDTO {
    @NotNull(message = "No puede nullo ")
    private Integer cuentaOrigenId ;
    @NotNull(message = "No ")
    private Integer cuentaDestinoId ;
    @Positive
    private Double monto;


    public Integer getCuentaOrigenId() {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(Integer cuentaOrigenId) {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public  Integer getCuentaDestinoId() {
        return cuentaDestinoId;
    }

    public void setCuentaDestinoId(Integer cuentaDestinoId) {
        this.cuentaDestinoId = cuentaDestinoId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
