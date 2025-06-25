/**
 * juan.trejo
 */
// @ts-nocheck

 function toggleMopMaximo() {
        const tipo = document.querySelector('input[name="tipoEvaluacion"]:checked').value;
        const mopMaxRow = document.getElementById('mopMaximoRow');

        if (tipo === 'PROSPECTOR') {
            mopMaxRow.style.display = 'none';
        } else {
            mopMaxRow.style.display = 'flex'; // usa 'flex' o 'block' según el diseño
        }
 }

    // En caso de recarga con valor seleccionado
 window.onload = function () {
        const checked = document.querySelector('input[name="tipoEvaluacion"]:checked');
        if (checked) toggleMopMaximo();
 };