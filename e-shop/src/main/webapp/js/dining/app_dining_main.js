var app_dining_main=angular.module('app_dining_main',[]);
app_dining_main.controller('diningMainController', ['$scope',"$http", function($scope,$http) {
    $scope.total = 0.00;
    $scope.balance = 0.00;
    
    
    $http.post('/ws/haiDiningStatisticsJson', {},{}).  
    success(function(data) {  
        console.log("success");
        
        $scope.total = ((data.model.weixinAmount + data.model.cashAmount) / 100 ).toFixed(2);
        
    }).  
    error(function(err) {  
        //错误代码  
    	$scope.total = 0;
    });
    
    
}]);


