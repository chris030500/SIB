package ahorrosbienestar.sib.std.presolicitudes.model;


import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "TBL_EMPRESAS_VOBO")
public class EmpresaSIB {

    @Id
    @Column(name = "PK_ID_EMPRESA_CONVENIO")
    private Integer id;

    @Column(name = "VC_NOMBRE_EMP", length = 150)
    private String nombre;

    @Column(name = "VC_CORREO_EMP", length = 150)
    private String correoContacto;

    @Column(name = "VC_CONTACTO_EMP", length = 150)
    private String personaContacto;

    @Column(name = "I_FRECUENCIA")
    private Integer frecuencia;

    @Column(name = "I_ESTATUS_EMP")
    private Integer estatus;
    
    @Column(name = "DT_ULT_NOTIFICACION")
    private LocalDateTime fecha;

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

	public String getCorreoContacto() {
		return correoContacto;
	}

	public void setCorreoContacto(String correoContacto) {
		this.correoContacto = correoContacto;
	}

	public String getPersonaContacto() {
		return personaContacto;
	}

	public void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}

	public Integer getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
    
}

