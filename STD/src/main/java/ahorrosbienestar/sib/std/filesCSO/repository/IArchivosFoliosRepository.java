package ahorrosbienestar.sib.std.filesCSO.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;

public interface IArchivosFoliosRepository extends JpaRepository<ArchivosFolios, Integer> {

    @Query(value = "SELECT * FROM SIB.TBL_ARCHIVOS_FOLIOS WHERE I_FOLIO = :idFolio AND I_ESTATUS = 1", nativeQuery = true)
    public List<ArchivosFolios> obtArchivos(@Param("idFolio") Integer idFolio);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SIB.TBL_ARCHIVOS_FOLIOS SET I_ESTATUS = 2 WHERE PK_ID_ARCHIVO = :idArchivo", nativeQuery = true)
    public int actualizar(@Param("idArchivo") Integer idArchivo);

    @Query(value = "SELECT COUNT(*) FROM SIB.TBL_ARCHIVOS_FOLIOS WHERE VC_NOMBRE_DOCTO = :name AND I_ESTATUS = 1 AND I_FOLIO = :idFolio", nativeQuery = true)
    public int existeNombre(@Param("name") String name, @Param("idFolio") String idFolio);

}