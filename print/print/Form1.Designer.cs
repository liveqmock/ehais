namespace print
{
    partial class WeiXinPrintExe
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.loginBtn = new System.Windows.Forms.Button();
            this.username = new System.Windows.Forms.TextBox();
            this.password = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.loginPanel = new System.Windows.Forms.Panel();
            this.lblMsg = new System.Windows.Forms.Label();
            this.userPanel = new System.Windows.Forms.Panel();
            this.btnLogout = new System.Windows.Forms.Button();
            this.lblStoreName = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.lbl_timestamp = new System.Windows.Forms.Label();
            this.lbl_pay_time = new System.Windows.Forms.Label();
            this.loginPanel.SuspendLayout();
            this.userPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(32, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(41, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "用户名";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // loginBtn
            // 
            this.loginBtn.Location = new System.Drawing.Point(239, 21);
            this.loginBtn.Name = "loginBtn";
            this.loginBtn.Size = new System.Drawing.Size(75, 53);
            this.loginBtn.TabIndex = 2;
            this.loginBtn.Text = "登录";
            this.loginBtn.UseVisualStyleBackColor = true;
            this.loginBtn.Click += new System.EventHandler(this.button1_Click);
            // 
            // username
            // 
            this.username.Location = new System.Drawing.Point(79, 18);
            this.username.Name = "username";
            this.username.Size = new System.Drawing.Size(129, 21);
            this.username.TabIndex = 3;
            // 
            // password
            // 
            this.password.Location = new System.Drawing.Point(79, 55);
            this.password.Name = "password";
            this.password.PasswordChar = '*';
            this.password.Size = new System.Drawing.Size(129, 21);
            this.password.TabIndex = 5;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(32, 58);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(29, 12);
            this.label2.TabIndex = 4;
            this.label2.Text = "密码";
            // 
            // loginPanel
            // 
            this.loginPanel.Controls.Add(this.lblMsg);
            this.loginPanel.Controls.Add(this.label1);
            this.loginPanel.Controls.Add(this.loginBtn);
            this.loginPanel.Controls.Add(this.password);
            this.loginPanel.Controls.Add(this.label2);
            this.loginPanel.Controls.Add(this.username);
            this.loginPanel.Location = new System.Drawing.Point(78, 12);
            this.loginPanel.Name = "loginPanel";
            this.loginPanel.Size = new System.Drawing.Size(373, 124);
            this.loginPanel.TabIndex = 6;
            // 
            // lblMsg
            // 
            this.lblMsg.AutoSize = true;
            this.lblMsg.Location = new System.Drawing.Point(77, 94);
            this.lblMsg.Name = "lblMsg";
            this.lblMsg.Size = new System.Drawing.Size(41, 12);
            this.lblMsg.TabIndex = 6;
            this.lblMsg.Text = "label3";
            // 
            // userPanel
            // 
            this.userPanel.Controls.Add(this.btnLogout);
            this.userPanel.Controls.Add(this.lblStoreName);
            this.userPanel.Location = new System.Drawing.Point(72, 15);
            this.userPanel.Name = "userPanel";
            this.userPanel.Size = new System.Drawing.Size(373, 100);
            this.userPanel.TabIndex = 7;
            this.userPanel.Visible = false;
            // 
            // btnLogout
            // 
            this.btnLogout.Location = new System.Drawing.Point(239, 25);
            this.btnLogout.Name = "btnLogout";
            this.btnLogout.Size = new System.Drawing.Size(75, 55);
            this.btnLogout.TabIndex = 1;
            this.btnLogout.Text = "退出";
            this.btnLogout.UseVisualStyleBackColor = true;
            this.btnLogout.Click += new System.EventHandler(this.button1_Click_1);
            // 
            // lblStoreName
            // 
            this.lblStoreName.AutoSize = true;
            this.lblStoreName.Location = new System.Drawing.Point(32, 46);
            this.lblStoreName.Name = "lblStoreName";
            this.lblStoreName.Size = new System.Drawing.Size(77, 12);
            this.lblStoreName.TabIndex = 0;
            this.lblStoreName.Text = "餐饮商家名称";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(177, 347);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(161, 12);
            this.label3.TabIndex = 8;
            this.label3.Text = "广州易海司信息科技有限公司";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(201, 315);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(113, 12);
            this.label4.TabIndex = 9;
            this.label4.Text = "尚价团队--粤北韶关";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("宋体", 30F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label5.Location = new System.Drawing.Point(24, 196);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(457, 40);
            this.label5.TabIndex = 10;
            this.label5.Text = "微信点餐订单打印小程序";
            // 
            // lbl_timestamp
            // 
            this.lbl_timestamp.AutoSize = true;
            this.lbl_timestamp.Location = new System.Drawing.Point(70, 271);
            this.lbl_timestamp.Name = "lbl_timestamp";
            this.lbl_timestamp.Size = new System.Drawing.Size(0, 12);
            this.lbl_timestamp.TabIndex = 11;
            // 
            // lbl_pay_time
            // 
            this.lbl_pay_time.AutoSize = true;
            this.lbl_pay_time.Location = new System.Drawing.Point(70, 296);
            this.lbl_pay_time.Name = "lbl_pay_time";
            this.lbl_pay_time.Size = new System.Drawing.Size(0, 12);
            this.lbl_pay_time.TabIndex = 12;
            // 
            // WeiXinPrintExe
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(504, 381);
            this.Controls.Add(this.lbl_pay_time);
            this.Controls.Add(this.lbl_timestamp);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.userPanel);
            this.Controls.Add(this.loginPanel);
            this.Name = "WeiXinPrintExe";
            this.Text = "微信点餐订单打印小程序";
            this.loginPanel.ResumeLayout(false);
            this.loginPanel.PerformLayout();
            this.userPanel.ResumeLayout(false);
            this.userPanel.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button loginBtn;
        private System.Windows.Forms.TextBox username;
        private System.Windows.Forms.TextBox password;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Panel loginPanel;
        private System.Windows.Forms.Panel userPanel;
        private System.Windows.Forms.Label lblStoreName;
        private System.Windows.Forms.Button btnLogout;
        private System.Windows.Forms.Label lblMsg;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label lbl_timestamp;
        private System.Windows.Forms.Label lbl_pay_time;
    }
}

