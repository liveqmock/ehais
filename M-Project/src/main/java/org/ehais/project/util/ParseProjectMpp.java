package org.ehais.project.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.project.service.ProWbsWorkService;
import org.ehais.util.DateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;


public class ParseProjectMpp {
	
	public static void main(String[] args) {
		String file = "E:/LGJ/a广州明动/信息推送/专利推送.mpp";
		
		
		List<Map<String,Object>> list = ParseProjectMpp.psrseProjectFile(file);
		
	}

	public static List<Map<String,Object>> psrseProjectFile(String file){
		File mppfile = new File(file);
        if(!mppfile.exists()){
        	return null;
        }
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        MPPReader reader = new MPPReader();
        ProjectFile projectFile;
        try{
            projectFile = reader.read(mppfile);
            List<Task> taskList = projectFile.getAllTasks();
            Integer parentId = 0;
            for(Task task : taskList){
            	
            	parentId = 0;
                if(task.getParentTask()!=null){
                	parentId = task.getParentTask().getID();
                }
                
                
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("id",task.getID());
                map.put("taskName",task.getName());
                map.put("startDate",DateUtil.formatDate(task.getStart(), DateUtil.FORMATSTR_3));
                map.put("endDate",DateUtil.formatDate(task.getFinish(), DateUtil.FORMATSTR_3));
                map.put("parentId",parentId);
                map.put("level",task.getOutlineLevel());
                map.put("beforeTask",getBeforeTaskId(task));//获取前置任务的Id
                map.put("resource",getResources(task));//获得资源
                
                
//                System.out.println(task.getID()+"   ---    "+level_str(task.getOutlineLevel())+task.getName()+" l: "+task.getOutlineLevel() +" 上级: "+ parentId +" start :" + DateUtil.formatDate(task.getStart(), DateUtil.FORMATSTR_3)  + " finish : "+ DateUtil.formatDate(task.getFinish(), DateUtil.FORMATSTR_3) );
                
                list.add(map);
            }
        }catch(MPXJException e ){
            e.printStackTrace();
        }
        return list;
    }
	
	private static String level_str(int level){
		String str = "";
		for(int i = 0 ; i < level ; i++){
			str+="    ";
		}
		
		return str;
	}

   
    private static String  getBeforeTaskId(Task task){
        StringBuffer beforeTaskId = new StringBuffer();
        if(task!=null){
            List list = task.getPredecessors();
            if(list != null ){
                if(list.size()>0){
                    for(int i=0; i<list.size(); i++){
                        Relation relation = (Relation)list.get(i);
                        beforeTaskId.append(relation.getTargetTask().getID());
                    }
                }
            }
        }
        return beforeTaskId.toString();
    }

   
    private static String getResources(Task task){
        if(task == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        List<ResourceAssignment> assignments = task.getResourceAssignments();
        for(ResourceAssignment ra : assignments){
            Resource resource = ra.getResource();
            if(resource != null){
                sb = sb.append(resource.getName());
            }
        }
        return sb.toString();
    }
    
}
