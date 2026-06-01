package ahorrosbienestar.sib.std.contrasena.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;

@Repository
public interface IHistContrasenaRepository extends JpaRepository<HistContrasena, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE SIB.TBL_HIST_CONTRASENIA AS hc " +
            "SET hc.VC_INDICADOR_ACTIVO = 0 " +
            "WHERE hc.Usr_historico_contrasena_id = ?1 " +
            "AND hc.VC_INDICADOR_ACTIVO = 1 " +
            "ORDER BY hc.PK_ID_PARAMETROS DESC LIMIT 1",
            nativeQuery = true)
    public int bajaContrasena(Integer usuario);

    @Query(value = "SELECT contra.* " +
            "FROM SIB.TBL_HIST_CONTRASENIA AS contra " +
            "INNER JOIN SIB.TBL_CAT_USUARIOS AS u " +
            "ON u.PK_ID_USUARIO = contra.Usr_historico_contrasena_id " +
            "WHERE u.PK_ID_USUARIO = :id " +
            "AND contra.VC_INDICADOR_ACTIVO = 1",
            nativeQuery = true)
    public Optional<HistContrasena> findByIdHisto(@Param("id") Integer id);

    @Query(value = "SELECT * FROM SIB.TBL_HIST_CONTRASENIA " +
            "WHERE Usr_historico_contrasena_id = ?1 " +
            "ORDER BY PK_ID_PARAMETROS DESC LIMIT 1",
            nativeQuery = true)
    public HistContrasena findTopByUsuarioByFechaDesc(Integer idUsuario);

    @Query(value = "SELECT * " +
            "FROM SIB.TBL_HIST_CONTRASENIA " +
            "WHERE Usr_historico_contrasena_id = :idUsuario " +
            "ORDER BY PK_ID_PARAMETROS DESC",
            nativeQuery = true)
    List<HistContrasena> findByUsuarioOrderByFechaDesc(
            @Param("idUsuario") Integer idUsuario);
}