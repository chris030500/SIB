package ahorrosbienestar.sib.std.presolicitudes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.math.BigDecimal;

import jakarta.persistence.Column;

@Entity
@Table(name = "TBL_PARAMS_SIMULADOR_CREDITO")
public class Parametrosimulador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "I_ID")
    private Integer id;

    @Column(name = "VC_DESC_FRECUENCIA", length = 20)
    private String descFrecuencia;

    @Column(name = "VC_FRECUENCIA", length = 1)
    private String frecuencia;

    @Column(name = "I_PERIODO")
    private Integer periodo;

    @Column(name = "I_INCREMENTO", precision = 10, scale = 2)
    private BigDecimal incremento;
    
    // Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescFrecuencia() {
		return descFrecuencia;
	}

	public void setDescFrecuencia(String descFrecuencia) {
		this.descFrecuencia = descFrecuencia;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getIncremento() {
		return incremento;
	}

	public void setIncremento(BigDecimal incremento) {
		this.incremento = incremento;
	}
    

}
