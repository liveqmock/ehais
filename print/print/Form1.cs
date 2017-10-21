﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Drawing.Printing;
using System.Security.Cryptography;
using System.Net;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Timers;
using System.Text.RegularExpressions;
using System.Media;

namespace print
{
    public partial class WeiXinPrintExe : Form
    {
        //private Font printFont;
        //private StreamReader streamToPrint;
        private String website = "http://mg.ehais.com";
        //private String website = "http://192.168.0.104";
        private String appkey = "Ehais";
        private String secret = "EhaisSecret";
        private String version = "v1";
        private String token = "";
        private String store_id = "";
        private System.Timers.Timer myTimer;
        private PrintDocument pd;
        private PrintPreviewDialog ppd;
        private String responseUser;
        private JObject joStore;
        private JObject joOrder;
        private JArray jOrderGoodsList;
        private Dictionary<double, string> dic = new Dictionary<double, string>();


        public WeiXinPrintExe()
        {
            InitializeComponent();

            //打印预览
            ppd = new PrintPreviewDialog();
            pd = new PrintDocument();

            PrintDialog BS = new PrintDialog();
            int x = BS.PrinterSettings.DefaultPageSettings.PaperSize.Width;//打印机默认纸张大小
            int y = BS.PrinterSettings.DefaultPageSettings.PaperSize.Height;
            Console.WriteLine("当前打印机的设置：" + x + "====" + y);

            //设置边距

            Margins margin = new Margins(10, 10, 10, 10);

            pd.DefaultPageSettings.Margins = margin;

            ////纸张设置默认

            Console.WriteLine("getYc(100):" + getYc(58) + "--" + getYc(100));

            //PaperSize pageSize = new PaperSize("First custom size", getYc(58), 600);

            //pd.DefaultPageSettings.PaperSize = pageSize;

            //打印事件设置            

            //pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
            pd.PrintPage += new PrintPageEventHandler(this.MyPrintDocument_PrintPage);

            ppd.Document = pd;


            myTimer = new System.Timers.Timer(10000);//定时周期20秒

            myTimer.Elapsed += myTimer_Elapsed;//到20秒了做的事件
            myTimer.AutoReset = true; //是否不断重复定时器操作

            this.lblMsg.Text = "";
        }

        // The Click event is raised when the user clicks the Print button.
        private void printButton_Click(object sender, EventArgs e)
        {
            
            try
            {
                

                //ppd.ShowDialog();


                //pd.Print();
                

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);

                MessageBox.Show(ex.Message, "打印出错", MessageBoxButtons.OK, MessageBoxIcon.Error);

                pd.PrintController.OnEndPrint(pd, new PrintEventArgs());

            }
        }


        private int getYc(double cm)

        {

            return (int)(cm / 25.4) * 100;

        }







        /// <summary>  
        /// 打印的格式  
        /// </summary>  
        /// <param name="sender"></param>  
        /// <param name="e"></param>  
        private void MyPrintDocument_PrintPage(object sender, System.Drawing.Printing.PrintPageEventArgs e)
        {
            /*如果需要改变自己 可以在new Font(new FontFamily("Arial"),11）中的“Arial”改成自己要的字体就行了，Arial 后面的数字代表字体的大小 
             System.Drawing.Brushes.Blue , 170, 10 中的 System.Drawing.Brushes.Blue 为颜色，后面的为输出的位置 */
            //Console.WriteLine("--------------------------------" + responseUser);

            //JObject joStore = (JObject)JsonConvert.DeserializeObject(responseUser);

            Graphics g = e.Graphics;

            Font printFont = new Font(new FontFamily("Arial"), 6);

            float fltYPos = 0;                   //每一行的Y坐标  
            float fltXPos = 0;                   //每一行的X坐标  
            float fltLeftMargin = e.MarginBounds.Left;                     //获取打印起始位置  
            float fltTopMargin = e.MarginBounds.Top;
            float fltScreenWidth = this.pd.DefaultPageSettings.PaperSize.Width - fltLeftMargin;
            float fltRowHeight = printFont.GetHeight(e.Graphics) + 10;


            

            StringFormat stringFormat = new StringFormat();
            stringFormat.Alignment = StringAlignment.Center;
            stringFormat.FormatFlags = StringFormatFlags.LineLimit;//自动换行
            fltXPos = fltLeftMargin;
            fltYPos = fltTopMargin;
            RectangleF rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth, fltRowHeight);//
            g.DrawString(joStore["model"]["storeName"].ToString() + "【尚阶餐饮】", printFont, Brushes.Black, rec, stringFormat);

