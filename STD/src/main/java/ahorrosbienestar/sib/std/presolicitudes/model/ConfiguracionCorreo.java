package ahorrosbienestar.sib.std.presolicitudes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "TBL_CONFIGURACION_CORREO_VOBO")
public class ConfiguracionCorreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_CONF")  // Nombre de la columna en la base de datos
    private Long id;

    @Column(name = "VC_CORREO_REMITENTE")  // Nombre de la columna en la base de datos
    private String correoRemitente;

    @Column(name = "VC_PASSWORD")  // Nombre de la columna en la base de datos
    private String password;

    @Column(name = "VC_MENSAJE")  // Nombre de la columna en la base de datos
    private String mensaje;
    
    @Column(name = "VC_CRON")  // Nombre de la columna en la base de datos
    private String cronExpresion;
    
    // Getters y setters

    @Override
	public String toString() {
		return "ConfiguracionCorreo [id=" + id + ", correoRemitente=" + correoRemitente + ", password=" + password
				+ ", mensaje=" + mensaje + ", getId()=" + getId() + ", getCorreoRemitente()=" + getCorreoRemitente()
				+ ", getPassword()=" + getPassword() + ", getMensaje()=" + getMensaje() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

	public String getCronExpresion() {
		return cronExpresion;
	}

	public void setCronExpresion(String cronExpresion) {
		this.cronExpresion = cronExpresion;
	}
    
}


