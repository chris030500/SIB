package ahorrosbienestar.sib.std.opcionMenu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.opcionMenu.model.OpcionesMenu;
@Repository
public interface IOpcionesMenuRepository extends JpaRepository<OpcionesMenu, Integer> {


}
