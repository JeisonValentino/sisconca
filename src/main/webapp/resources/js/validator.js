function valSoloDigitos(e) {
    console.log(e);
    tecla = e.which || e.keyCode;
    patron = /\d/; // Solo acepta números
    te = String.fromCharCode(tecla);
    return !bloquearOtros(te) && ((patron.test(te) || tecla == 9 || tecla == 8 || tecla == 0 || tecla == 37 || tecla == 39 || tecla == 46 || tecla == 35 || tecla == 36));
}

function valSoloLetras(e) {
    tecla = e.which || e.keyCode;
    te = String.fromCharCode(tecla);
    return !bloquearOtros(te) && ((tecla >= 60 && tecla <= 90) || (tecla >= 97 && tecla <= 122) || tecla == 9 || tecla == 8 || tecla == 0 || tecla == 32 || tecla == 37 || tecla == 39 || tecla == 46 || tecla == 35 || tecla == 36 || tecla == 241 || tecla == 209);
}

function valSoloAlfanumerico(e) {
    tecla = e.which || e.keyCode;
    patron = /^[0-9a-zA-Z]*$/;
    te = String.fromCharCode(tecla);
    return !bloquearOtros(te) && ((patron.test(te) || tecla == 9 || tecla == 8 || tecla == 0));
}

function valSoloHora(inputField) {
    var temp = inputField.value + "0";
    var isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(temp);
    if (temp.length > 1) {
        if (isValid) {
            inputField.style.backgroundColor = '#fff';
        } else {
            inputField.style.backgroundColor = '#fba';
        }
    }
    return true;
}

function valEmail(e) {
    tecla = e.which || e.keyCode;
    patron = /^[0-9a-zA-Z@.]*$/;
    te = String.fromCharCode(tecla);
    return !bloquearOtrosEmail(te) && ((patron.test(te) || tecla == 9 || tecla == 8 || tecla == 0));
}

function valTelMovil(e) {
    tecla = e.which || e.keyCode;
    patron = /\d/; // Solo acepta números
    te = String.fromCharCode(tecla);
    return !bloquearOtrosTelMovil(te) && ((patron.test(te) || tecla == 9 || tecla == 8 || tecla == 0 || tecla == 37 || tecla == 39 || tecla == 46 || tecla == 35 || tecla == 36));
}

function valDecimales(e) {
    tecla = e.which || e.keyCode;
    patron = /\d/;
    te = String.fromCharCode(tecla);
    return !bloquearOtrosConPunto(te) && ((patron.test(te) || tecla == 9 || tecla == 8 || tecla == 0 || tecla == 37 || tecla == 39 || tecla == 46 || tecla == 35 || tecla == 36));
}

function bloquearOtros(valor) {
    var bloquea = false;
    if (valor.toString() == '$')
        bloquea = true;
    else if (valor.toString() == '%')
        bloquea = true;
    else if (valor.toString() == '.')
        bloquea = true;
    else if (valor.toString() == '\'')
        bloquea = true;
    else if (valor.toString() == '#')
        bloquea = true;

    return bloquea;
}

function bloquearOtrosEmail(valor) {
    var bloquea = false;
    if (valor.toString() == '$')
        bloquea = true;
    else if (valor.toString() == '%')
        bloquea = true;
    else if (valor.toString() == '\'')
        bloquea = true;
    else if (valor.toString() == '#')
        bloquea = true;

    return bloquea;
}

function bloquearOtrosTelMovil(valor) {
    var bloquea = false;
    if (valor.toString() == '$')
        bloquea = true;
    else if (valor.toString() == '%')
        bloquea = true;
    else if (valor.toString() == '\'')
        bloquea = true;

    return bloquea;
}

function bloquearOtrosConPunto(valor) {
    var bloquea = false;
    if (valor.toString() == '$')
        bloquea = true;
    else if (valor.toString() == '%')
        bloquea = true;
    else if (valor.toString() == '\'')
        bloquea = true;
    else if (valor.toString() == '#')
        bloquea = true;

    return bloquea;
}