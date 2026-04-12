package proyecto_practica.proyecto_practica.model;

import java.math.BigDecimal;
import java.util.List;

public class EstadoCuentaDTO {

    private Integer cuentaId;

    private Double saldoActual;


    private BigDecimal totalDepositos;


    private BigDecimal totalRetiros;

    private Integer numeroTransacciones;

    private List<TransaccionResumenDTO> ultimosMovimientos;

    public EstadoCuentaDTO() {
    }

    public EstadoCuentaDTO(Integer cuentaId,
                           Double saldoActual,
                           BigDecimal totalDepositos,
                           BigDecimal totalRetiros,
                           Integer numeroTransacciones,
                           List<TransaccionResumenDTO> ultimosMovimientos) {
        this.cuentaId = cuentaId;
        this.saldoActual = saldoActual;
        this.totalDepositos = totalDepositos;
        this.totalRetiros = totalRetiros;
        this.numeroTransacciones = numeroTransacciones;
        this.ultimosMovimientos = ultimosMovimientos;
    }

    // getters y setters

    public Integer getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public BigDecimal getTotalDepositos() {
        return totalDepositos;
    }

    public void setTotalDepositos(BigDecimal totalDepositos) {
        this.totalDepositos = totalDepositos;
    }

    public BigDecimal getTotalRetiros() {
        return totalRetiros;
    }

    public void setTotalRetiros(BigDecimal totalRetiros) {
        this.totalRetiros = totalRetiros;
    }

    public Integer getNumeroTransacciones() {
        return numeroTransacciones;
    }

    public void setNumeroTransacciones(Integer numeroTransacciones) {
        this.numeroTransacciones = numeroTransacciones;
    }

    public List<TransaccionResumenDTO> getUltimosMovimientos() {
        return ultimosMovimientos;
    }

    public void setUltimosMovimientos(List<TransaccionResumenDTO> ultimosMovimientos) {
        this.ultimosMovimientos = ultimosMovimientos;
    }
}
