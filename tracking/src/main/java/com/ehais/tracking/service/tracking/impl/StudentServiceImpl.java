package com.ehais.tracking.service.tracking.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.EncryptUtils;
import org.ehais.util.UploadUtils;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.DepartmentDao;
import com.ehais.tracking.dao.GradesDao;
import com.ehais.tracking.dao.ProfessionalDao;
import com.ehais.tracking.dao.SchoolDao;
import com.ehais.tracking.dao.StudentDao;
import com.ehais.tracking.dao.UsersDao;
import com.ehais.tracking.entity.Department;
import com.ehais.tracking.entity.Grades;
import com.ehais.tracking.entity.Professional;
import com.ehais.tracking.entity.School;
import com.ehais.tracking.entity.Student;
import com.ehais.tracking.entity.Users;
import com.ehais.tracking.service.tracking.StudentService;

import net.sf.json.JSONObject;





@Service("studentService")
public class StudentServiceImpl  extends TrackingCommonServiceImpl implements StudentService{
	
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private GradesDao gradesDao;
	@Autowired
	private ProfessionalDao professionalDao;
	@Autowired
	private UsersDao usersDao;
	
	public ReturnObject<Student> student_list(Integer school_id) throws Exception{
		
		ReturnObject<Student> rm = new ReturnObject<Student>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Student> student_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String,Object>();
		this.storeIdMap(request, map);
		ReturnObject<Student> rm = studentDao.select(Student.class, page, len, map);
		
		Map<String ,Object> rmMap = new HashMap<String, Object>();
		
		Map<Integer,String> schoolMap = schoolMap();
		rmMap.put("school", schoolMap);
		rm.setMap(rmMap);
		
		Map<String,Object> mapWhere = new HashMap<String,Object>();
		this.storeIdMap(request, mapWhere);
		Map<Integer,String> departmentMap = departmentMap(mapWhere);
		rmMap.put("department", departmentMap);
		
		
		Map<Integer,String> gradesMap = gradesMap(null);
		rmMap.put("grades", gradesMap);
		
		Map<Integer,String> professionalMap = professionalMap(null);
		rmMap.put("professional", professionalMap);
		
		rm.setMap(rmMap);		
		rm.setCode(1);
		
		return rm;
	}

