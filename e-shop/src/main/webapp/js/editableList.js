(function($) {

    'use strict';

    $.extend($.fn.bootstrapTable.defaults, {
        editable: true,
    });
    
    
    
    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initTable = BootstrapTable.prototype.initTable,
        _initBody = BootstrapTable.prototype.initBody,
        _resetView = BootstrapTable.prototype.resetView;
        

    BootstrapTable.prototype.initTable = function() {
    	console.log("第一层：BootstrapTable.prototype.initTable");
        var that = this;
        _initTable.apply(this, Array.prototype.slice.apply(arguments));
        if (!this.options.editable) {
            return;
        }
        
        $.each(this.columns, function(i, column) {
            if (!column.editable) {
                return;
            }
            
			column.formatter = column.formatter || function(value, row, index) {
                return value;
            };
            
            column._formatter = column._formatter ? column._formatter : column.formatter;
            
            column.formatter = function(value, row, index) {
            	var result = column._formatter ? column._formatter(value, row, index) : value;
				return '<div id="' + column.field + '_'+index + '" value="'+value+'" code="'+(row[column.field+"_code"]=="undefined" || row[column.field+"_code"] == null ? "" : row[column.field+"_code"])+'" name="'+(row[column.field+"_name"] == "undefined" || row[column.field+"_name"] == null ? "" : row[column.field+"_name"])+'" type="'+column.editable.type+'" url="'+column.editable.url+'" class="editable-list-ehais" index="'+index+'" ></div>';
            }
            
            
            
        });
        
        
    };
    
    BootstrapTable.prototype.initBody = function() {
        console.log("第一层：BootstrapTable.prototype.initBody");
        _initBody.apply(this, Array.prototype.slice.apply(arguments));

        if (!this.options.editable) {
            return;
        }
        
        var eData = this.getData();

        this.$body.find('.editable-list-ehais').each(function () {
        	var index = $(this).attr("index");
        	var id = $(this).attr("id").replace("_"+index,"");
        	
        	$(this).editableInputSelect({
				success:function(data){
					eData[index][id] = data["id"];
					eData[index][id+"_code"] = data["code"];
					eData[index][id+"_name"] = data["name"];
					
					console.log(JSON.stringify(eData));
				}
			});
        });
        
        
    };
    
    BootstrapTable.prototype.initFixedColumns = function () {
    	
    };
    
    
    
    
//  $.extend($.fn.bootstrapTable.Constructor, {});
    
})(jQuery);