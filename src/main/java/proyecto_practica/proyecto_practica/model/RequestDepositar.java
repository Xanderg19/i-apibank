package proyecto_practica.proyecto_practica.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RequestDepositar {

    @PositiveOrZero(message = "El monto no puede ser negativo")
    private Double monto;


}
