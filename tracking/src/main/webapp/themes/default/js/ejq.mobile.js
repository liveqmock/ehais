/**
 * 
 */
(function($,window){
    "use strict";
    
    $.extend($.fn,{
    		vendorCss: function (attribute, value, obj) {
	            this.css(attribute.toLowerCase(),value,obj);
	            return this.css($.feat.cssPrefix + attribute, value, obj);
	        },
	
	        cssTranslate: function (val) {
	            this.vendorCss("Transform", "translate" + $.feat.cssTransformStart + val + $.feat.cssTransformEnd);
	        },
    });
    
    function detectUA($, userAgent) {
        $.os = {};

        $.os.webkit = userAgent.match(/WebKit\/([\d.]+)/) ? true : false;
        $.os.android = userAgent.match(/(Android)\s+([\d.]+)/) || userAgent.match(/Silk-Accelerated/) ? true : false;
        $.os.androidICS = $.os.android && userAgent.match(/(Android)\s4/) ? true : false;
        $.os.ipad = userAgent.match(/(iPad).*OS\s([\d_]+)/) ? true : false;
        $.os.iphone = !$.os.ipad && userAgent.match(/(iPhone\sOS)\s([\d_]+)/) ? true : false;
        $.os.ios7 = ($.os.ipad||$.os.iphone);
        $.os.webos = userAgent.match(/(webOS|hpwOS)[\s\/]([\d.]+)/) ? true : false;
        $.os.touchpad = $.os.webos && userAgent.match(/TouchPad/) ? true : false;
        $.os.ios = $.os.ipad || $.os.iphone;
        $.os.playbook = userAgent.match(/PlayBook/) ? true : false;
        $.os.blackberry10 = userAgent.match(/BB10/) ? true : false;
        $.os.blackberry = $.os.playbook || $.os.blackberry10|| userAgent.match(/BlackBerry/) ? true : false;
        $.os.chrome = userAgent.match(/Chrome/) ? true : false;
        $.os.opera = userAgent.match(/Opera/) ? true : false;
        $.os.fennec = userAgent.match(/fennec/i) ? true : userAgent.match(/Firefox/) ? true : false;
        $.os.ie = userAgent.match(/MSIE 10.0/i)||userAgent.match(/Trident\/7/i) ? true : false;
        $.os.ieTouch = $.os.ie && userAgent.toLowerCase().match(/touch/i) ? true : false;
        $.os.tizen = userAgent.match(/Tizen/i)?true:false;
        $.os.supportsTouch = ((window.DocumentTouch && document instanceof window.DocumentTouch) || "ontouchstart" in window);
        $.os.kindle=userAgent.match(/Silk-Accelerated/)?true:false;
        //features
        $.feat = {};

        $.feat.cssTransformStart = !$.os.opera ? "3d(" : "(";
        $.feat.cssTransformEnd = !$.os.opera ? ",0)" : ")";
        if ($.os.android && !$.os.webkit)
            $.os.android = false;


        //IE tries to be webkit
        if(userAgent.match(/IEMobile/i)){
            $.each($.os,function(ind){
                $.os[ind]=false;
            });
            $.os.ie=true;
            $.os.ieTouch=true;
        }
        var items=["Webkit","Moz","ms","O"];
        for(var j=0;j<items.length;j++){
            if(document.documentElement.style[items[j]+"Transform"]==="")
                $.feat.cssPrefix=items[j];
        }

    }

    detectUA($, navigator.userAgent);
    $.__detectUA = detectUA; //needed for unit tests

    $.uuid = function () {
        var S4 = function () {
            return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        };
        return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
    };
    
    $.create = function(type, props) {
        var elem;
        var f = new $();
        if (props || type[0] !== "<") {
            if (props.html){
                props.innerHTML = props.html;
                delete props.html;
            }

            elem = document.createElement(type);
            for (var j in props) {
                elem[j] = props[j];
            }
            f[f.length++] = elem;
        } else {
            elem = document.createElement("div");
            if (isWin8) {
                MSApp.execUnsafeLocalFunction(function() {
                    elem.innerHTML = type.trim();
                });
            } else
                elem.innerHTML = type;
            _shimNodes(elem.childNodes, f);
        }
        return f;
    };
    
    $.query = function (sel, what) {
        try {
            return $(sel,what);
        }
        catch(e) {
            return $();
        }
    };

    $.isObject = function (obj) {
        return typeof obj === "object";
    };


    $.is$ = function (obj) {
        return obj instanceof $;
    };
    //Shim to put touch events on the jQuery special event
    
})(jQuery,window);



