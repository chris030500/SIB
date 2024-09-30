package ahorrosbienestar.sib.std.contabilidad.calculoeprc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EPRC {
	@Id
    @Column(name = "PROCESO")
    private int proceso;

    @Column(name = "MESPROCESO")
    private String mesproceso;

    @Column(name = "FECHACORTE")
    private String fechacorte;

    @Column(name = "USUARIOVOBO")
    private String usuariovobo;

    @Column(name = "FECHAREGISTROVOBO")
    private String fecharegistrovobo;

	public int getProceso() {
		return proceso;
	}

	public void setProceso(int proceso) {
		this.proceso = proceso;
	}

	public String getMesproceso() {
		return mesproceso;
	}

	public void setMesproceso(String mesproceso) {
		this.mesproceso = mesproceso;
	}

	public String getFechacorte() {
		return fechacorte;
	}

	public void setFechacorte(String fechacorte) {
		this.fechacorte = fechacorte;
	}

	public String getUsuariovobo() {
		return usuariovobo;
	}

	public void setUsuariovobo(String usuariovobo) {
		this.usuariovobo = usuariovobo;
	}

	public String getFecharegistrovobo() {
		return fecharegistrovobo;
	}

	public void setFecharegistrovobo(String fecharegistrovobo) {
		this.fecharegistrovobo = fecharegistrovobo;
	}
}
