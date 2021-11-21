$(function () {
    $(".centeredMenu>.nav.navbar-nav a").on({
        mouseenter: function () {
            //ocultarOtrosSubmenus();
            var menuNumber = $(this).attr("menu-number");
            removerClases();
            cambiar($(this));
        }
        ,
        mouseleave: function () {
            $(this).css("background-color", "#428bca !important")
        }
        //$(this).css("background-color","#222d32");
    });

    $(".subcontainerNavbar2>.nav.submenu a").on("click", function () {
        $(".submenuSeleccionado").removeClass("submenuSeleccionado");
        $(this).addClass("submenuSeleccionado");
    });


    function cambiar(number) {

        var elemento = ".subcontainerNavbar2";

        $(elemento).each(function () {
            if ($(this).attr("menu-number") == $(number).attr("menu-number")) {
                $(this).css("display", "block");
                $(number).addClass("menuSeleccionado");
            }
            else {
                $(this).css("display", "none");
            }
        });
    }

    function removerClases() {
        $(".centeredMenu>.nav.navbar-nav a").removeClass("menuSeleccionado");
        $(".centeredMenu>.nav.navbar-nav a").css("background-color", "transparent !important");
    }

    function ocultarOtrosSubmenus() {

        $(".submenu").each(function () {
            $(this).css("display", "none");
        });
    }

})
