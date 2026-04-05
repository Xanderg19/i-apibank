package proyecto_practica.proyecto_practica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto_practica.proyecto_practica.model.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {}
