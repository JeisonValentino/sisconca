function generarGraficoBarra() {
    var barChartData = {
        labels: ["llactahuaman", "s.mateo"],
        datasets: [{
                label: 'Ventas',
                backgroundColor: "rgba(220,220,220,0.5)",
                data: [760, 60.00]
            }, {
                label: 'Resultado (UP)',
                backgroundColor: "rgba(151,187,205,0.5)",
                data: [1430.00, 40.00]
            }]
    };
    var ctx = document.getElementById("barChart").getContext("2d");
    window.myBar = new Chart(ctx, {
        type: 'bar',
        data: barChartData,
        options: {
            elements: {
                rectangle: {
                    borderWidth: 2,
                    borderColor: 'rgb(0, 255, 0)',
                    borderSkipped: 'bottom'
                }
            },
            responsive: true,
            legend: {
                position: 'top',
            },
            title: {
                display: true
            }
        }
    });
}