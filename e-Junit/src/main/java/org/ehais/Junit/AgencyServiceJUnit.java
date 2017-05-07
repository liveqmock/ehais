package org.ehais.Junit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ehais.model.AgencyServiceModel;
import org.ehais.model.AgencyServiceModel.Company;
import org.ehais.util.FSO;

import com.thoughtworks.xstream.XStream;

public class AgencyServiceJUnit {

	public static void main(String[] args) throws IOException {
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);  
		xStream.setMode(XStream.NO_REFERENCES);
		
		AgencyServiceModel agency = new AgencyServiceModel();
		List<AgencyServiceModel.Company> companyList = new ArrayList<AgencyServiceModel.Company>();
		AgencyServiceModel.Company company = agency.getCompany();
		company.setCompanyName("AA");	
		company.setAdminId(1);
		company.setAdminName("andy1");
		company.setIntroService("业务介绍：@#$$%FSDFSDFGG%^*&^&**(&*9785645%#$!@~#!~!@");
		List<AgencyServiceModel.Company.Profession> professionList = new ArrayList<AgencyServiceModel.Company.Profession>();
		
		AgencyServiceModel.Company.Profession profession = company.getProfession();
		profession.setIsVoid(1);
		profession.setProfessionName("专利检索");
		profession.setProfessionCode("profession-adminName-WDdwSg13Df");
		
		List<AgencyServiceModel.Company.Profession.Scope> scopeList = new ArrayList<AgencyServiceModel.Company.Profession.Scope>();
		AgencyServiceModel.Company.Profession.Scope scope = profession.getScope();
		scope.setControl("checkbox");scope.setIsVoid(1);scope.setScopeCode("scope-profession-adminName-Wdf32Dse");scope.setScopeName("所属技术领域");
		List<AgencyServiceModel.Company.Profession.Scope.Options> optionsList = new ArrayList<AgencyServiceModel.Company.Profession.Scope.Options>();
		AgencyServiceModel.Company.Profession.Scope.Options options = scope.getOptions();
		options.setLabel("机械制造自动化");options.setValue("options-scope-profession-adminName-fdsdsdw2");
		optionsList.add(options);
		
		AgencyServiceModel.Company.Profession.Scope.Options options2 = scope.getOptions();
		options2.setLabel("电子通信信息");options2.setValue("options-scope-profession-adminName-fdsdsdw2");
		optionsList.add(options2);
		
		AgencyServiceModel.Company.Profession.Scope.Options options3 = scope.getOptions();
		options3.setLabel("生物医学化工");options3.setValue("options-scope-profession-adminName-fdsdsdw2");
		optionsList.add(options3);
		
		
		scope.setOptionsList(optionsList);
		scopeList.add(scope);
		profession.setScopeList(scopeList);
		professionList.add(profession);
		company.setProfessionList(professionList);
		companyList.add(company);
		
		AgencyServiceModel.Company company2 = agency.getCompany();
		company2.setCompanyName("BB");		
		company2.setAdminId(2);
		company2.setAdminName("tyler");
		company2.setIntroService("业务介绍：@#$$%FSDFSDFGG%^*&^&**(&*9785645%#$!@~#!~!@");
		companyList.add(company2);
		
		agency.setCompanyList(companyList);
		
		String xml = xStream.toXML(agency);
		System.out.println(xml);
		
		
		String filepath = "D:/workspace_luna/ehais/e-Junit/src/main/resources/config/AgencyService.xml";
		
		String xmlContent = FSO.BufferedReader(filepath);
		
		XStream xs = new XStream();
		xs.processAnnotations(AgencyServiceModel.class);
//		xs.processAnnotations(AgencyServiceModel.Company.class);
		
		AgencyServiceModel asModel = (AgencyServiceModel)xStream.fromXML(xmlContent);
		List<Company> c = asModel.getCompanyList();
		for (Company company3 : c) {
			System.out.println(company3.getCompanyName()+" -- "+company3.getIntroService());
		}
	}
}