(function($) {
    "use strict";
    
    var E_JQ_Mobile = function() {
        // Init the page
        var that = this;
        if (typeof define === "function" && define.amd) {
            that.autoLaunch=false;
            define("ejqmobile", [], function() {
                return $.ejq;
            });
        } else if (typeof module !== "undefined" && module.exports) {
            that.autoLaunch=false;
            $.ejq = that;
        }
        
        if (document.readyState === "complete" || document.readyState === "loaded") {
            //setupAFDom();
        	
            if(that.init)
                that.autoBoot();
            else{
                $(window).one("afui:init", function() {
                    that.autoBoot();
                });
            }
        } else $(document).ready(        		
            function() {
            	
                //setupAFDom();
                if(that.init)
                    that.autoBoot();
                else{
                    $(window).one("ejq:init", function() {
                        that.autoBoot();
                    });
                }
            },
        false);
        
    };
    
    var clickHandlers=[];
    E_JQ_Mobile.prototype = {
    		init:false,
            showLoading: true,
            showingMask: false,
            loadingText: "Loading Content",
            remotePages: {},
            history: [],
            views:{},
            _readyFunc: null,
            doingTransition: false,
            ajaxUrl: "",
            transitionType: "slide",
            firstPanel: "",
            hasLaunched: false,
            isLaunching:false,
            launchCompleted: false,
            activeDiv: "",
            customClickHandler: "",
            useOSThemes: true,
            overlayStatusbar: false,
            useAutoPressed: true,
            useInternalRouting:true,
            backButtonText: "Back",
            autoBoot: function() {
                this.hasLaunched = true;
                if (this.autoLaunch) {
                    this.launch();
                }
            },
    		/**
             * Show the loading mask with specificed text
               ```
               $.ejq.showMask()
               $.ejq.showMask('Fetching data...')
               ```

             * @param {string=} text
             * @title $.ejq.showMask(text);
             */
            showMask: function(text) {
                if (!text) text = this.loadingText || "";
                if($("#afui_mask").length == 0){
                		$("body").append("<div id='afui_mask'><div></div><br><h4></h4></div>")
                }
                $.query("#afui_mask>h4").html(text);
                $.query("#afui_mask").show();
                this.showingMask = true;
                $('#afui_mask').css({ 
					left: ($(window).width() - $('#afui_mask').outerWidth())/2,
					top: ($(window).height() - $('#afui_mask').outerHeight())/2
				});

//                var self = this;
//                //set another timeout to auto-hide the mask if something goes wrong after 15 secs
//                setTimeout(function() {
//                    if(self.showingMask) {
//                        self.hideMask();
//                    }
//                }, 15000);
            },
            /**
             * Hide the loading mask
                ```
                $.ejq.hideMask();
                ```
             * @title $.ejq.hideMask();
             */
            hideMask: function() {
                $.query("#afui_mask").hide();
                this.showingMask = false;
            },
            popup: function(opts) {
                return $.query(document.body).popup(opts);
            },
            /**
             * Register a data directive for a click event.  Plugins use this to allow
             * html based execution (see af.popup.js)
             ```
             $.ejq.registerDataDirective("[data-foo]",function(){
                console.log("foo was clicked");
             })
             ```
             * @param {string} selector
             * @param {function} callback to execute
             * @title $.ejq.registerDataDirective
             */
            registerDataDirective:function(selector,cb){
                clickHandlers.push({sel:selector,cb:cb});
            },
            setTitle:function(item){
                //find the header
                var title="";
                if(typeof(item)==="string"){
                    title=item;
                    item=$(this.activeDiv).closest(".view");
                }
                else if($(item).attr("data-title"))
                    title=$(item).attr("data-title");
                else if($(item).attr("title"))
                    title=$(item).attr("title");

                if(title)
                    $(item).closest(".view").children("header").find("h1").html(title);

            },
            showBackButton:function(view,isNewView) {
                var items=(this.views[view.prop("id")].length);
                var hdr=view.children("header");
                if(hdr.length===0) return;

                if(items>=2&&isNewView!==true){
                    //Add the back button if it's not there
                    if(hdr.find(".backButton").length===1) return;
                    hdr.prepend("<div class='backButton back'>返回</div>");
                    $(".backButton").click(function(){that.backButtonEvent();});
                }
                else {
                    hdr.find(".backButton").remove();
                }
            },
            backButtonEvent : function(){
            	console.log("点击返回!");
            },
            loadContentData: function(what,view,back,isNewView) {
                this.activeDiv = what;
//                this.setTitle(what,view,back,isNewView);
//                this.showBackButton(view,isNewView);
//                this.setActiveTab(what,view);
            },
            launch: function() {
//                if (this.hasLaunched === false || this.launchCompleted) {
//                	
//                    this.hasLaunched = true;
//                    return;
//                }
//                if(this.isLaunching)
//                    return true;
                this.isLaunching=true;
//                this.blockPageBounce();
                var that = this;

                var maskDiv = $.create("div", {
                    id: "afui_mask",
                    className: "ui-loader",
                    html: "<span class='ui-icon ui-icon-loading spin'></span><h1>Loading Content</h1>"
                }).css({
                    "z-index": 20000,
                    display: "none"
                });


            },
            
            /**
         * This is a shorthand call to the $.actionsheet plugin.  We wire it to the afui div automatically
           ```
           $.ejq.actionsheet("<a href='javascript:;' class='button'>Settings</a> <a href='javascript:;' class='button red'>Logout</a>")
           $.ejq.actionsheet("[{
                        text: 'back',
                        cssClasses: 'red',
                        handler: function () { $.ejq.goBack(); ; }
                    }, {
                        text: 'show alert 5',
                        cssClasses: 'blue',
                        handler: function () { alert("hi"); }
                    }, {
                        text: 'show alert 6',
                        cssClasses: '',
                        handler: function () { alert("goodbye"); }
                    }]");
           ```
         * @param {(string|Array.<string>)} links
         * @title $.ejq.actionsheet()
         */
            actionsheet: function(opts) {
	            return $.query(document.body).actionsheet(opts);
	        }
            
    };
    
    $.ejq = new E_JQ_Mobile();
    
})(jQuery);

