package ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto;

public class ValidarEjecutarCronBase {
	private int i_ejecutar;
	private String expresionCron;
	public int getI_ejecutar() {
		return i_ejecutar;
	}
	public void setI_ejecutar(int i_ejecutar) {
		this.i_ejecutar = i_ejecutar;
	}
	public String getExpresionCron() {
		return expresionCron;
	}
	public void setExpresionCron(String expresionCron) {
		this.expresionCron = expresionCron;
	}
}
