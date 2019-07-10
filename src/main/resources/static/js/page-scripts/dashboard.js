"use strict";

$.getJSON('/viewCharts', {},(j) => {
    let ctx = document.getElementById("myChart");
    let data1 = j[0];
    let data2 = j[1];

    let data = {
        labels: data1.lables,
        datasets: data1.datasets.map((l) => ({
            label: l.name,
            fill: false,
            lineTension: 0.3,
            backgroundColor: [
                '#eedf9b'
            ],
            borderColor: [
                '#cca910'
            ],
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderWidth: 5,
            pointHoverRadius: 5,
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: l.value,
            spanGaps: true
        }))
    };

    let myChart = new Chart(ctx, {
        type: 'line',
        data: data,
        options: {
            title: {
                display: true,
                text: data1.appName,
                fontSize: 20,
                fontColor: '#585858'
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });

    let barCtx = document.getElementById("myChart2");
    let myBarChart = new Chart(barCtx, {
        type: 'bar',
        data: {
            labels: data2.lables,
            datasets: data2.datasets.map((d) => ({
                label: d.name,
                data: d.value,
                backgroundColor: [
                    '#eedf9b'
                ],
                borderColor: [
                    '#cca910'
                ],
                borderWidth: 2
            }))
        },
        options: {
            title: {
                display: true,
                text: data2.appName,
                fontSize: 20,
                fontColor: '#585858'
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
});