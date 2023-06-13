package ahorrosbienestar.sib.std.opcionMenu.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_CAT_MENU")
public class Menus {
	
	@Column(name = "PK_ID_MENU")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_menu_seq")
	@SequenceGenerator(name = "tbl_menu_seq", sequenceName = "tbl_menu_seq", initialValue = 1, allocationSize=1)
	private int pk_id_menu;
	
	@Column(name = "VC_NOMBRE_MENU", columnDefinition = "VARCHAR(200)")
	private String vc_nombre_usuario;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;

	@OneToMany
	@JoinColumn(name = "Menu_idOpciones")
	private OpcionesMenu menu_idOpciones;

}
