package ahorrosbienestar.sib.std.presolicitudes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_TIPOEMPLEADO")
public class Puesto {

    @Id
    @Column(name = "PK_ID_TIPO")
    private int pkIdTipo;

    @Column(name = "VC_DESCRIPCION", length = 200)
    private String descripcion;

    // Getters y Setters
    public int getPkIdTipo() {
        return pkIdTipo;
    }

    public void setPkIdTipo(int pkIdTipo) {
        this.pkIdTipo = pkIdTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