$(function(){
	$.ejq.init=true;
    $.ejq.launch();
});

//toast
(function ($) {
	
    "use strict";
    $.fn.toast = function (opts) {
        return new Toast(this[0], opts);
    };
    var Toast = (function () {
        var Toast = function (containerEl, opts) {

            if (typeof containerEl === "string" || containerEl instanceof String) {
                this.container = document.getElementById(containerEl);
            } else {
                this.container = containerEl;
            }
            if (!this.container) {
                window.alert("Error finding container for toast " + containerEl);
                return;
            }
            if (typeof (opts) === "string" || typeof (opts) === "number") {
            	
                opts = {
                    message: opts
                };
            }
            this.addCssClass = opts.addCssClass ? opts.addCssClass : "";
            this.message = opts.message || "";
            this.delay=opts.delay||this.delay;
            this.position=opts.position||"";
            this.addCssClass+=" "+this.position;
            this.type=opts.type||"";
            //Check if the container exists
            this.container=$(this.container);
            if(this.container.find(".afToastContainer").length===0)
            {
                this.container.append("<div class='afToastContainer'></div>");
            }
            this.container=this.container.find(".afToastContainer");
            this.container.removeClass("tr br tl bl tc bc").addClass(this.addCssClass);
            if(opts.autoClose===false)
                this.autoClose=false;
            this.show();
            
            
				
        };

        Toast.prototype = {
            addCssClass: null,
            message: null,
            delay:5000,
            el:null,
            container:null,
            timer:null,
            autoClose:true,
            show: function () {
                var self = this;
                var markup = "<div  class='afToast "+this.type+"'>"+
                            "<div>" + this.message + "</div>"+
                            "</div>";
                this.el=$(markup).get(0);
                this.container.append(this.el);
                var $el=$(this.el);
                var height=this.el.clientHeight;
                $el.addClass("hidden");
                setTimeout(function(){
                    $el.css("height",height);
                    $el.removeClass("hidden");
                    $(".afToastContainer").css({ 
						left: ($(window).width() - $el.outerWidth())/2,
						top: ($(window).height() - $el.outerHeight())/2
					});
                },20);
                if(this.autoClose){
                    this.timer=setTimeout(function(){
                        self.hide();
                    },this.delay);
                }
                $el.bind("click",function(){
                    self.hide();
                });
            },

            hide: function () {
                var self = this;
                clearTimeout(this.timer);
                $(this.el).unbind("click").addClass("hidden");
                $(this.el).css("height","0px");
                self.remove();
            },

            remove: function () {
                var $el = $(this.el);
                $el.remove();
            },
            
        };
        return Toast;
    })();


    $.ejq.toast=function(opts){
        $(document.body).toast(opts);
    };
 
    
})(jQuery);

