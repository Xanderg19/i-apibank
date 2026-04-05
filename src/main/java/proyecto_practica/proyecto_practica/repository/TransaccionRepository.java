package proyecto_practica.proyecto_practica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_practica.proyecto_practica.model.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
}
