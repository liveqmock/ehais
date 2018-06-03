var app = new Vue({
  el: '#app',
  data: {
    message: 'Vue.js',
    withdrawSH:false,//显示或隐藏提现图层
    withdraw_deposit:"",
    withdraw_warn:false,
    withdraw_warn_txt:"请输入正确的提现金额",
    withdraw_warn_class:"",
    countic:countic,
    balance:balance,
  },
  methods:{
  	withdraw:function(){
  		if(this.countic == 0){
  			alert("提现正在进行中，不能重复提现");return ;
  		}
  		this.withdrawSH = true;
  	},
  	withdraw_submit:function(){
  		withdraw_submit(this);
  	},
  	withdraw_close:function(){
  		this.withdrawSH = false;
  	}
  }
});


//提现操作
function withdraw_submit(that){
	if(this.countic == 0){
		that.withdraw_warn = true;
		that.withdraw_warn_txt="不能重复提现!";
		return ;
	}
	
	if(that.withdraw_deposit == 0 || that.withdraw_deposit == null || that.withdraw_deposit == ""){
		that.withdraw_warn = true;
		that.withdraw_warn_class="warn";
		return ;
	}
	if(parseFloat(that.withdraw_deposit) < 1){
		that.withdraw_warn = true;
		that.withdraw_warn_txt="提现金额不能小于1.00元";
		return ;
	}
	if( parseFloat(that.withdraw_deposit) > parseFloat(that.balance) ){
		that.withdraw_warn = true;
		that.withdraw_warn_txt="提现金额不能大于余额";
		return ;
	}
	
	this.countic = 0;
	
	axios({
	  method: 'post',
	  url: '/withdraw_deposit!dining',
	  params: {
		  money: that.withdraw_deposit,
	  }
	}).then(function (response) {
	    console.log(response);
	    that.withdraw_warn = true;
	    that.withdraw_warn_txt = response.data.msg;
	}).catch(function (error) {
		console.log(error);
		alert("error:"+error);
	});
}


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