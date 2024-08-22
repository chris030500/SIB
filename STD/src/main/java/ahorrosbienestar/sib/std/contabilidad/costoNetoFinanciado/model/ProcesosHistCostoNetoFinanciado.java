package ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProcesosHistCostoNetoFinanciado {
	@Id
    @Column(name = "PROCESO")
    private int proceso;

    @Column(name = "MES_PROCESO")
    private String mes_proceso;

    @Column(name = "FECHA_CORTE")
    private String fecha_corte;

    @Column(name = "USUARIO_VOBO")
    private String usuario_vobo;

    @Column(name = "FECHA_REGISTRO_VOBO")
    private String fecha_registro_vobo;

	public int getProceso() {
		return proceso;
	}

	public void setProceso(int proceso) {
		this.proceso = proceso;
	}

	public String getMes_proceso() {
		return mes_proceso;
	}

	public void setMes_proceso(String mes_proceso) {
		this.mes_proceso = mes_proceso;
	}

	public String getFecha_corte() {
		return fecha_corte;
	}

	public void setFecha_corte(String fecha_corte) {
		this.fecha_corte = fecha_corte;
	}

	public String getUsuario_vobo() {
		return usuario_vobo;
	}

	public void setUsuario_vobo(String usuario_vobo) {
		this.usuario_vobo = usuario_vobo;
	}

	public String getFecha_registro_vobo() {
		return fecha_registro_vobo;
	}

	public void setFecha_registro_vobo(String fecha_registro_vobo) {
		this.fecha_registro_vobo = fecha_registro_vobo;
	}


	
}
