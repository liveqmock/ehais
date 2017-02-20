package com.ehais.tracking.service.tracking.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.UploadUtils;
import org.springframework.stereotype.Service;

import com.ehais.tracking.entity.School;
import com.ehais.tracking.entity.Teacher;
import com.ehais.tracking.service.tracking.TeacherService;

import net.sf.json.JSONObject;




@Service("teacherService")
public class TeacherServiceImpl  extends TrackingCommonServiceImpl implements TeacherService{
	
	
	
	public ReturnObject<Teacher> teacher_list(Integer school_id) throws Exception{
		
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Teacher> teacher_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		this.storeIdMap(request, map);
		ReturnObject<Teacher> rm = teacherDao.select(Teacher.class, page, len, map);
		Map<String ,Object> rmMap = new HashMap<String, Object>();
		Map<Integer,String> schoolMap = schoolMap();
		rmMap.put("school", schoolMap);
		rm.setMap(rmMap);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Teacher> teacher_insert(Integer school_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();	
		Teacher model = new Teacher();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Teacher> teacher_insert_submit(Teacher model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		teacherDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<Teacher> teacher_update(Integer school_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		
		Teacher model = teacherDao.findById(Teacher.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Teacher> teacher_update_submit(Teacher model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		teacherDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<Teacher> teacher_find(Integer school_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		
		
		Teacher model = teacherDao.findById(Teacher.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Teacher> teacher_delete(Integer school_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		teacherDao.delete(Teacher.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(Teacher model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "jobNumber", "工号", model.getJobNumber(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "name", "姓名", model.getName(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "schoolId", "", model.getSchoolId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "createTime", "", model.getCreateTime(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "mobile", "手机号码", model.getMobile(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "email", "邮箱", model.getEmail(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "usersId", "", model.getUsersId(), "请输入", "", "", null, 0));
		
		Map<String, String> sexMap = new HashMap<String, String>();
		sexMap.put("1", "男");
		sexMap.put("2", "女");
		bootStrapList.add(new BootStrapModel("radio", "sex", "", model.getSex(), "请输入", "", "", sexMap, 0));
		bootStrapList.add(new BootStrapModel("text", "idcard", "身份证号", model.getIdcard(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "politicalStatus", "", model.getPoliticalStatus(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "educationBackground", "", model.getEducationBackground(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "startTime", "", model.getStartTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "height", "", model.getHeight(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "weight", "", model.getWeight(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "vision", "", model.getVision(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "qq", "", model.getQq(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "otherMobile", "", model.getOtherMobile(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "address", "详细地址", model.getAddress(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "status", "", model.getStatus(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "departmentId", "", model.getDepartmentId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "professionalId", "", model.getProfessionalId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "gradesId", "", model.getGradesId(), "请输入", "", "", null, 0));
		
		
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<Teacher> teacher_login(String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		rm.setCode(0);
		if(username == null || username.equals("") || password == null || password.equals("")){
			rm.setMsg("用户名与密码都不能为空");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		System.out.println(username + "     " + password);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", username);
		map.put("password", password);
		List<Teacher> aList = teacherDao.selectAll(Teacher.class, 1, 1, map, null, null);
		if(aList == null || aList.size() == 0){
			rm.setMsg("用户名与密码错误");
			return rm;
		}
		rm.setMsg("登录成功!");
		rm.setModel(aList.get(0));
		rm.setCode(1);
		return rm;
	}

	@Override
	public ReturnObject<Teacher> teacher_file(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Teacher> rm = new ReturnObject<Teacher>();
		String jsonStr = UploadUtils.upload_file(request, response);
		System.out.println(jsonStr);
		JSONObject json = JSONObject.fromObject(jsonStr);
		System.out.println(json.getString("msg"));
		InputStream is = new FileInputStream(json.getString("msg"));
		
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		
		List<School> schoolList = schoolList();
		
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) continue;
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                  XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                  if (xssfRow != null) {
//                      XSSFCell no = xssfRow.getCell(0);
//                      XSSFCell name = xssfRow.getCell(1);
//                      XSSFCell age = xssfRow.getCell(2);
//                      XSSFCell score = xssfRow.getCell(3);
                      
                      Teacher t = new Teacher();
                      t.setJobNumber(getCellStringValue(xssfRow.getCell(0)));
                      t.setName(getCellStringValue(xssfRow.getCell(1)));
                      t.setSex(getCellStringValue(xssfRow.getCell(2)).equals("男") ? 1 : 2);
                      t.setIdcard(getCellStringValue(xssfRow.getCell(3)));
                      t.setPoliticalStatus(this.change_political_status(getCellStringValue(xssfRow.getCell(4))));
                      t.setSchoolId(getSchoolId(schoolList , getCellStringValue(xssfRow.getCell(4))));
                      t.setEducationBackground(getCellStringValue(xssfRow.getCell(6)).trim());
                      t.setStartTime(getCellStringValue(xssfRow.getCell(7)));
                      t.setHeight(getCellStringValue(xssfRow.getCell(8)));
                      t.setWeight(getCellStringValue(xssfRow.getCell(9)));						
                      t.setVision(getCellStringValue(xssfRow.getCell(10)));						
                      t.setEmail(getCellStringValue(xssfRow.getCell(11)));
                      t.setQq(getCellStringValue(xssfRow.getCell(12)));
                      t.setMobile(getCellStringValue(xssfRow.getCell(13)));
                      t.setOtherMobile(getCellStringValue(xssfRow.getCell(14)));
                      t.setAddress(getCellStringValue(xssfRow.getCell(15)));
                      t.setPassword(EncryptUtils.md5("123456"));
                      teacherDao.insert(t);
                      
                  }
              }
		}
		
		rm.setCode(1);
		rm.setMsg("导入教师信息成功");
		   
		return rm;
	}
	
	
	
	
}
