package ahorrosbienestar.sib.std.opcionMenu.repository;
 
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
import ahorrosbienestar.sib.std.opcionMenu.model.RelRolOpciones;
import ahorrosbienestar.sib.std.roles.model.Rol;
 
@Repository
public interface IRelRolOpcionesRepository extends JpaRepository<RelRolOpciones, Integer>{
 
    @Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_REL_ROL_OPCIONES WHERE FK_ROL =:idRol and FK_OPCION=:idOpcion", nativeQuery = true)
    public int existeOpcion(@Param("idRol") Integer idRol, @Param("idOpcion") Integer idOpcion);
 
    @Modifying
    @Transactional
    @Query(value = "UPDATE SIB.TBL_REL_ROL_OPCIONES SET I_ESTATUS = 1, DT_FECHA_BAJA = null, I_USUARIOID_MODIFICO =:usuarioMod,DT_FECHA_MODIFICO=now() WHERE FK_ROL =:idRol and FK_OPCION=:idOpcion", nativeQuery = true)
    public void activarOpcion(@Param("idRol") Integer idRol, @Param("idOpcion") Integer idOpcion, @Param("usuarioMod") Integer usuarioMod);
 
    @Query(value = "SELECT * FROM SIB.TBL_REL_ROL_OPCIONES WHERE FK_ROL =:idRol and I_ESTATUS = 1", nativeQuery = true)
    public List<RelRolOpciones> buscarOpciones(@Param("idRol") Integer idRol);
 
    @Modifying
    @Transactional
    @Query(value = "UPDATE SIB.TBL_REL_ROL_OPCIONES SET I_ESTATUS = 2, DT_FECHA_BAJA = now(), I_USUARIOID_MODIFICO =:usuarioMod,DT_FECHA_MODIFICO=now() WHERE FK_ROL =:idRol and FK_OPCION=:idOpcion", nativeQuery = true)
    public int inactivarRelacion(@Param("idRol") Integer idRol, @Param("idOpcion") Integer idOpcion,@Param("usuarioMod") Integer usuarioMod);
 
    @Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_REL_ROL_OPCIONES WHERE FK_ROL =:idRol and FK_OPCION=:idOpcion and I_ESTATUS = 1", nativeQuery = true)
    public int validarAcceso(@Param("idRol") Integer idRol, @Param("idOpcion") Integer idOpcion);
 
}