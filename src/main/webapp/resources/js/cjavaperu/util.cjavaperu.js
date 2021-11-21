function hide(idModal) {
    $(idModal).modal('hide');
    if ($(".modal-backdrop") !== null) {
        $(".modal-backdrop").remove();
    }
}

function show(idModal) {
    $(idModal).modal('show');
}

function showMessage(sumary, detail, tipe) {
    PF('growl').renderMessage({"summary": sumary,
        "detail": detail,
        "severity": tipe});
    //"warn"
    //"info
    //error
}

function reiniciarInputs(idContenido) {
    $('#' + idContenido).find('form').find('input[type=text], textarea').val('');
}

function limpiarCSSTablaPrimeFaces() {
    console.log("Limpiar Tabla");
//    $('table').addClass('table table-bordered table-striped table-condensed table-hover smart-form has-tickbox');

    $('.table.table-bordered.table-striped.table-condensed.table-hover.smart-form.has-tickbox')
            .removeClass('table table-bordered table-striped table-condensed table-hover smart-form has-tickbox');

//    $('table').closest('div.ui-datatable').removeClass();
//    $('table thead th').removeClass('ui-state-default');
}

function agregarCSSTablaBootstrapPrimeFaces() {
    $('table').addClass('table table-bordered table-striped table-condensed table-hover smart-form has-tickbox');
    $('table').closest('div.ui-datatable').removeClass();
    $('table thead th').removeClass('ui-state-default');
}


function limpiarCSSInputPrimeFaces() {
    console.log("Limpiar Input");
//    $('table').addClass('table table-bordered table-striped table-condensed table-hover smart-form has-tickbox');

    $('.ui-inputNum.ui-widget')
            .removeClass('ui-inputNum ui-widget');

    $('.ui-inputfield.ui-inputtext.ui-widget.ui-state-default.ui-corner-all.pe-inputNumber')
            .removeClass('ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all');

    $('.pe-inputNumber')
            .addClass('form-control inputNumber input-sm');
    
    console.log("removing padding");
    $('.padding-right')
            .addClass('padding-right');
    
}
function cleanBody(){
    log("something");
    $.removeAttr('padding-right');
}

function limpiarCSSAutoCompletePrimefaces() {
    $('.ui-autocomplete-panel.ui-widget-content.ui-corner-all.ui-helper-hidden.ui-shadow')
            .css("z-index", "2000");
}

function limpiarBackgroundErrorPrimeFaces() {
    console.log("Limpiar ui-state-error");
    $('.ui-outputlabel.ui-widget.ui-state-error').addClass('ui-state-error-personalized');
    $('.ui-outputlabel.ui-widget.ui-state-error.ui-state-error-personalized')
            .removeClass('ui-state-error');
}

function changeInput(event, id) {
    console.log(event);
    if (event.keyCode == 13) {
        document.getElementById(id).focus();
        return false;
    }
}

function clickButton(event, id) {
    console.log(id);
    if (event.keyCode == 13) {
        document.getElementById(id).click();
        return false;
    }
}

function sumarUnaColumnaTabla(idTabla, columna) {

    var tabla = document.getElementById(idTabla);
    var fila = tabla.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    var total = 0;
    for (i = 0; i < fila.length; i++) {
        var celda = fila[i].getElementsByTagName("td")[columna];
        var texto = celda.getElementsByTagName("input");
        var num = parseFloat(texto[0].value);
        if (!isNaN(num)) {
            total = total + num;
        }
    }

    return total;
}

function sumarUnaColumnaTablaLabel(idTabla, columna) {
    var tabla = document.getElementById(idTabla);
    var fila = tabla.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    var total = 0;
    for (i = 0; i < fila.length; i++) {
        var celda = fila[i].getElementsByTagName("td")[columna];
        var texto = celda.getElementsByTagName("label");
        var num = parseFloat(texto[0].value);
        if (!isNaN(num)) {
            total = total + num;
        }
    }
    return total;
}

function agregarControlSidebarABody() {
    var claseBody = $("body").attr("class");
    console.log(claseBody);
    if (claseBody === 'skin-purple-light sidebar-mini' || claseBody === 'skin-purple-light sidebar-mini sidebar-collapse') {
        $("body").addClass("control-sidebar-open");
    } else {
        $('body').removeClass("control-sidebar-open");
    }
}

