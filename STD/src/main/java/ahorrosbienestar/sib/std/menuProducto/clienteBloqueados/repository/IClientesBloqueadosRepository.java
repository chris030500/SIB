package ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.model.ClientesBloqueados;

@Repository
public interface IClientesBloqueadosRepository extends JpaRepository<ClientesBloqueados, Integer> {

}
