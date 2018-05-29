var app = new Vue({
  el: '#app',
  data: {
    message: 'Vue.js',
    withdrawSH:false,//显示或隐藏提现图层
    withdraw_deposit:"",
    withdraw_warn:false,
    withdraw_warn_txt:"请输入正确的提现金额",
    withdraw_warn_class:"",
  },
  methods:{
  	withdraw:function(){
  		this.withdrawSH = true;
  	},
  	withdraw_submit:function(){
  		if(this.withdraw_deposit == 0 || this.withdraw_deposit == null || this.withdraw_deposit == ""){
  			this.withdraw_warn = true;
  			this.withdraw_warn_class="warn";
				return ;
  		}
  		
  		// 发送 POST 请求
			axios({
			  method: 'get',
			  url: '../manifest.json',
			  data: {
			    firstName: 'Fred',
			    lastName: 'Flintstone'
			  }
			}).then(function (response) {
		    console.log(response);
		    alert("response:"+JSON.stringify(response));
		  }).catch(function (error) {
		    console.log(error);
		    alert("error:"+error);
		  });
		  
		  
  	}
  }
});




Array.prototype.indexOf = function(val) { 
for (var i = 0; i < this.length; i++) { 
if (this[i] == val) return i; 
} 
return -1; 
};
Array.prototype.remove = function(val) { 
var index = this.indexOf(val); 
if (index > -1) { 
this.splice(index, 1); 
} 
};