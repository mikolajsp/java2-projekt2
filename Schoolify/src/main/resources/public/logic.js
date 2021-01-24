var DOMAIN = "http://localhost:8080";


function createRadar(schoolname, assesment, elementId) {
    var options = {
        responsive: true,
        maintainAspectRatio: false,
        scale: {
            angleLines: {
                display: false
            },
            ticks: {
                suggestedMin: 0.0,
                suggestedMax: 5.0
            }
        },
        legend: {
            display: false
        },
    };
    var d = {
        labels: ['Jakość', 'Przyjazność', 'Konkurencyjność', 'Rozwój', 'Dostępność'],
        datasets: [{
            data: [assesment.educational, assesment.friendliness, assesment.lowPrice, assesment.intrests, assesment.commute],
            backgroundColor: ["rgba(0,0,255,0.3)"],
            borderColor: ["rgba(0,0,255,0.5)"],
            pointBackgroundColor: ["rgba(31, 58, 147, 1)", "rgba(31, 58, 147, 1)", "rgba(31, 58, 147, 1)", "rgba(31, 58, 147, 1)", "rgba(31, 58, 147, 1)"],
            pointRadius: 5,
            label: schoolname,
        }],
    };

    var ctx = document.getElementById(elementId).getContext("2d");
    var myRadarChart = new Chart(ctx, {
        type: 'radar',
        data: d,
        options: options
    });

}


function getNBlue(n) {
    var inline = "";
    for (var i = 0; i < n; i++) {
        inline += "&bigstar;";
    }
    var whole = `<p style="font-size: 20px; font-family: 'FontAwesome', serif;color:blue;">` + inline + `</p>`;
    return whole;
}