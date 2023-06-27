package ahorrosbienestar.sib.std.core_bancario.dto;

public class ProductosDto {

	private int p_no_producto;
	private int p_id_clasif;
	private String nombre_producto;

	public int getP_no_producto() {
		return p_no_producto;
	}

	public void setP_no_producto(int p_no_producto) {
		this.p_no_producto = p_no_producto;
	}

	public int getP_id_clasif() {
		return p_id_clasif;
	}

	public void setP_id_clasif(int p_id_clasif) {
		this.p_id_clasif = p_id_clasif;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

}
