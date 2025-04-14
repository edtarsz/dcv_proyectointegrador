document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('.form-cliente');

    const nombreInput = document.getElementById('nombre');
    const avisoNombre = document.getElementById('nombre-aviso');
    const regexNombre = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,100}$/;

    form.addEventListener('submit', function (e) {
        const nombre = nombreInput.value.trim();

        if (!regexNombre.test(nombre)) {
            e.preventDefault();
            mostrarAvisoNombre('Solo se permiten letras y espacios. Mínimo 3 caracteres.');
        }
    });

    nombreInput.addEventListener('input', function () {
        if (regexNombre.test(nombreInput.value.trim())) {
            ocultarAvisoNombre();
        }
    });

    nombreInput.addEventListener('paste', function (e) {
        const pasted = (e.clipboardData || window.clipboardData).getData('text');
        const total = nombreInput.value + pasted;
        if (!regexNombre.test(total)) {
            e.preventDefault();
            mostrarAvisoNombre('No se permiten caracteres especiales ni números en el nombre (máx. 100 caracteres).');
        }
    });

    function mostrarAvisoNombre(mensaje) {
        avisoNombre.textContent = mensaje;
        avisoNombre.style.display = 'block';
    }

    function ocultarAvisoNombre() {
        avisoNombre.textContent = '';
        avisoNombre.style.display = 'none';
    }

    const correoInput = document.getElementById('correo');
    const avisoCorreo = document.getElementById('correo-aviso');

    correoInput.addEventListener('input', function () {
        if (correoInput.validity.patternMismatch || correoInput.validity.tooLong) {
            avisoCorreo.style.display = 'block';
        } else {
            avisoCorreo.style.display = 'none';
        }
    });
});
