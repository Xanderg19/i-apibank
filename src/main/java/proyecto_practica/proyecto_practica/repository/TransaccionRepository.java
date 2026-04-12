package proyecto_practica.proyecto_practica.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyecto_practica.proyecto_practica.model.Transaccion;
import proyecto_practica.proyecto_practica.utils.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.List;
public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

    @Query("""
SELECT t FROM Transaccion t
WHERE (t.cuentaOrigen.id = :cuentaId OR t.cuentaDestino.id = :cuentaId)
AND (:tipo IS NULL OR t.tipo = :tipo)
""")
    List<Transaccion> filtrarPorCuentaYTipo(
            @Param("cuentaId") Integer cuentaId,
            @Param("tipo") TipoTransaccion tipo
    );

    @Query("""
    SELECT t FROM Transaccion t
    WHERE t.cuentaOrigen.id = :cuentaId
       OR t.cuentaDestino.id = :cuentaId
    ORDER BY t.fecha DESC
""")
    List<Transaccion> findUltimasTransacciones(
            @Param("cuentaId") Integer cuentaId,
            Pageable pageable
    );


}
