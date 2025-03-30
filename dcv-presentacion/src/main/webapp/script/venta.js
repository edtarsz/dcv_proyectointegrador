/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function toggleDireccionEnvio(value) {
    const direccionContainer = document.getElementById('direccionEnvioContainer');
    const direccionInput = document.getElementById('direccionEnvio');
    
    if (value === 'si') {
        direccionContainer.style.display = 'block';
        direccionInput.required = true;
    } else {
        direccionContainer.style.display = 'none';
        direccionInput.required = false;
        direccionInput.value = '';
    }
}

// Validación del formulario
document.querySelector('.form-cliente').addEventListener('submit', function(e) {
    const envioSi = document.getElementById('envioSi');
    const direccionEnvio = document.getElementById('direccionEnvio');
    
    if (envioSi.checked && !direccionEnvio.value.trim()) {
        e.preventDefault();
        alert('Por favor ingrese la dirección de envío');
        return;
    }
});