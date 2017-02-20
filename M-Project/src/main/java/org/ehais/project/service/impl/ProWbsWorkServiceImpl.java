package org.ehais.project.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ehais.project.mapper.HaiUsersMapper;
import org.ehais.project.mapper.ProProjectMapper;
import org.ehais.project.mapper.ProTaskMapper;
import org.ehais.project.mapper.ProUserProjectMapper;
import org.ehais.project.mapper.ProWbsWorkMapper;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.HaiUsersExample;
import org.ehais.project.model.ProProject;
import org.ehais.project.model.ProProjectExample;
import org.ehais.project.model.ProTaskExample;
import org.ehais.project.model.ProTaskWithBLOBs;
import org.ehais.project.model.ProUserProject;
import org.ehais.project.model.ProUserProjectExample;
import org.ehais.project.model.ProWbsWork;
import org.ehais.project.model.ProWbsWorkExample;
import org.ehais.project.model.ProWbsWorkTree;
import org.ehais.project.model.ProWbsWorkExample.Criteria;
import org.ehais.project.service.ProWbsWorkService;
import org.ehais.project.util.mail.JavaMailUtils;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("proWbsWorkService")
public class ProWbsWorkServiceImpl extends ProCommonServiceImpl implements ProWbsWorkService{

	@Autowired
	private ProWbsWorkMapper proWbsWorkMapper;	
	@Autowired
	private ProProjectMapper proProjectMapper;	
	@Autowired
	private ProUserProjectMapper proUserProjectMapper;
	@Autowired
	private ProTaskMapper proTaskMapper;	
	@Autowired
	private HaiUsersMapper haiUserMapper;
	

	public List<ProWbsWorkTree> treeWbsWork(Integer proId) throws Exception {
		// TODO Auto-generated method stub
		ProWbsWorkExample example = new ProWbsWorkExample();
		example.or().andProIdEqualTo(proId);
		
		List<ProWbsWork> list = proWbsWorkMapper.selectByExample(example);
		TreeWbsService treeWbsService = new TreeWbsService();
		treeWbsService.setProWbsWorkList(list);
		treeWbsService.getTree(0);
		List<ProWbsWorkTree> proWbsWorkTreeList = treeWbsService.getProWbsWorkTreeList();
		return proWbsWorkTreeList;
	}
	
	public List<ProWbsWork> treeWbsWorkNotNullDate(Integer proId) throws Exception {
		// TODO Auto-generated method stub
		ProWbsWorkExample example = new ProWbsWorkExample();
		ProWbsWorkExample.Criteria criteria = example.createCriteria();
		criteria.andProIdEqualTo(proId);
		criteria.andPlanStartDateIsNotNull();
		criteria.andPlanEndDateIsNotNull();	

		List<ProWbsWork> list = proWbsWorkMapper.selectByExample(example);

		return list;
	}
	
	private String getLevel(Integer level){
		String str = "";
		for(int i = 0; i<level.intValue();i++){
			str+="-";
		}
		return str;
	}
	
	class TreeWbsService{
		private List<ProWbsWorkTree> proWbsWorkTreeList = new ArrayList<ProWbsWorkTree>();
		private Integer i = 1;
		private Integer l = 0;
		
		private List<ProWbsWork> proWbsWorkList;


		
		private List<ProWbsWork> _getChild(Integer pid){
			List<ProWbsWork> list = new ArrayList<ProWbsWork>();
			
			for (ProWbsWork proWbsWork : proWbsWorkList) {
				if(proWbsWork.getWbsParentId().intValue() == pid.intValue()){
					list.add(proWbsWork);
				}
			}
			
			return list;
		}
		
		public List<ProWbsWorkTree> getTree(Integer pid){
			List<ProWbsWork> list = this._getChild(pid);
			if(list.size()>0){
				l++;
				for (ProWbsWork proWbsWork : list) {
					ProWbsWorkTree tree = new ProWbsWorkTree();
					tree.setValueByProWbsWork(proWbsWork);
					tree.setLevel(l);
					proWbsWorkTreeList.add(tree);
					this.getTree(proWbsWork.getWbsId());
				}
				l--;
			}
			return null;
		}
		
