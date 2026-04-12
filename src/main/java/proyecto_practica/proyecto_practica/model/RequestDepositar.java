package proyecto_practica.proyecto_practica.model;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class RequestDepositar {

    @PositiveOrZero(message = "El monto no puede ser negativo")
    private Double monto;

    public RequestDepositar() {
    }

    public RequestDepositar(Double monto) {
        this.monto = monto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
