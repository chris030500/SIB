class Titulo extends HTMLElement {
	constructor() {
		super();
	}

	connectedCallback() {
		let shadowRoot = this.attachShadow({ mode: "open" });
		shadowRoot.innerHTML = `
		
			<h2 style="color: black; text-align: center;">configuración de contraseña</h2>
		
    	`;
	}
}

window.customElements.define("enviar-titulo", Titulo);