

package com.hotent.yq.controller.zht;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.page.PageList;
import com.hotent.platform.model.system.SysUser;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;

import com.hotent.yq.model.zht.Rulescore;
import com.hotent.yq.service.zht.RulescoreService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:前置规则评分 控制器类
 */
@Controller
@RequestMapping("/yq/zht/rulescore/")
public class RulescoreController extends BaseController
{
	@Resource
	private RulescoreService rulescoreService;
	
	/**
	 * 添加或更新前置规则评分。
	 * @param request
	 * @param response
	 * @param rulescore 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新前置规则评分")
	public void save(HttpServletRequest request, HttpServletResponse response,Rulescore rulescore) throws Exception
	{
		String resultMsg=null;		
		try{
			if(rulescore.getId()==null){
				rulescoreService.save(rulescore);
				resultMsg=getText("添加","前置规则评分");
			}else{
			    rulescoreService.save(rulescore);
				resultMsg=getText("更新","前置规则评分");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得前置规则评分分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看前置规则评分分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Rulescore> list=rulescoreService.getAll(new QueryFilter(request,"rulescoreItem"));
		ModelAndView mv=this.getAutoView().addObject("rulescoreList",list);
		return mv;
	}
	
	/**
	 * 删除前置规则评分
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除前置规则评分")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			rulescoreService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除前置规则评分成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑前置规则评分
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑前置规则评分")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		Rulescore rulescore=rulescoreService.getById(id);
		
		return getAutoView().addObject("rulescore",rulescore)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得前置规则评分明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看前置规则评分明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Rulescore rulescore=rulescoreService.getById(id);
		return getAutoView().addObject("rulescore", rulescore);
	}
	
}