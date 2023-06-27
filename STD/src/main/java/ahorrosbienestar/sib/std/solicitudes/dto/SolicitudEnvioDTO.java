package ahorrosbienestar.sib.std.solicitudes.dto;

import java.time.LocalDate;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

public class SolicitudEnvioDTO {

	private int pk_id_solicitud_envio;
	private int i_no_solicitud_core;
	private int i_producto_id;
	private int i_estatus;
	private String vc_obser_peticion;
	private String vc_obser_autoriza;
	private LocalDate dt_fecha_registro;
	private LocalDate dt_fecha_autorizacion;
	private LocalDate dt_fecha_envio;
	private String vc_hora_envio;
	private LocalDate dt_fecha_cancelacion;
	private String vc_hora_cancelacion;
	private Usuario fk_usr_registra;
	private Usuario fk_usr_autoriza;
	private Usuario fk_usr_cancela;
	private String vc_obser_cancelacion;
	private String empleado_autorizo;
	private String empleado_cancelo;
	private LocalDate dt_fecha_descarga;
	private int i_info_descarga;
	private String vc_ruta;
	private int i_identificador_ruta;
	private String vc_requisition_id;

	public String getEmpleado_cancelo() {
		return empleado_cancelo;
	}

	public void setEmpleado_cancelo(String empleado_cancelo) {
		this.empleado_cancelo = empleado_cancelo;
	}

	public int getPk_id_solicitud_envio() {
		return pk_id_solicitud_envio;
	}

	public void setPk_id_solicitud_envio(int pk_id_solicitud_envio) {
		this.pk_id_solicitud_envio = pk_id_solicitud_envio;
	}

	public int getI_no_solicitud_core() {
		return i_no_solicitud_core;
	}

	public void setI_no_solicitud_core(int i_no_solicitud_core) {
		this.i_no_solicitud_core = i_no_solicitud_core;
	}

	public int getI_producto_id() {
		return i_producto_id;
	}

	public void setI_producto_id(int i_producto_id) {
		this.i_producto_id = i_producto_id;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getVc_obser_peticion() {
		return vc_obser_peticion;
	}

	public void setVc_obser_peticion(String vc_obser_peticion) {
		this.vc_obser_peticion = vc_obser_peticion;
	}

	public String getVc_obser_autoriza() {
		return vc_obser_autoriza;
	}

	public void setVc_obser_autoriza(String vc_obser_autoriza) {
		this.vc_obser_autoriza = vc_obser_autoriza;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_autorizacion() {
		return dt_fecha_autorizacion;
	}

	public void setDt_fecha_autorizacion(LocalDate dt_fecha_autorizacion) {
		this.dt_fecha_autorizacion = dt_fecha_autorizacion;
	}

	public LocalDate getDt_fecha_envio() {
		return dt_fecha_envio;
	}

	public void setDt_fecha_envio(LocalDate dt_fecha_envio) {
		this.dt_fecha_envio = dt_fecha_envio;
	}

	public String getVc_hora_envio() {
		return vc_hora_envio;
	}

	public void setVc_hora_envio(String vc_hora_envio) {
		this.vc_hora_envio = vc_hora_envio;
	}

	public LocalDate getDt_fecha_cancelacion() {
		return dt_fecha_cancelacion;
	}

	public void setDt_fecha_cancelacion(LocalDate dt_fecha_cancelacion) {
		this.dt_fecha_cancelacion = dt_fecha_cancelacion;
	}

	public String getVc_hora_cancelacion() {
		return vc_hora_cancelacion;
	}

	public void setVc_hora_cancelacion(String vc_hora_cancelacion) {
		this.vc_hora_cancelacion = vc_hora_cancelacion;
	}

	public Usuario getFk_usr_registra() {
		return fk_usr_registra;
	}

	public void setFk_usr_registra(Usuario fk_usr_registra) {
		this.fk_usr_registra = fk_usr_registra;
	}

	public Usuario getFk_usr_autoriza() {
		return fk_usr_autoriza;
	}

	public void setFk_usr_autoriza(Usuario fk_usr_autoriza) {
		this.fk_usr_autoriza = fk_usr_autoriza;
	}

	public Usuario getFk_usr_cancela() {
		return fk_usr_cancela;
	}

	public void setFk_usr_cancela(Usuario fk_usr_cancela) {
		this.fk_usr_cancela = fk_usr_cancela;
	}

	public String getVc_obser_cancelacion() {
		return vc_obser_cancelacion;
	}

	public void setVc_obser_cancelacion(String vc_obser_cancelacion) {
		this.vc_obser_cancelacion = vc_obser_cancelacion;
	}

	public String getEmpleado_autorizo() {
		return empleado_autorizo;
	}

	public void setEmpleado_autorizo(String empleado_autorizo) {
		this.empleado_autorizo = empleado_autorizo;
	}

	public LocalDate getDt_fecha_descarga() {
		return dt_fecha_descarga;
	}

	public void setDt_fecha_descarga(LocalDate dt_fecha_descarga) {
		this.dt_fecha_descarga = dt_fecha_descarga;
	}

	public int getI_info_descarga() {
		return i_info_descarga;
	}

	public void setI_info_descarga(int i_info_descarga) {
		this.i_info_descarga = i_info_descarga;
	}

	public String getVc_ruta() {
		return vc_ruta;
	}

	public void setVc_ruta(String vc_ruta) {
		this.vc_ruta = vc_ruta;
	}

	public int getI_identificador_ruta() {
		return i_identificador_ruta;
	}

	public void setI_identificador_ruta(int i_identificador_ruta) {
		this.i_identificador_ruta = i_identificador_ruta;
	}

	public String getVc_requisition_id() {
		return vc_requisition_id;
	}

	public void setVc_requisition_id(String vc_requisition_id) {
		this.vc_requisition_id = vc_requisition_id;
	}

}
