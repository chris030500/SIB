package ahorrosbienestar.sib.std.roles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.roles.model.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {

}
