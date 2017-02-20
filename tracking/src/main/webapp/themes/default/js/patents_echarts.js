$(function(){
    var myChart1 = echarts.init(document.getElementById('report1'));

    // 指定图表的配置项和数据
    var option1 = {
title : {
    text: '平台需求与推送数量',
    subtext: '实时数据',
    x:'center'
},
tooltip : {
    trigger: 'item',
    formatter: "{a} <br/>{b} : {c} ({d}%)"
},
legend: {
    orient: 'vertical',
    left: 'left',
    data: ['总发布需求数量','总推送数量']
},
series : [
    {
        name: '平台实时数据',
        type: 'pie',
        radius : '55%',
        center: ['60%', '50%'],
        data:[
            {value:335, name:'总发布需求数量'},
            {value:310, name:'总推送数量'}
        ],
        itemStyle: {
            emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    }
]
};


    // 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);
    
    
    // 基于准备好的dom，初始化echarts实例
    var myChart2 = echarts.init(document.getElementById('report2'));

    // 指定图表的配置项和数据
    var option2 = {
        title: {
            text: '发布需求地区分布图'
        },
        tooltip: {},
        legend: {
            data:['发布需求数']
        },
        xAxis: {
            data: ["广州","深圳","佛山","江门","清远","珠海"]
        },
        yAxis: {},
        series: [{
            name: '发布需求数',
            type: 'bar',
            data: [530, 232, 150, 10, 10, 20]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart2.setOption(option2); 
});