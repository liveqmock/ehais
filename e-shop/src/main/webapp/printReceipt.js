
/**
* Created by hery on 2016/7/25.
*/
function initPrintActiveX(){
var div_ = document.createElement('div');
div_.style="display:none";
var object_=document.createElement('object');
object_.setAttribute("classid","clsid:AF33188F-6656-4549-99A6-E394F0CE4EA4");
object_.setAttribute("codebase","http://www.4Fang.net/4ff/sc_setup.exe");
object_.setAttribute("id","pazu");
object_.setAttribute("name","pazu");
var param_=document.createElement('param');
param_.setAttribute("name","License");
param_.setAttribute("value","2AE816BA3A24A9BA3F01162E7BF420F4");
object_.appendChild(param_);
div_.appendChild(object_);
document.getElementsByTagName("body")[0].appendChild(div_);
}
//预先选中的纸张
var strDefaultPaper = 'A4';
//1.载入打印机和纸张列表
function init() {
listPrinters();
//listPapers();
//2.页面载入立即打印
//demo();
}
//列出所有的打印机
function listPrinters() {
var ps = pazu.TPrinter.getPrinters();
//获得是一个以回车换行分隔的字符串
// alert(ps);
var pa = ps.split("\r\n");
for (var i = 0; i < pa.length; i++) {
//alert(pa[i]);
var Op = document.createElement("option");
Op.text = pa[i];
Op.value = pa[i];
try {
printers.add(Op);
} catch (ex) {
alert(ex.message)
}
}
}
function print(num) {
/*跳过IE打印选择提示*/
//默认打印机的名称设置为：GP-58120 Series
var isPromtUser = false;
/*页面设置*/
var sPaper = "A4";
// var sPrinter = printers.value;
var sPrinter = "GP-58120 Series";
pazu.TPrinter.marginTop = 1; //属性 上边距
pazu.TPrinter.marginBottom = 14; //属性 下边距
pazu.TPrinter.marginLeft = 7; //属性 左边距
pazu.TPrinter.marginRight = 8; //属性 右边距
pazu.TPrinter.footer = ""; //属性 页脚
pazu.TPrinter.header = ""; //属性 页眉
pazu.TPrinter.orientation = 1; //属性 整型：纸张方向 1=纵向 2=横向
pazu.TPrinter.paperName = sPaper; //属性 纸张大小名称
pazu.TPrinter.printerName = sPrinter; //属性 打印机名称
pazu.TPrinter.isPrintBackground = false; //属性 是否打印背景 true / false
pazu.TPrinter.isZoomOutToFit = true; //属性 是否缩放以适应大小打印 true / false
//pazu.TPrinter.printTemplate = sPT; //属性 打印模板的URL
pazu.TPrinter.copies = num; //属性 打印份数
//pazu.TPrinter.range = range.value; //属性 页面范围
pazu.TPrinter.isCopyByCopy = false; //属性 是否整份打印结束后再打印下一份 true / false
//pazu.TPrinter.getDefaultPrinter //方法 获得默认打印机的对象
//pazu.TPrinter.printToDefaultPrinter //方法 把要打印的字符串输送到默认打印机（配合getDefaultPrinter 使用）
//pazu.TPrinter.getPaperForms //方法 返回所有纸张格式的列表，以vbCrlf 分割
//pazu.TPrinter.getPrinters //方法 返回一个打印机列表，以vbCrlf 分割
//pazu.TPrinter.createPaper //方法 按指定的宽度和高度创建自定义纸张 请看示例
//pazu.TPrinter.doPrint //方法 执行打印
//pazu.TPrinter.doPrint_ //方法 执行打印但是不进行页面参数设置
//pazu.TPrinter.doPreview //方法 打印预览
//pazu.TPrinter.doPageSetup //方法 执行页面参数的设置
//pazu.TPrinter.showPageSetup //方法 弹出页面设置窗口
//pazu.TPrinter.writeHTMLtoOfficeFile 方法 把HTML导出为Office EXCEL或者 Word格式文件
//要指定打印那个Frame只要用javascript 让那个Frame获得焦点就可以了
//注意：这种方式下是不能预览的，只能立即打印。否则预览看到的是整个网页，而不是指定的frame
window.frames['myifrm'].focus();
//pazu.TPrinter.doPreview();//打印预览
pazu.TPrinter.doPrint(isPromtUser);
}
String.prototype.trim = function () {
return this.replace(/(^\s*)|(\s*$)/g, "");
}
function isNum(n) {
if (isNaN(n))return false;
return true;
}