		public List<ProWbsWork> getProWbsWorkList() {
			return proWbsWorkList;
		}

		public void setProWbsWorkList(List<ProWbsWork> proWbsWorkList) {
			this.proWbsWorkList = proWbsWorkList;
		}

		public List<ProWbsWorkTree> getProWbsWorkTreeList() {
			return proWbsWorkTreeList;
		}


	}

	public ReturnObject<ProWbsWork> proWbsWorkSave(Integer wbsId, String wbsName)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWbsWork> ro = new ReturnObject<ProWbsWork>();
		ProWbsWork record = new ProWbsWork();
		record.setWbsId(wbsId);
		record.setWbsName(wbsName);
		proWbsWorkMapper.updateByPrimaryKeySelective(record);
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProWbsWork> proWbsWorkSave(Integer wbsId, Integer userId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWbsWork> ro = new ReturnObject<ProWbsWork>();
		
		ProWbsWork record = new ProWbsWork();
		record.setWbsId(wbsId);
		record.setUserId(userId);
		proWbsWorkMapper.updateByPrimaryKeySelective(record);
		
		ProWbsWork wbs = proWbsWorkMapper.selectByPrimaryKey(wbsId);
		
		
		ProTaskWithBLOBs task = proTaskMapper.getByTaskName(wbs.getWbsName(), wbs.getProId());
		boolean isSend = false;
		//把分解计划导到任务表中，并发送邮件
		if(task == null){
			task = new ProTaskWithBLOBs();
			task.setTaskTitle(wbs.getWbsName());
			task.setProId(wbs.getProId());
			task.setUserId(userId);
			task.setProgress(0);
			Date date = new Date();
			task.setCreateDate(date);
			task.setUpdateDate(date);
			isSend = true;
			proTaskMapper.insertSelective(task);
		}else{
			if(task.getUserId() != userId){
				task.setUserId(userId);
				task.setProgress(0);
				Date date = new Date();
				task.setUpdateDate(date);
				isSend = true;
				proTaskMapper.updateByPrimaryKeyWithBLOBs(task);
			}
		}
		
		if(isSend && userId!=null && userId > 0){
			HaiUsers user = haiUserMapper.selectByPrimaryKey(userId);
			JavaMailUtils.createSimpleMail(
					user.getEmail(), 
					task.getTaskTitle(), 
					""
					);
		}
		
		
		
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProWbsWork> proWbsWorkSave(Integer wbsId,
			String planStartDate, String planEndDate, 
			String truthStartDate, String truthEndDate, Integer progress) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWbsWork> ro = new ReturnObject<ProWbsWork>();
		
		ProWbsWork record = new ProWbsWork();
		record.setWbsId(wbsId);
		if(planStartDate!=null && !planStartDate.equals("")){
			record.setPlanStartDate(DateUtil.formatDate(planStartDate, DateUtil.FORMATSTR_3));
		}else{
			record.setPlanStartDate(null);
		}
		if(planEndDate!=null && !planEndDate.equals("")){
			record.setPlanEndDate(DateUtil.formatDate(planEndDate, DateUtil.FORMATSTR_3));
		}else{
			record.setPlanEndDate(null);
		}
		if(truthStartDate!=null && !truthStartDate.equals("")){
			record.setTruthStartDate(DateUtil.formatDate(truthStartDate, DateUtil.FORMATSTR_3));
		}else{
			record.setTruthStartDate(null);
		}
		if(truthEndDate!=null && !truthEndDate.equals("")){
			record.setTruthEndDate(DateUtil.formatDate(truthEndDate, DateUtil.FORMATSTR_3));
		}else{
			record.setTruthEndDate(null);
		}
		record.setProgress(progress);
		proWbsWorkMapper.updateByPlanWBS(record);
		
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProWbsWork> proWbsWorkRemarkSave(Integer wbsId,
			String remark) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWbsWork> ro = new ReturnObject<ProWbsWork>();
		
		ProWbsWork record = new ProWbsWork();
		record.setWbsId(wbsId);
		record.setRemark(remark);
		proWbsWorkMapper.updateByPrimaryKeySelective(record);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProProject> projectList(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProProject> ro = new ReturnObject<ProProject>();
		ro.setCode(0);
		ProUserProjectExample example = new ProUserProjectExample();
		example.or().andUserIdEqualTo(userId);
		List<ProUserProject> listUserProject = proUserProjectMapper.selectByExample(example);
		List<Integer> proIdList = new ArrayList<Integer>();
		for (ProUserProject proUserProject : listUserProject) {
			proIdList.add(proUserProject.getProId());
			if(proUserProject.getState() == 1){
				ro.setCode(proUserProject.getProId());
			}
		}
		
		ProProjectExample examplePP = new ProProjectExample();
		if(proIdList.size()>0){
			examplePP.or().andProIdIn(proIdList);
			List<ProProject> projectList = proProjectMapper.selectByExample(examplePP);
			ro.setRows(projectList);
		}else{
			ro.setRows(null);
		}
		
		return ro;
	}

	public ReturnObject<ProWbsWork> proWbsWorkAdd(String wbsName,
			Integer wbsParentId, Integer proId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWbsWork> ro = new ReturnObject<ProWbsWork>();
		
		ProWbsWork record = new ProWbsWork();
		record.setWbsName(wbsName);
		record.setWbsParentId(wbsParentId);
		record.setProId(proId);
		proWbsWorkMapper.insertSelective(record);
		
		
		ro.setModel(record);
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public void setDefaultProject(Integer userId, Integer proId)
			throws Exception {
		// TODO Auto-generated method stub
		proUserProjectMapper.updateState(userId);
		proUserProjectMapper.updateDefaultState(userId,proId);
	}



	public ByteArrayOutputStream exportExcel(
			List<ProWbsWorkTree> proWbsWorkTreeList) throws Exception {
		// TODO Auto-generated method stub
		try{
			//第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
            HSSFSheet sheet = wb.createSheet("分享排名");  
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
            HSSFRow row = sheet.createRow(0);  
            //第四步，创建单元格样式：居中  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五步，创建表头单元格，并设置样式  
            HSSFCell cell;  
            
            cell = row.createCell(0);  
            cell.setCellValue("编号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(1);  
            cell.setCellValue("功能");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(10);  
            cell.setCellValue("计划开始");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(11);  
            cell.setCellValue("计划结束");  
            cell.setCellStyle(style);
            
            cell = row.createCell(12);  
            cell.setCellValue("实际开始");  
            cell.setCellStyle(style);
            
            cell = row.createCell(13);  
            cell.setCellValue("实际结束");  
            cell.setCellStyle(style);
            
            int i = 1;
            for (ProWbsWorkTree pwwt : proWbsWorkTreeList) {
            	row = sheet.createRow(i);  
            	row.createCell(0).setCellValue(pwwt.getWbsId());
            	row.createCell(pwwt.getLevel()).setCellValue(pwwt.getWbsName());
            	System.out.println(DateUtil.formatDate(pwwt.getPlanStartDate(), DateUtil.FORMATSTR_3));
            	row.createCell(10).setCellValue(DateUtil.formatDate(pwwt.getPlanStartDate(), DateUtil.FORMATSTR_3));
            	row.createCell(11).setCellValue(DateUtil.formatDate(pwwt.getPlanEndDate(), DateUtil.FORMATSTR_3));
            	row.createCell(12).setCellValue(DateUtil.formatDate(pwwt.getTruthStartDate(), DateUtil.FORMATSTR_3));
            	row.createCell(13).setCellValue(DateUtil.formatDate(pwwt.getTruthEndDate(), DateUtil.FORMATSTR_3));
            	
            	i++;
			}
            
          //第七步，将文件存到流中 
			ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
//            byte[] fileContent = os.toByteArray();  
//            ByteArrayInputStream excelStream = new ByteArrayInputStream(fileContent);  
  
            return os;//文件流  
            
            
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}


