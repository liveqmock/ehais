$(function(){
    
    
    $('#bsTable').bootstrapTable({
        url: 'm_order_list.json',//请求后台的url
        method: 'get',//请求方式
        toolbar: '#toolbar',//工具按钮用哪个容器
        dataType: "json",
		pagination: true, //分页
		//search: true, //显示搜索框
        striped: true,//是否显示行间隔色
        cache: false,
        sortable: true,//是否启用排序
        sortOrder: 'asc',//排序方式
        clickToSelect: true,//是否启用点击选中行
        sidePagination: "server", //服务端处理分页
        uniqueId: 'order_sn',//每一行的唯一标识，一般为主键列
        columns: [{
            field: 'order_sn',
            title: '订单编号'
        }, {
            field: 'consignee',
            title: '收货人'
        }, {
            field: 'goods_name',
            title: '红酒名称 '
        }, {
            field: 'total',
            title: '价格'
        }, {
            field: 'order_status',
            title: '订单状态'
        }, {
            field: 'pay_status',
            title: '支付状态'
        }, {
            field: 'shipping_status',
            title: '运输状态'
        }, {
            field: 'order_time',
            title: '订单日期'
        }]
    });
    
    
});
