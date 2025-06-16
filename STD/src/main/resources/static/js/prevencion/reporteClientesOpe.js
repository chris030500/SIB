document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("uploadFormPEP");
  const fileInput = document.getElementById("fileUploadPEP");

form.addEventListener("submit", function (event) {
  event.preventDefault();

  if (!(fileInput instanceof HTMLInputElement)) {
    alert("Elemento inválido.");
    return;
  }

  const file = fileInput.files[0];
  if (!file) {
    alert("Debe seleccionar un archivo.");
    return;
  }

  const formData = new FormData();
  formData.append("fileUploadPEP", file);

  fetch(`${window.contextPath}/prevencion/uploadPEP`, {
    method: "POST",
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => alert(data.mensaje))
    .catch((error) => {
      console.error(error);
      alert("Ocurrió un error.");
    });
});