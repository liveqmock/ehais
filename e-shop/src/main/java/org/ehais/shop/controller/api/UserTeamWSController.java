package org.ehais.shop.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.shop.mapper.HaiUsersRelationMapper;
import org.ehais.shop.model.HaiUserDistributionTeam;
import org.ehais.shop.model.HaiUsersRelation;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ws")
public class UserTeamWSController extends CommonController{

	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiUsersRelationMapper haiUsersRelationMapper;
	private String defaultimg = ResourceUtil.getProValue("defaultimg");
	
	@ResponseBody
	@RequestMapping("/team_list")
	public String team_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition) {
		
		ReturnObject<HaiUserDistributionTeam> rm = new ReturnObject<HaiUserDistributionTeam>();
		rm.setCode(0);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		List<HaiUserDistributionTeam> teamList = new ArrayList<HaiUserDistributionTeam>();
		try{
			
			//查看跟自己有关的用户
			List<HaiUsersRelation> list = haiUsersRelationMapper.selectUsersRelation(user_id, condition.getStart(), condition.getRows());
			List<Long> userList = new ArrayList<Long>();
			for (HaiUsersRelation haiUsersRelation : list) {
				userList.add(haiUsersRelation.getUserId());
				HaiUserDistributionTeam team = new HaiUserDistributionTeam();
				team.byUsersRelation(haiUsersRelation);
				teamList.add(team);
			}
			if(userList.size() > 0){
				EHaiUsersExample exp = new EHaiUsersExample();
				exp.createCriteria().andUserIdIn(userList);
				
				List<EHaiUsers> user_list = eHaiUsersMapper.selectByExample(exp);
				for (EHaiUsers eHaiUsers : user_list) {
					for (HaiUserDistributionTeam team : teamList) {
						if(team.getUserId().longValue() == eHaiUsers.getUserId().longValue()){
							team.setUserName(eHaiUsers.getUserName());
							team.setNickname(eHaiUsers.getNickname());
							team.setFaceImage(eHaiUsers.getFaceImage());
							team.setRegTime(eHaiUsers.getRegTime());
						}
					}
				}
			}
			
			long total = haiUsersRelationMapper.countUsersRelation(user_id);
			rm.setRows(teamList);
			rm.setTotal(total);
			rm.setAction(defaultimg);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	
}
