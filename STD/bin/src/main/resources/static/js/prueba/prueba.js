// @ts-nocheck
class SellButton extends HTMLElement {
	constructor() {
		super();
	}

	connectedCallback() {
		let shadowRoot = this.attachShadow({ mode: "open" });
		shadowRoot.innerHTML = `
			<h5 class='card-title' style='color: #012970;'>Bienvenido</h5>
			<p>Sistema de transferencia de documentos.</p>
    	`;
	}
}

window.customElements.define("sell-button", SellButton);