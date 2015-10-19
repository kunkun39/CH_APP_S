var app_column_sta_container = {
    chart: {
        type: 'column',
        renderTo: 'app_column_sta'
    },
    title: {
        text: '用户应用下载统计'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: []
    },
    yAxis: {
        title: {
            text: '次数'
        },
        min:0
    },
    tooltip: {
        formatter: function() {
            return '<b>' + this.series.name + this.y + '次';
        }
    },
    legend: {
        enabled: true
    },
    plotOptions: {
        series: {
            colorByPoint: true
        },
        column: {
            dataLabels: {
                enabled: true
            },
            pointPadding:0.2,
            borderWidth:0,
            enableMouseTracking: true
        }
    }, series: [
        {
            name: '应用下载数量',
            data: []
        }
    ]
};

var app_pie_sta_container = {
    chart: {
        renderTo: 'app_pie_sta',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
    },
    title: {
        text: '用户应用下载数量'
    },
    tooltip: {
        pointFormat: '<b>{series.name}: {point.percentage:.1f} %</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                color: '#ffffff',
                connectorColor: '#bbbbbb',
                format: '{point.name}类占总下载量{point.percentage:.1f} %'
            }
        }
    },
    legend: {
        enabled: true
    },
    series: [{
        type: 'pie',
        name: '用户应用下载数量种类比例',
        data: []
    }]
};