//taost end

//popup
(function ($) {
    "use strict";
    $.fn.popup = function (opts) {
        return new Popup(this[0], opts);
    };
    var queue = [];
    var Popup = function (containerEl, opts) {

        if (typeof containerEl === "string" || containerEl instanceof String) {
            this.container = document.getElementById(containerEl);
        } else {
            this.container = containerEl;
        }
        if (!this.container) {
            window.alert("Error finding container for popup " + containerEl);
            return;
        }

        try {
            if (typeof (opts) === "string" || typeof (opts) === "number")
                opts = {
                    message: opts,
                    cancelOnly: "true",
                    cancelText: "OK"
                };
            this.id = opts.id = opts.id || $.uuid(); //opts is passed by reference
            this.addCssClass = opts.addCssClass ? opts.addCssClass : "";
            this.suppressTitle = opts.suppressTitle || this.suppressTitle;
            this.title = opts.suppressTitle ? "" : (opts.title || "Alert");
            this.message = opts.message || "";
            this.cancelText = opts.cancelText || "Cancel";
            this.cancelCallback = opts.cancelCallback || function () {};
            this.cancelClass = opts.cancelClass || "button";
            this.doneText = opts.doneText || "Done";
            this.doneCallback = opts.doneCallback || function () {
                // no action by default
            };
            this.doneClass = opts.doneClass || "button";
            this.cancelOnly = opts.cancelOnly || false;
            this.onShow = opts.onShow || function () {};
            this.autoCloseDone = opts.autoCloseDone !== undefined ? opts.autoCloseDone : true;

            queue.push(this);
            if (queue.length === 1)
                this.show();
        } catch (e) {
            console.log("error adding popup " + e);
        }

    };

    Popup.prototype = {
        id: null,
        addCssClass: null,
        title: null,
        message: null,
        cancelText: null,
        cancelCallback: null,
        cancelClass: null,
        doneText: null,
        doneCallback: null,
        doneClass: null,
        cancelOnly: false,
        onShow: null,
        autoCloseDone: true,
        suppressTitle: false,
        show: function () {
            var self = this;
            var markup = "<div id='" + this.id + "' class='afPopup hidden "+ this.addCssClass + "'>"+
                        "<header>" + this.title + "</header>"+
                        "<div>" + this.message + "</div>"+
                        "<footer>"+
                             "<a href='javascript:;' class='" + this.cancelClass + "' id='cancel'>" + this.cancelText + "</a>"+
                             "<a href='javascript:;' class='" + this.doneClass + "' id='action'>" + this.doneText + "</a>"+
                             "<div style='clear:both'></div>"+
                        "</footer>"+
                        "</div>";

            var $el=$(markup);
            $(this.container).append($el);
            $el.bind("close", function () {
                self.hide();
            });

            if (this.cancelOnly) {
                $el.find("A#action").hide();
                $el.find("A#cancel").addClass("center");
            }
            $el.find("A").each(function () {
                var button = $(this);
                button.bind("click", function (e) {
                    if (button.attr("id") === "cancel") {
                        self.cancelCallback.call(self.cancelCallback, self);
                        self.hide();
                    } else {
                        self.doneCallback.call(self.doneCallback, self);
                        if (self.autoCloseDone)
                            self.hide();
                    }
                    e.preventDefault();
                });
            });
            self.positionPopup();
            $.blockUI(0.5);

            $el.bind("orientationchange", function () {
                self.positionPopup();
            });

            //force header/footer showing to fix CSS style bugs
            $el.find("header").show();
            $el.find("footer").show();
            setTimeout(function(){
                $el.removeClass("hidden").addClass("show");
                self.onShow(self);
            },50);
        },

        hide: function () {
            var self = this;
            $.query("#" + self.id).addClass("hidden");
            $.unblockUI();
            if(!$.os.ie&&!$.os.android){
                setTimeout(function () {
                    self.remove();
                }, 250);
            }
            else
                self.remove();
        },

        remove: function () {
            var self = this;
            var $el = $.query("#" + self.id);
            $el.unbind("close");
            $el.find("BUTTON#action").unbind("click");
            $el.find("BUTTON#cancel").unbind("click");
            $el.unbind("orientationchange").remove();
            queue.splice(0, 1);
            if (queue.length > 0)
                queue[0].show();
        },

        positionPopup: function () {
            var popup = $.query("#" + this.id);

            popup.css("top", ((window.innerHeight / 2.5) + window.pageYOffset) - (popup[0].clientHeight / 2) + "px");
            popup.css("left", (window.innerWidth / 2) - (popup[0].clientWidth / 2) + "px");
        }
    };
    var uiBlocked = false;
    $.blockUI = function (opacity) {
        if (uiBlocked)
            return;
        opacity = opacity ? " style='opacity:" + opacity + ";'" : "";
        $.query("BODY").prepend($("<div id='mask'" + opacity + "></div>"));
        $.query("BODY DIV#mask").bind("touchstart", function (e) {
            e.preventDefault();
        });
        $.query("BODY DIV#mask").bind("touchmove", function (e) {
            e.preventDefault();
        });
        uiBlocked = true;
    };

    $.unblockUI = function () {
        uiBlocked = false;
        $.query("BODY DIV#mask").unbind("touchstart");
        $.query("BODY DIV#mask").unbind("touchmove");
        $("BODY DIV#mask").remove();
    };


    $.ejq.registerDataDirective("[data-alert]",function(item){
        var $item=$(item);
        var message=$item.attr("data-message");
        if(message.length===0) return;
        $(document.body).popup(message);
    });

    $.ejq.popup=function(opts){
        return $(document.body).popup(opts);
    };

})(jQuery);
//popup end

