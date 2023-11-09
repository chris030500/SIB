package ahorrosbienestar.sib.std.opcionMenu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.opcionMenu.model.Menus;

@Repository
public interface IMenusRepository extends JpaRepository<Menus, Integer>{

}
