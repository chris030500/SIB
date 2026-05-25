package ahorrosbienestar.sib.std.presolicitudes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;

@Access(AccessType.FIELD) 
@Entity
@Table(name = "TBL_CORREOS_SUC_VOBO")  // Especificamos el nombre de la tabla
public class CorreoSucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Definir el campo PK como autoincrementable
    @Column(name = "PK_ID_CORREO")  // Especificamos el nombre de la columna para la clave primaria
    private int pkIdCorreo;

    @Column(name = "I_SUC")  // Especificamos el nombre de la columna para la sucursal
    private int iSuc;

    @Column(name = "VC_CORREO", length = 450)  // Especificamos el nombre de la columna y su longitud
    private String vcCorreo;

    // Getters y Setters
    public int getPkIdCorreo() {
        return pkIdCorreo;
    }

    public void setPkIdCorreo(int pkIdCorreo) {
        this.pkIdCorreo = pkIdCorreo;
    }

    public int getiSuc() {
        return iSuc;
    }

    public void setiSuc(int iSuc) {
        this.iSuc = iSuc;
    }

    public String getVcCorreo() {
        return vcCorreo;
    }

    public void setVcCorreo(String vcCorreo) {
        this.vcCorreo = vcCorreo;
    }
}    

