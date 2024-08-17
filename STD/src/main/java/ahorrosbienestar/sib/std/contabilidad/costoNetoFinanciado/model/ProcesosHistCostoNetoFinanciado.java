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

    @Column(name = "FECHA_EJECUTO")
    private String fecha_ejecuto;

    @Column(name = "USUARIO_EJECUTO")
    private String usuario_ejecuto;

    @Column(name = "USUARIO_VOBO")
    private String usuario_vobo;

    @Column(name = "USUARIO_MODIFICO")
    private String usuario_modifico;

    @Column(name = "FECHA_REGISTRO_VOBO")
    private String fecha_registro_vobo;

    @Column(name = "FECHA_REGISTRO_MODIFICO")
    private String fecha_registro_modifico;

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

	public String getFecha_ejecuto() {
		return fecha_ejecuto;
	}

	public void setFecha_ejecuto(String fecha_ejecuto) {
		this.fecha_ejecuto = fecha_ejecuto;
	}

	public String getUsuario_ejecuto() {
		return usuario_ejecuto;
	}

	public void setUsuario_ejecuto(String usuario_ejecuto) {
		this.usuario_ejecuto = usuario_ejecuto;
	}

	public String getUsuario_vobo() {
		return usuario_vobo;
	}

	public void setUsuario_vobo(String usuario_vobo) {
		this.usuario_vobo = usuario_vobo;
	}

	public String getUsuario_modifico() {
		return usuario_modifico;
	}

	public void setUsuario_modifico(String usuario_modifico) {
		this.usuario_modifico = usuario_modifico;
	}

	public String getFecha_registro_vobo() {
		return fecha_registro_vobo;
	}

	public void setFecha_registro_vobo(String fecha_registro_vobo) {
		this.fecha_registro_vobo = fecha_registro_vobo;
	}

	public String getFecha_registro_modifico() {
		return fecha_registro_modifico;
	}

	public void setFecha_registro_modifico(String fecha_registro_modifico) {
		this.fecha_registro_modifico = fecha_registro_modifico;
	}
	
}