	public ReturnObject<Student> student_insert(Integer school_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();	
		Student model = new Student();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Student> student_insert_submit(Student model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		studentDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<Student> student_update(Integer school_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		
		Student model = studentDao.findById(Student.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Student> student_update_submit(Student model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		studentDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<Student> student_find(Integer school_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		
		
		Student model = studentDao.findById(Student.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Student> student_delete(Integer school_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		studentDao.delete(Student.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(Student model) throws Exception{
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "studentId", "学号", model.getStudentId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "name", "学生姓名", model.getName(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select", "schoolId", "学校", model.getSchoolId(), "请输入", "", "", this.schoolMapStr(), 0));
		bootStrapList.add(new BootStrapModel("select", "departmentId", "院系", model.getDepartmentId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select", "professionalId", "专业", model.getProfessionalId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select", "gradesId", "班级", model.getGradesId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "idCard", "身份证号", model.getIdCard(), "请输入", "", "", null, 0));

//		bootStrapList.add(new BootStrapModel("text", "createTime", "", model.getCreateTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "usersId", "", model.getUsersId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "mobile", "手机号码", model.getMobile(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "email", "邮箱", model.getEmail(), "请输入", "", "", null, 0));
		Map<String, String> sexMap = new HashMap<String, String>();
		sexMap.put("1", "男");
		sexMap.put("2", "女");
		bootStrapList.add(new BootStrapModel("radio", "sex", "", model.getSex(), "请输入", "", "", sexMap, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "politicalStatus", "", model.getPoliticalStatus(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "educationBackground", "教育背景", model.getEducationBackground(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "startTime", "", model.getStartTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "endTime", "", model.getEndTime(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "height", "身高", model.getHeight(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "weight", "体重", model.getWeight(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "vision", "", model.getVision(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "studentHome", "家庭地址", model.getStudentHome(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "qq", "QQ号", model.getQq(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "otherMobile", "手机号", model.getOtherMobile(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "address", "现在地址", model.getAddress(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "units", "单位", model.getUnits(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "status", "", model.getStatus(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<Student> student_login(String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
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
		List<Student> aList = studentDao.selectAll(Student.class, 1, 1, map, null, null);
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
	public ReturnObject<Student> student_file(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		String jsonStr = UploadUtils.upload_file(request, response);
		System.out.println(jsonStr);
		JSONObject json = JSONObject.fromObject(jsonStr);
		System.out.println(json.getString("msg"));
		InputStream is = new FileInputStream(json.getString("msg"));
		
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		Integer schoolId = (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID);
		List<School> schoolList = schoolDao.selectAll(School.class, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolId", schoolId);
		List<Department> departmentList = departmentList(map);
		List<Grades> gradesList = gradesList(null);
		List<Professional> professionalList = professionalList(null);
		
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) continue;
			
			
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
					Student s = new Student();
					  
					s.setStudentId(getCellStringValue(xssfRow.getCell(0)));
					s.setName(getCellStringValue(xssfRow.getCell(1)));
					s.setSex(getCellStringValue(xssfRow.getCell(2)).equals("男") ? 1 : 2);
					s.setIdCard(getCellStringValue(xssfRow.getCell(3)));
					s.setPoliticalStatus(this.change_political_status(getCellStringValue(xssfRow.getCell(4))));
					s.setSchoolId(getSchoolId(schoolList , getCellStringValue(xssfRow.getCell(5))));
					s.setDepartmentId(getDepartmentId(departmentList,getCellStringValue(xssfRow.getCell(6)).trim()));
					s.setProfessionalId(getProfessionalId(professionalList,getCellStringValue(xssfRow.getCell(7)).trim()));
					s.setGradesId(getGradesId(gradesList,getCellStringValue(xssfRow.getCell(8)).trim()));                	  
					s.setEducationBackground(getCellStringValue(xssfRow.getCell(9)).trim());
					s.setStartTime(getCellStringValue(xssfRow.getCell(10)));
					s.setEndTime(getCellStringValue(xssfRow.getCell(11)));
					s.setHeight(getCellStringValue(xssfRow.getCell(12)));
					s.setWeight(getCellStringValue(xssfRow.getCell(13)));						
					s.setVision(getCellStringValue(xssfRow.getCell(14)));
					s.setStudentHome(getCellStringValue(xssfRow.getCell(15)));
					s.setEmail(getCellStringValue(xssfRow.getCell(16)));
					s.setQq(getCellStringValue(xssfRow.getCell(17)));						
					s.setMobile(getCellStringValue(xssfRow.getCell(18)));
					s.setOtherMobile(getCellStringValue(xssfRow.getCell(19)));
					s.setAddress(getCellStringValue(xssfRow.getCell(20)));
					s.setUnits(getCellStringValue(xssfRow.getCell(21)));
					
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("studentId", s.getStudentId());
					Long l = studentDao.count(Student.class, map2);
					if(l.intValue() == 0){
						studentDao.insert(s);
					}
					
                }
			}
		}
		
		rm.setCode(1);
		rm.setMsg("导入成功");
		return rm;
	}

	@Override
	public ReturnObject<Student> weixin_student_save(HttpServletRequest request, WeiXinUserInfo userInfo)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		Map<String, Object> map = new HashMap<String, Object>();
		Users user = usersDao.findByKey(Users.class, "openid", userInfo.getOpenid());
		map.put("map", map);
		Date date = new Date();
		if(user == null){
			user = new Users();
			user.setCreate_time(date);
			user.setLogin_name(userInfo.getNickname());
			user.setOpenid(userInfo.getOpenid());
			usersDao.insert(user);
			Student studen = new Student();
			studen.setUsersId(user.getId());
			studen.setCreateTime(DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
			studentDao.insert(studen);
			rm.setModel(studen);
		}else{
			Student studen = studentDao.findByKey(Student.class, "usersId", user.getId());
			if(studen == null){
				studen = new Student();
				studen.setUsersId(user.getId());
				studen.setCreateTime(DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
				studentDao.insert(studen);
			}
			rm.setModel(studen);
		}
		rm.setMap(map);
		rm.setCode(1);
		rm.setMsg("保存成功");
		return rm;
	}

	@Override
	public ReturnObject<Student> student_bind_save(HttpServletRequest request, Integer wxid, Integer qid, String openid,
			Integer school, Integer department, Integer professional, Integer grades, String name, String studentId,
			String mobile, String email) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Student> rm = new ReturnObject<Student>();
		Integer sid = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//		if(sid == null || sid == 0 || qid == null || qid == 0 || sid != qid){
//			rm.setCode(10);rm.setMsg("非法学生用户");return rm;
//		}
		String sopenid = (String)request.getSession().getAttribute("openid");
		if(openid == null || openid.equals("") || sopenid == null || sopenid.equals("")  || !openid.equals(sopenid)){
			rm.setCode(11);rm.setMsg("非法微信用户");return rm;
		}
		
		Student student = studentDao.findById(Student.class, sid);
		if(student == null){
			rm.setCode(12);rm.setMsg("非法系统用户");return rm;
		}
		
		student.setSchoolId(school);
		student.setDepartmentId(department);
		student.setProfessionalId(professional);
		student.setGradesId(grades);
		student.setName(name);
		student.setStudentId(studentId);
		student.setMobile(mobile);
		student.setEmail(email);
		
		studentDao.update(student);
		rm.setCode(1);
		rm.setMsg("保存成功");
		
		return rm;
	}
	
}