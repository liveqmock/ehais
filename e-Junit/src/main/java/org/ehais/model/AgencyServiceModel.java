package org.ehais.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("agency")
public class AgencyServiceModel {
	@XStreamImplicit
	private List<Company> companyList;
	public List<Company> getCompanyList() {
		return companyList;
	}
	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	//获取公司信息
	public Company getCompany(){
		return new Company();
	}

	
	
	
	/**
	 * @描述 代理服务的信息
	 * @作者 lgj628
	 * @日期 2016年8月17日
	 * @返回 Company
	 */
	@XStreamAlias("company")
	public class Company{
		@XStreamAsAttribute()
		private Integer adminId;//登录编号
		@XStreamAsAttribute()
		private String adminName;//登录帐号
		@XStreamAsAttribute()
		private String companyName;//公司名称
		private String introService;//业务说明
		
		@XStreamImplicit
		private List<Profession> professionList;//业务列表

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public Integer getAdminId() {
			return adminId;
		}

		public void setAdminId(Integer adminId) {
			this.adminId = adminId;
		}

		public String getAdminName() {
			return adminName;
		}

		public void setAdminName(String adminName) {
			this.adminName = adminName;
		}

		public String getIntroService() {
			return introService;
		}

		public void setIntroService(String introService) {
			this.introService = introService;
		}
		
		public List<Profession> getProfessionList() {
			return professionList;
		}

		public void setProfessionList(List<Profession> professionList) {
			this.professionList = professionList;
		}

		public Profession getProfession(){
			return new Profession();
		}


		/**
		 * @描述 代理服务的业务信息
		 * @作者 lgj628
		 * @日期 2016年8月17日
		 * @返回 Profession
		 */
		@XStreamAlias("profession")
		public class Profession{
			@XStreamAsAttribute()
			private String professionCode;//专业编码，用户不可见，系统按规则生成
			@XStreamAsAttribute()
			private String professionName;//专业显示名称
			@XStreamAsAttribute()
			private Integer isVoid;//可见是否生效
			@XStreamAsAttribute()
			private Integer sort;//排序
			@XStreamImplicit
			private List<Scope> scopeList;
			public Scope getScope(){
				return new Scope();
			}
			
			public String getProfessionCode() {
				return professionCode;
			}

			public void setProfessionCode(String professionCode) {
				this.professionCode = professionCode;
			}

			public String getProfessionName() {
				return professionName;
			}

			public void setProfessionName(String professionName) {
				this.professionName = professionName;
			}

			public Integer getIsVoid() {
				return isVoid;
			}

			public void setIsVoid(Integer isVoid) {
				this.isVoid = isVoid;
			}

			public List<Scope> getScopeList() {
				return scopeList;
			}

			public void setScopeList(List<Scope> scopeList) {
				this.scopeList = scopeList;
			}

			public Integer getSort() {
				return sort;
			}

			public void setSort(Integer sort) {
				this.sort = sort;
			}

			/**
			 * @描述 代理服务业务的对应的范围
			 * @作者 lgj628
			 * @日期 2016年8月17日
			 * @返回 scope
			 */
			@XStreamAlias("scope")
			public class Scope{
				@XStreamAsAttribute()
				private String scopeCode;//范围编号，按规则生成
				@XStreamAsAttribute()
				private String scopeName;//用户可见的范围名称
				@XStreamAsAttribute()
				private String control;//控件，目前只支持checkbox与radio
				@XStreamAsAttribute()
				private Integer isVoid;//可见是否生效
				@XStreamAsAttribute()
				private Integer sort;//排序
				@XStreamImplicit
				private List<Options> optionsList;//选项列表
				public Options getOptions(){
					return new Options();
				}
				
				public String getControl() {
					return control;
				}

				public void setControl(String control) {
					this.control = control;
				}

				public Integer getSort() {
					return sort;
				}

				public void setSort(Integer sort) {
					this.sort = sort;
				}

				public String getScopeCode() {
					return scopeCode;
				}

				public void setScopeCode(String scopeCode) {
					this.scopeCode = scopeCode;
				}

				public String getScopeName() {
					return scopeName;
				}

				public void setScopeName(String scopeName) {
					this.scopeName = scopeName;
				}

				public Integer getIsVoid() {
					return isVoid;
				}

				public void setIsVoid(Integer isVoid) {
					this.isVoid = isVoid;
				}

				public List<Options> getOptionsList() {
					return optionsList;
				}

				public void setOptionsList(List<Options> optionsList) {
					this.optionsList = optionsList;
				}

				/**
				 * @描述 代理服务业务范围对应的选项
				 * @作者 lgj628
				 * @日期 2016年8月17日
				 * @返回 options
				 */
				@XStreamAlias("options")
				public class Options{
					@XStreamAsAttribute()
					private String label;//对应标签
					@XStreamAsAttribute()
					private String value;//对应值
					
					public String getLabel() {
						return label;
					}
					public void setLabel(String label) {
						this.label = label;
					}
					public String getValue() {
						return value;
					}
					public void setValue(String value) {
						this.value = value;
					}
					
					
				}
			}
		}
		
	}


	
}