//actionsheet start

(function($) {
    "use strict";
    $.fn.actionsheet = function(opts) {
        var tmp;
        for (var i = 0; i < this.length; i++) {
            tmp = new actionsheet(this[i], opts);
        }
        return this.length === 1 ? tmp : this;
    };
    var actionsheet = function(elID, opts) {
        if (typeof elID === "string" || elID instanceof String) {
            this.el = document.getElementById(elID);
        } else {
            this.el = elID;
        }
        if (!this.el) {
            window.alert("Could not find element for actionsheet " + elID);
            return;
        }

        if (this instanceof actionsheet) {
            if (typeof(opts) === "object") {
                for (var j in opts) {
                    this[j] = opts[j];
                }
            }
        } else {
            return new actionsheet(elID, opts);
        }

        var markStart = "<div id='af_actionsheet'><div style='width:100%'>";
        var markEnd = "</div></div>";
        var markup;
        var noop=function(){};
        if (typeof opts === "string") {
            markup = $(markStart + opts + "<a href='javascript:;' class='cancel'>取消</a>" + markEnd);
        } else if (typeof opts === "object") {
            markup = $(markStart + markEnd);
            var container = $(markup.children().get(0));
            opts.push({
                text: "取消",
                cssClasses: "cancel"
            });
            for (var i = 0; i < opts.length; i++) {
                var item = $("<a href='javascript:;'>" + (opts[i].text || "TEXT NOT ENTERED") + "</a>");
                item[0].onclick = (opts[i].handler || noop);
                if (opts[i].cssClasses && opts[i].cssClasses.length > 0)
                    item.addClass(opts[i].cssClasses);
                container.append(item);
            }
        }
        $(elID).find("#af_actionsheet").remove();
        $(elID).find("#af_action_mask").remove();
        $(elID).append(markup);

        markup.vendorCss("Transition", "all 0ms");
        this.el.style.overflow = "hidden";
        markup.on("click", "a", this.sheetClickHandler.bind(this));
        this.activeSheet = markup;
        markup.cssTranslate("0," + ((markup.height())) + "px");
        $(elID).append("<div id='af_action_mask' style='position:fixed;top:0px;left:0px;right:0px;bottom:0px;z-index:9998;background:rgba(0,0,0,.4)'/>");
        setTimeout(function() {
            markup.vendorCss("Transition", "all 300ms");
            markup.cssTranslate("0,0");
        }, 10);
        $("#af_action_mask").bind("touchstart touchmove touchend click",function(e){
            e.preventDefault();
            e.stopPropagation();
        });

    };
    actionsheet.prototype = {
        activeSheet: null,
        sheetClickHandler:function(){
            this.hideSheet();
            return false;
        },
        hideSheet: function() {
            this.activeSheet.off("click", "a", this.sheetClickHandler);
            $(this.el).find("#af_action_mask").unbind("click").remove();
            this.activeSheet.vendorCss("Transition", "all 0ms");
            var markup = this.activeSheet;
            var theEl = this.el;
            setTimeout(function() {
                markup.vendorCss("Transition", "all 300ms");
                markup.cssTranslate("0,"+(markup.height()+60)+"px");
                setTimeout(function() {
                    markup.remove();
                    markup = null;
                    theEl.style.overflow = "none";
                }, 350);
            }, 10);
        }
    };
    $.ejq.actionsheet=function(opts){
        return $(document.body).actionsheet(opts);
    };
})(jQuery);

