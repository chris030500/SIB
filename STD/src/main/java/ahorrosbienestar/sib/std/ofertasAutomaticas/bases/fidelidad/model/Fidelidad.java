package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_FIDELIDAD_OA")
public class Fidelidad {
	
	@Column(name = "PK_ID_FIDELIDAD")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_fidelidad;
	
	@Column(name = "I_CLIENTE_ID", columnDefinition = "INT")
	private int i_cliente_id;
	
	@Column(name = "VC_NOMBRE_COMPLETO_CLIENTE", columnDefinition = "VARCHAR(400)")
	private String vc_nombre_completo_cliente;
	
	@Column(name = "VC_REGION", columnDefinition = "VARCHAR(200)")
	private String vc_region;
	
	@Column(name = "VC_NOMBRE_SUCURS", columnDefinition = "VARCHAR(200)")
	private String vc_nombre_sucurs;
	
	@Column(name = "DT_FECHA_NACIMIENTO", columnDefinition = "DATE")
	private Date dt_fecha_nacimiento;
	
	@Column(name = "I_EDAD", columnDefinition = "BIGINT")
	private int i_edad;
	
	@Column(name = "I_CREDITO_ID", columnDefinition = "BIGINT")
	private long i_credito_id;
	
	@Column(name = "VC_NOMBRE_PRODUCTO", columnDefinition = "VARCHAR(200)")
	private String vc_nombre_producto;
	
	@Column(name = "D_MONTO_ORIGINAL", columnDefinition = "decimal(12,2) DEFAULT NULL")
	private Double d_monto_original;
	
	@Column(name = "I_MAX_DIA_MORA", columnDefinition = "INT")
	private int i_max_dia_mora;
	
	@Column(name = "VC_TELEFONO_CELULAR", columnDefinition = "VARCHAR(20)")
	private String vc_telefono_celular;
	
	@Column(name = "VC_TELEFONO_RECADOS", columnDefinition = "VARCHAR(20)")
	private String vc_telefono_recados;
	
	@Column(name = "VC_TELEFONO_EMPLEADO", columnDefinition = "VARCHAR(20)")
	private String vc_telefono_empleado;
	
	@Column(name = "VC_TELEFONO_REFERENCIA_1", columnDefinition = "VARCHAR(20)")
	private String vc_telefono_referencia_1;
	
	@Column(name = "VC_TELEFONO_REFERENCIA_2", columnDefinition = "VARCHAR(20)")
	private String vc_telefono_referencia_2;
	
	@Column(name = "VC_EXISTE_EN_REUS", columnDefinition = "INT DEFAULT 0")
	private int vc_existe_en_reus;
	
	@Column(name = "VC_EXISTE_ENCSN_PUBLICIDAD", columnDefinition = "INT DEFAULT 0")
	private int vc_existe_encsn_publicidad;
	
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATE")
	private Date dt_fecha_registro;
	
	@Column(name = "I_ESTATUS_OFERTA", columnDefinition = "INT DEFAULT 1")
	private int i_estatus_ofertas;
	
	@Column(name = "VC_PLAZO_OFERTA", columnDefinition = "VARCHAR(100)")
	private String vc_plazo_oferta;

	public int getPk_id_fidelidad() {
		return pk_id_fidelidad;
	}

	public void setPk_id_fidelidad(int pk_id_fidelidad) {
		this.pk_id_fidelidad = pk_id_fidelidad;
	}

	public int getI_cliente_id() {
		return i_cliente_id;
	}

	public void setI_cliente_id(int i_cliente_id) {
		this.i_cliente_id = i_cliente_id;
	}

	public String getVc_nombre_completo_cliente() {
		return vc_nombre_completo_cliente;
	}

	public void setVc_nombre_completo_cliente(String vc_nombre_completo_cliente) {
		this.vc_nombre_completo_cliente = vc_nombre_completo_cliente;
	}

	public String getVc_region() {
		return vc_region;
	}

	public void setVc_region(String vc_region) {
		this.vc_region = vc_region;
	}

	public String getVc_nombre_sucurs() {
		return vc_nombre_sucurs;
	}

	public void setVc_nombre_sucurs(String vc_nombre_sucurs) {
		this.vc_nombre_sucurs = vc_nombre_sucurs;
	}

	public Date getDt_fecha_nacimiento() {
		return dt_fecha_nacimiento;
	}

	public void setDt_fecha_nacimiento(Date dt_fecha_nacimiento) {
		this.dt_fecha_nacimiento = dt_fecha_nacimiento;
	}

	public int getI_edad() {
		return i_edad;
	}

	public void setI_edad(int i_edad) {
		this.i_edad = i_edad;
	}

	public long getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(long i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public String getVc_nombre_producto() {
		return vc_nombre_producto;
	}

	public void setVc_nombre_producto(String vc_nombre_producto) {
		this.vc_nombre_producto = vc_nombre_producto;
	}

	public Double getD_monto_original() {
		return d_monto_original;
	}

	public void setD_monto_original(Double d_monto_original) {
		this.d_monto_original = d_monto_original;
	}

	public int getI_max_dia_mora() {
		return i_max_dia_mora;
	}

	public void setI_max_dia_mora(int i_max_dia_mora) {
		this.i_max_dia_mora = i_max_dia_mora;
	}

	public String getVc_telefono_celular() {
		return vc_telefono_celular;
	}

	public void setVc_telefono_celular(String vc_telefono_celular) {
		this.vc_telefono_celular = vc_telefono_celular;
	}

	public String getVc_telefono_recados() {
		return vc_telefono_recados;
	}

	public void setVc_telefono_recados(String vc_telefono_recados) {
		this.vc_telefono_recados = vc_telefono_recados;
	}

	public String getVc_telefono_empleado() {
		return vc_telefono_empleado;
	}

	public void setVc_telefono_empleado(String vc_telefono_empleado) {
		this.vc_telefono_empleado = vc_telefono_empleado;
	}

	public String getVc_telefono_referencia_1() {
		return vc_telefono_referencia_1;
	}

	public void setVc_telefono_referencia_1(String vc_telefono_referencia_1) {
		this.vc_telefono_referencia_1 = vc_telefono_referencia_1;
	}

	public String getVc_telefono_referencia_2() {
		return vc_telefono_referencia_2;
	}

	public void setVc_telefono_referencia_2(String vc_telefono_referencia_2) {
		this.vc_telefono_referencia_2 = vc_telefono_referencia_2;
	}

	public int getVc_existe_en_reus() {
		return vc_existe_en_reus;
	}

	public void setVc_existe_en_reus(int vc_existe_en_reus) {
		this.vc_existe_en_reus = vc_existe_en_reus;
	}

	public int getVc_existe_encsn_publicidad() {
		return vc_existe_encsn_publicidad;
	}

	public void setVc_existe_encsn_publicidad(int vc_existe_encsn_publicidad) {
		this.vc_existe_encsn_publicidad = vc_existe_encsn_publicidad;
	}

	public int getI_estatus_ofertas() {
		return i_estatus_ofertas;
	}

	public void setI_estatus_ofertas(int i_estatus_ofertas) {
		this.i_estatus_ofertas = i_estatus_ofertas;
	}

	public Date getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(Date dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_plazo_oferta() {
		return vc_plazo_oferta;
	}

	public void setVc_plazo_oferta(String vc_plazo_oferta) {
		this.vc_plazo_oferta = vc_plazo_oferta;
	}
	
	
}