            fltYPos += fltRowHeight;
            rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth, fltRowHeight);

            printFont = new Font(new FontFamily("Arial"), 10);
            g.DrawString("欢迎光临", printFont, Brushes.Black, rec, stringFormat);

            fltYPos += fltRowHeight;

            g.DrawLine(Pens.Black, fltXPos, fltYPos + fltRowHeight / 2, fltScreenWidth, fltYPos + fltRowHeight / 2);

            printFont = new Font(new FontFamily("Arial"), 6);


            SizeF sizeF = g.MeasureString("订单编号：", printFont);


            fltYPos += fltRowHeight;
            g.DrawString("订单编号：", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);

            printFont = new Font(new FontFamily("Arial"), 10);
            
            g.DrawString(joOrder["daySerialNumber"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos + sizeF.Width, fltYPos - 5);

            printFont = new Font(new FontFamily("Arial"), 6);

            fltYPos += fltRowHeight;
            g.DrawString("日期："+joOrder["addTime"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);


            sizeF = g.MeasureString("餐桌：", printFont);
            fltYPos += fltRowHeight;
            g.DrawString("餐桌：", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);

            printFont = new Font(new FontFamily("Arial"), 10);
            g.DrawString(joOrder["zipcode"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos + sizeF.Width, fltYPos - 5);

            printFont = new Font(new FontFamily("Arial"), 6);


            fltYPos += fltRowHeight;            
            g.DrawLine(Pens.Black, fltXPos, fltYPos + fltRowHeight / 2, fltScreenWidth, fltYPos + fltRowHeight / 2);

            fltYPos += fltRowHeight;
            g.DrawString("菜名", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);
            g.DrawString("数量", printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2, fltYPos);
            g.DrawString("单价", printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2 + fltScreenWidth / 2 / 3, fltYPos);
            g.DrawString("小计", printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2 + fltScreenWidth / 2 / 3 * 2, fltYPos);

            stringFormat.Alignment = StringAlignment.Near;

            int quantity = 0;
            long total = 0;
            foreach (JObject item in jOrderGoodsList)
            {

                if(Convert.ToInt64(item["orderId"].ToString()) == Convert.ToInt64(joOrder["orderId"].ToString()))
                {
                    fltYPos += fltRowHeight;
                    rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth/2 - fltXPos, fltRowHeight);
                    g.DrawString(item["goodsName"].ToString(), printFont, Brushes.Black, rec, stringFormat);
                    g.DrawString(item["goodsNumber"].ToString(), printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2, fltYPos);
                    g.DrawString(Convert.ToDouble(Convert.ToDouble(item["goodsPrice"].ToString()) / 100).ToString("0.00"), printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2 + fltScreenWidth / 2 / 3, fltYPos);
                    g.DrawString(Convert.ToDouble(Convert.ToDouble(Convert.ToDouble(item["goodsNumber"].ToString()) * Convert.ToDouble(item["goodsPrice"].ToString()) ) / 100 ).ToString("0.00"), printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2 + fltScreenWidth / 2 / 3 * 2, fltYPos);

                    quantity += Convert.ToInt32(item["goodsNumber"].ToString());
                    total += Convert.ToInt32(item["goodsNumber"].ToString()) * Convert.ToInt32(item["goodsPrice"].ToString());
                }
                
            }


            fltYPos += fltRowHeight;
            if(joOrder["postscript"].ToString().Length > 0 && joOrder["postscript"].ToString().Length <= 16)
            {
                g.DrawString("留言：" + joOrder["postscript"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos + fltRowHeight + 3);
            }else if(joOrder["postscript"].ToString().Length > 16)
            {
                
                rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth, fltRowHeight*4);

                g.DrawString("留言：" + joOrder["postscript"].ToString(), printFont, Brushes.Black, rec, stringFormat);


            }



            fltYPos += fltRowHeight*3;
            g.DrawLine(Pens.Black, fltXPos, fltYPos + fltRowHeight / 2, fltScreenWidth, fltYPos + fltRowHeight / 2);

            fltYPos += fltRowHeight;
            g.DrawString("合计：", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);
            g.DrawString(quantity.ToString(), printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2, fltYPos);
            g.DrawString(Convert.ToDouble(Convert.ToDouble(total) / 100).ToString("#0.00"), printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2 + fltScreenWidth / 2 / 3 * 2, fltYPos);

            fltYPos += fltRowHeight;
            g.DrawString("支付方式：", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);
            g.DrawString(joOrder["payName"].ToString(), printFont, System.Drawing.Brushes.Black, fltScreenWidth / 2, fltYPos);

            fltYPos += fltRowHeight;
            g.DrawString("地址："+joOrder["address"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);

            fltYPos += fltRowHeight;
            g.DrawString("联系电话：" + joOrder["tel"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);

            fltYPos += fltRowHeight;
            g.DrawString("服务商：广州易海司信息科技有限公司", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);

            fltYPos += fltRowHeight;
            g.DrawString("微信号：gzehais", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);


            ////////////////////////////厨房用单///////////////////////////////////////////////////////////////////

            fltYPos += (fltRowHeight * 3);
            rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth, fltRowHeight);
            stringFormat.Alignment = StringAlignment.Center;

            printFont = new Font(new FontFamily("Arial"), 10);

            g.DrawString("厨房用单【尚阶餐饮】", printFont, Brushes.Black, rec, stringFormat);

            fltYPos += fltRowHeight;
            rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth, fltRowHeight);

            g.DrawString("餐桌：" + joOrder["zipcode"].ToString(), printFont, Brushes.Black, rec, stringFormat);


            fltYPos += fltRowHeight;
            g.DrawLine(Pens.Black, fltXPos, fltYPos + fltRowHeight / 2, fltScreenWidth, fltYPos + fltRowHeight / 2);

            printFont = new Font(new FontFamily("Arial"), 6);
            

            fltYPos += fltRowHeight;
            g.DrawString("菜名", printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);
            g.DrawString("数量", printFont, System.Drawing.Brushes.Black, fltScreenWidth / 4 * 3, fltYPos);
            
            stringFormat.Alignment = StringAlignment.Near;

            
            foreach (JObject item in jOrderGoodsList)
            {

                if (Convert.ToInt64(item["orderId"].ToString()) == Convert.ToInt64(joOrder["orderId"].ToString()))
                {
                    fltYPos += fltRowHeight;
                    rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth / 2 - fltXPos, fltRowHeight);
                    g.DrawString(item["goodsName"].ToString(), printFont, Brushes.Black, rec, stringFormat);
                    //g.DrawString(item["goodsName"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos);
                    g.DrawString(item["goodsNumber"].ToString(), printFont, System.Drawing.Brushes.Black, fltScreenWidth / 4 * 3, fltYPos);
                   
                }

            }



            fltYPos += fltRowHeight;
            if (joOrder["postscript"].ToString().Length > 0 && joOrder["postscript"].ToString().Length <= 16)
            {
                g.DrawString("留言：" + joOrder["postscript"].ToString(), printFont, System.Drawing.Brushes.Black, fltXPos, fltYPos + fltRowHeight + 3);
            }
            else if (joOrder["postscript"].ToString().Length > 16)
            {

                rec = new RectangleF(fltXPos, fltYPos, fltScreenWidth, fltRowHeight * 4);

                g.DrawString("留言：" + joOrder["postscript"].ToString(), printFont, Brushes.Black, rec, stringFormat);


            }



        }

        

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            String username_val = this.username.Text;
            String password_val = this.password.Text;
            String timestamp = this.GetTimeStamp();

            MD5 md5 = new MD5CryptoServiceProvider();
            
            Dictionary<string, string> dic = new Dictionary<string, string>();
            dic.Add("appkey", appkey);
            dic.Add("version", version);
            dic.Add("timestamp", timestamp);
            dic.Add("username", username_val);
            dic.Add("password", password_val);
            
            String sign = this.GetSortedParas(dic)+secret;
            Console.WriteLine(sign);
            sign = this.EncryptWithMD5(sign);
            Console.WriteLine(sign);
            dic.Add("sign", sign);

            responseUser = this.Post(website+"/api/dining_manage_login", dic);

            Console.WriteLine(responseUser);

            joStore = (JObject)JsonConvert.DeserializeObject(responseUser);

            if(Convert.ToInt32(joStore["code"].ToString()) != 1)
            {
                this.lblMsg.Text = joStore["msg"].ToString();
                return;
            }

            this.lblMsg.Text = "";
            this.username.Text = "";
            this.password.Text = "";

            this.loginPanel.Visible = false;
            this.userPanel.Visible = true;

            this.lblStoreName.Text = joStore["model"]["storeName"].ToString();


            token = joStore["token"].ToString();
            store_id = joStore["model"]["storeId"].ToString();

            
            myTimer.Enabled = true; //定时器开始用

        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            this.loginPanel.Visible = true;
            this.userPanel.Visible = false;
            myTimer.Enabled = false; //定时器开始用
        }

        void myTimer_Elapsed(object sender, System.Timers.ElapsedEventArgs e)
        {
            Console.WriteLine("定时器获取最新订单数据:"+ DateTime.Now.ToString());
            String response = this.dining_order_list(store_id, token);
            //Console.WriteLine(response);
            JObject jo = (JObject)JsonConvert.DeserializeObject(response);
            JArray jArray = (JArray)JsonConvert.DeserializeObject(jo["rows"].ToString());
            if (jArray.Count > 0) {
                Warning();
            }

            if (jo["map"].ToString() != null && jo["map"].ToString() != "null" && jo["map"].ToString() != "")
            {
                Console.WriteLine("jo.map.tostring:"+jo["map"].ToString());
                jOrderGoodsList = (JArray)JsonConvert.DeserializeObject(jo["map"]["listGoods"].ToString());
                foreach (JObject item in jArray)
                {
                    joOrder = item;
                    pd.Print();
                }
            }else
            {
                Console.WriteLine("无订单信息===============================");
            }
            

            //

            //if (!ppd.IsAccessible)
            //{
            //    ppd.ShowDialog();
            //}

            //
        }

        public string dining_order_list(String _store_id,String _token)
        {
            String timestamp = this.GetTimeStamp() ;
            Dictionary<string, string> dic = new Dictionary<string, string>();
            dic.Add("appkey", appkey);
            dic.Add("version", version);
            dic.Add("timestamp", timestamp);
            dic.Add("token", _token);
            dic.Add("store_id", _store_id);

            String sign = this.GetSortedParas(dic) + secret;
            Console.WriteLine(sign);
            sign = this.EncryptWithMD5(sign);
            Console.WriteLine(sign);
            dic.Add("sign", sign);

            String response = this.Post(website + "/api/dining_order_list", dic);

            Console.WriteLine("订单表列返回数据：" + response);

            return response;
            
        }



        public string GetTimeStamp()
        {
            TimeSpan ts = DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0, 0);
            return Convert.ToInt64(ts.TotalMilliseconds).ToString();
        }

        public double getCurrentTime()
        {
            TimeSpan ts = DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0, 0);
            return ts.TotalSeconds;
        }


        public string Post(string url, Dictionary<string, string> dic)
        {
            string result = "";
            HttpWebRequest req = (HttpWebRequest)WebRequest.Create(url);
            req.Method = "POST";
            req.ContentType = "application/x-www-form-urlencoded";
            #region 添加Post 参数  
            StringBuilder builder = new StringBuilder();
            int i = 0;
            foreach (var item in dic)
            {
                if (i > 0)
                    builder.Append("&");
                builder.AppendFormat("{0}={1}", item.Key, item.Value);
                i++;
            }
            byte[] data = Encoding.UTF8.GetBytes(builder.ToString());
            req.ContentLength = data.Length;
            using (Stream reqStream = req.GetRequestStream())
            {
                reqStream.Write(data, 0, data.Length);
                reqStream.Close();
            }
            #endregion
            HttpWebResponse resp = (HttpWebResponse)req.GetResponse();
            Stream stream = resp.GetResponseStream();
            //获取响应内容  
            using (StreamReader reader = new StreamReader(stream, Encoding.UTF8))
            {
                result = reader.ReadToEnd();
            }
            return result;
        }


        public string EncryptWithMD5(string source)
        {
            byte[] sor = Encoding.UTF8.GetBytes(source);
            MD5 md5 = MD5.Create();
            byte[] result = md5.ComputeHash(sor);
            StringBuilder strbul = new StringBuilder(40);
            for (int i = 0; i < result.Length; i++)
            {
                strbul.Append(result[i].ToString("x2"));//加密结果"x2"结果为32位,"x3"结果为48位,"x4"结果为64位

            }
            return strbul.ToString();
        }

        private string GetSortedParas(Dictionary<string, string> dic)
        {
            dic = dic.OrderBy(key => key.Key).ToDictionary(keyItem => keyItem.Key, valueItem => valueItem.Value);
            var sbPara = new StringBuilder(1024);
            foreach (var para in dic)
            {
                //sbPara.AppendFormat("{0}={1}&", para.Key, para.Value);
                sbPara.Append( para.Value);
            }
            return sbPara.ToString();
        }

        private void Warning()
        {
            try
            {
                string filepath = "neworder.wav";
                System.Media.SoundPlayer sp = new SoundPlayer();
                if (File.Exists(filepath))
                {
                    sp.SoundLocation = filepath;
                    sp.Play();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        
    }
}