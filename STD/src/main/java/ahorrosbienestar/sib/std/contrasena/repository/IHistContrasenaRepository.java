package ahorrosbienestar.sib.std.contrasena.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;

@Repository
public interface IHistContrasenaRepository extends JpaRepository<HistContrasena, Integer> {

}
