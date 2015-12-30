var backup_line_sta_container = {
    chart: {
        type: 'line',
        renderTo: 'backup_line_sta'
    },
    title: {
        text: '云备份统计'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: []
    },
    yAxis: {
        max : 0,
        min : 0,
        minTickInterval : 5,
        title: {
            text: '次数'
        },
        stackLabels: {
            enabled:true
        }
    },
    tooltip: {
        enabled: false,
        formatter: function() {
            return '<b>'+ this.series.name +'</b><br>'+this.x +': '+ this.y +'次';
        }
    },
    plotOptions: {
        line: {
            dataLabels: {
                enabled: true
            },
            enableMouseTracking: false
        }
    },
    series: [{
        name: '云备份',
        data: []
    }, {
        name: '云管理',
        data: []
    }]
};