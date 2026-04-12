package proyecto_practica.proyecto_practica.model;


import java.math.BigDecimal;
import java.time.LocalDate;

public class TransaccionResumenDTO {

    private String tipo;
    private BigDecimal monto;
    private LocalDate fecha;

    public TransaccionResumenDTO() {
    }

    public TransaccionResumenDTO(String tipo, BigDecimal monto, LocalDate fecha) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
    }

    // getters y setters

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