//actonsheet end


//loading
//(function ($) {
//	
//  "use strict";
//  $.fn.loading = function (opts) {
//      return new Loading(this[0], opts);
//  };
//  var Loading = (function () {
//      var Loading = function (containerEl, opts) {
//
//          if (typeof containerEl === "string" || containerEl instanceof String) {
//              this.container = document.getElementById(containerEl);
//          } else {
//              this.container = containerEl;
//          }
//          if (!this.container) {
//              window.alert("Error finding container for toast " + containerEl);
//              return;
//          }
//          if (typeof (opts) === "string" || typeof (opts) === "number") {
//          	
//              opts = {
//                  message: opts
//              };
//          }
//          
//          //Check if the container exists
//          this.container=$(this.container);
//          if(this.container.find(".afToastContainer").length===0)
//          {
//              this.container.append("<div class='afToastContainer'></div>");
//          }
//          this.container=this.container.find(".afToastContainer");
//          if(opts.autoClose===false)
//              this.autoClose=false;
//          this.show();
//          
//          
//				
//      };
//
//      Loading.prototype = {
//          addCssClass: null,
//          message: null,
//          delay:5000,
//          el:null,
//          container:null,
//          timer:null,
//          autoClose:true,
//          show: function () {
//              var self = this;
//              var markup = '<div class="spinner-wrapper">'+
//							'<span class="spinner-text">'+ this.message +'</span>'+
//							'<span class="spinner"></span>'+
//							'</div>';
//		
//              this.el=$(markup).get(0);
//              this.container.append(this.el);
//              var $el=$(this.el);
//              var height=this.el.clientHeight;
//              $el.addClass("hidden");
//              setTimeout(function(){
//                  $el.css("height",height);
//                  $el.removeClass("hidden");
//                  $(".afToastContainer").css({ 
//						left: ($(window).width() - $el.outerWidth())/2,
//						top: ($(window).height() - $el.outerHeight())/2
//					});
//              },20);
//              if(this.autoClose){
//                  this.timer=setTimeout(function(){
//                      self.hide();
//                  },this.delay);
//              }
//              $el.bind("click",function(){
//                  self.hide();
//              });
//          },
//
//          hide: function () {
//              var self = this;
//              clearTimeout(this.timer);
//              $(this.el).unbind("click").addClass("hidden");
//              $(this.el).css("height","0px");
//              self.remove();
//          },
//
//          remove: function () {
//              var $el = $(this.el);
//              $el.remove();
//          },
//          
//      };
//      return Loading;
//  })();
//
//
//  $.ejq.loading=function(opts){
//      $(document.body).loading(opts);
//  };
// 
//  
//})(jQuery);
//loading end

