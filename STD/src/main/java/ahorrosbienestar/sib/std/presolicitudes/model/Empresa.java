package ahorrosbienestar.sib.std.presolicitudes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_EMPRESAS_CONVENIOS_MIGRACION")
public class Empresa {

    @Id
    @Column(name = "PK_ID_EMPRESA")
    private Integer id;

    @Column(name = "VC_NOMBRE_EMP", length = 150)
    private String nombre;

    @Column(name = "VC_PERSONA_CONTACTO", length = 150)
    private String personaContacto;

    @Column(name = "VC_CORREO_CONTACTO", length = 150)
    private String correoContacto;

    // Constructor vacío obligatorio para JPA
    public Empresa() {
    }

    // Constructor con parámetros
    public Empresa(Integer id, String nombre, String personaContacto, String correoContacto) {
        this.id = id;
        this.nombre = nombre;
        this.personaContacto = personaContacto;
        this.correoContacto = correoContacto;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }
}

