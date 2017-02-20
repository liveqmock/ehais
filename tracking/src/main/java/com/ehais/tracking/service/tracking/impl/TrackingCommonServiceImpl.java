package com.ehais.tracking.service.tracking.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.ehais.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.ehais.tracking.dao.DepartmentDao;
import com.ehais.tracking.dao.GradesDao;
import com.ehais.tracking.dao.ProfessionalDao;
import com.ehais.tracking.dao.SchoolDao;
import com.ehais.tracking.dao.TeacherDao;
import com.ehais.tracking.entity.Department;
import com.ehais.tracking.entity.Grades;
import com.ehais.tracking.entity.Professional;
import com.ehais.tracking.entity.School;

public class TrackingCommonServiceImpl extends CommonServiceImpl{

	@Autowired
	protected TeacherDao teacherDao;
	@Autowired
	protected SchoolDao schoolDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private GradesDao gradesDao;
	@Autowired
	private ProfessionalDao professionalDao;
	
	protected List<School> schoolList() throws Exception{
		return schoolDao.selectAll(School.class, null);
	}
	
	protected Map<Integer ,String > schoolMap() throws Exception{
		Map<Integer ,String > map = new HashMap<Integer ,String>();
		List<School> list = schoolList();
		for (School school : list) {
			map.put(school.getId(), school.getName());
		}		
		return map;
	}
	
	protected Map<String ,String > schoolMapStr() throws Exception{
		Map<String ,String > map = new HashMap<String ,String>();
		List<School> list = schoolList();
		for (School school : list) {
			map.put(school.getId().toString(), school.getName());
		}		
		return map;
	}
	
	
	protected List<Department> departmentList(Map<String,Object> mapWhere) throws Exception{
		return departmentDao.selectAll(Department.class, mapWhere);
	}
	
	protected Map<Integer ,String > departmentMap(Map<String,Object> mapWhere) throws Exception{
		Map<Integer ,String > map = new HashMap<Integer ,String>();
		List<Department> list = departmentList(mapWhere);
		for (Department d : list) {
			map.put(d.getId(), d.getName());
		}		
		return map;
	}
	
	protected Map<String ,String > departmentMapStr(Map<String,Object> mapWhere) throws Exception{
		Map<String ,String > map = new HashMap<String ,String>();
		List<Department> list = departmentList(mapWhere);
		for (Department d : list) {
			map.put(d.getId().toString(), d.getName());
		}		
		return map;
	}
	
	
	
	
	protected List<Professional> professionalList(Map<String,Object> mapWhere) throws Exception{
		return professionalDao.selectAll(Professional.class, mapWhere);
	}
	
	protected Map<Integer ,String > professionalMap(Map<String,Object> mapWhere) throws Exception{
		Map<Integer ,String > map = new HashMap<Integer ,String>();
		List<Professional> list = professionalList(mapWhere);
		for (Professional m : list) {
			map.put(m.getId(), m.getName());
		}		
		return map;
	}
	
	
	protected Map<String ,String > professionalMapStr(Map<String,Object> mapWhere) throws Exception{
		Map<String ,String > map = new HashMap<String ,String>();
		List<Professional> list = professionalList(mapWhere);
		for (Professional m : list) {
			map.put(m.getId().toString(), m.getName());
		}		
		return map;
	}
	
	

	protected List<Grades> gradesList(Map<String,Object> mapWhere) throws Exception{
		return gradesDao.selectAll(Grades.class, mapWhere);
	}
	
	protected Map<Integer ,String > gradesMap(Map<String,Object> mapWhere) throws Exception{
		Map<Integer ,String > map = new HashMap<Integer ,String>();
		List<Grades> list = gradesList(mapWhere);
		for (Grades m : list) {
			map.put(m.getId(), m.getName());
		}		
		return map;
	}
	
	protected Map<String ,String > gradesMapStr(Map<String,Object> mapWhere) throws Exception{
		Map<String ,String > map = new HashMap<String ,String>();
		List<Grades> list = gradesList(mapWhere);
		for (Grades m : list) {
			map.put(m.getId().toString(), m.getName());
		}		
		return map;
	}
	
	
	
	protected Integer change_political_status(String status){
		
		if(status.equals("党员"))
			return 1;
		else if(status.equals("团员"))
			return 2;
		else if(status.equals("群众"))
			return 3;
		else 
			return 4;	
	}
	
	protected Integer getSchoolId (List<School> schoolList , String schoolName) {
		
		for (School school : schoolList) {
			if(school.getName().equals(schoolName)){
				return school.getId();
			}
		}
		
		return 0;
	}
	
	protected Integer getDepartmentId (List<Department> departmentList , String departmentName) {
		
		for (Department d : departmentList) {
			if(d.getName().equals(departmentName)){
				return d.getId();
			}
		}
		
		return 0;
	}
	
	protected Integer getProfessionalId (List<Professional> professionalList , String professionalName) {
		
		for (Professional p : professionalList) {
			if(p.getName().equals(professionalName)){
				return p.getId();
			}
		}
		
		return 0;
	}
	
	protected Integer getGradesId (List<Grades> gradesList , String gradesName) {
		
		for (Grades g : gradesList) {
			if(g.getName().equals(gradesName)){
				return g.getId();
			}
		}
		
		return 0;
	}

	
	
	public String getCellStringValue(XSSFCell cell) {        
        String cellValue = "";        
        switch (cell.getCellType()) {        
        case XSSFCell.CELL_TYPE_STRING://字符串类型     
           cellValue = cell.getStringCellValue();        
           if(cellValue.trim().equals("")||cellValue.trim().length()<=0)        
                cellValue=" ";        
            break;        
        case XSSFCell.CELL_TYPE_NUMERIC: //数值类型     
            cellValue = String.valueOf(cell.getNumericCellValue());        
            break;        
        case XSSFCell.CELL_TYPE_FORMULA: //公式     
            cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);        
            cellValue = String.valueOf(cell.getNumericCellValue());        
            break;        
        case XSSFCell.CELL_TYPE_BLANK:        
            cellValue=" ";        
            break;        
        case XSSFCell.CELL_TYPE_BOOLEAN:        
           break;        
        case XSSFCell.CELL_TYPE_ERROR:        
            break;        
        default:        
            break;        
        }        
        return cellValue;        
    } 
	
	
}
