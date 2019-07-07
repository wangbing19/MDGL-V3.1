package com.vision.controller.res;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.res.ResSymptomType;
import com.vision.pojo.sys.SysUser;
import com.vision.service.res.ResSymptomTypeSvervise;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;



@Controller
@RequestMapping("/symptomType")
public class ResSymptomTypeContorller {

	@Autowired
	private ResSymptomTypeSvervise resSymptomTypeSvervise;

	/**
	 * 分页查询资源配置
	 * @return
	 */
	@RequestMapping("/getSymptomTypeList")
	@ResponseBody
	public JsonResult getSymptomTypeList(CusVo cusVo) {
		//1.数据合法性验证
		if(cusVo.getPageCurrent()==null||cusVo.getPageCurrent()<0)
			return JsonResult.build(201, "页码值不正确");
		if(cusVo.getOrgId()==null||cusVo.getOrgId()<0)
			return JsonResult.build(201, "门店信息不正确");
		if(cusVo.getPageSize()==null||cusVo.getPageSize()<0)
			return JsonResult.build(201, "页码大小不正确");
				
		try {
			PageObject<ResSymptomType> pageObject = resSymptomTypeSvervise.getSymptomTypeList(cusVo);
			return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "查询失败");
	}
	
	/**
	 * 根据id查询资源配置信息
	 * 
	 */
	@RequestMapping("/getSymptomTypeById")
	@ResponseBody
	public JsonResult getSymptomTypeById(Integer id, Integer orgId) {
		try {
			if(id==null||id<0)
				return JsonResult.build(201, "id错误");
			if(orgId==null||orgId<0)
				return JsonResult.build(201, "orgId错误");
			ResSymptomType result = resSymptomTypeSvervise.getSymptomTypeById(id, orgId);
			if(result !=null ) {
				return JsonResult.oK(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "该数据不存在");
	}
	
	/**
	 * 修改资源配置信息
	 */
	@RequestMapping("/updateSymptomType")
	@ResponseBody
	public JsonResult updateSymptomType(ResSymptomType resSymptomType) {
		try {
			if(resSymptomType==null)
				return JsonResult.build(201, "对象不能为空");
			if(resSymptomType.getId()==null||resSymptomType.getId()<0)
				return JsonResult.build(201, "id不能为空");
			if(resSymptomType.getOrgId()==null||resSymptomType.getOrgId()<0)
				return JsonResult.build(201, "门店信息不能为空");
			if(StringUtils.isEmpty(resSymptomType.getTitle()))
				return JsonResult.build(201, "服务名称不能为空");
			
//			SysUser user = null;
//			if(user.getOrganizationId()!=resSymptomType.getOrgId().longValue()) {
//				return JsonResult.build(201, "该账号无法修改该诊断表，请联系相关门店更改");
//			}
//			resSymptomType.setModifiedUser(user.getUserName());
			
			Integer result = resSymptomTypeSvervise.updateSymptomType(resSymptomType);
			if(result !=null && result !=0) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "修改失败");
	}
	/**
	 * 新增资源配置信息
	 * @return
	 */
	@RequestMapping("/addSymptomType")
	@ResponseBody
	public JsonResult addSymptomType(ResSymptomType resSymptomType) {
		try {
			if(resSymptomType==null)
				return JsonResult.build(201, "对象不能为空");
			if(resSymptomType.getOrgId()==null||resSymptomType.getOrgId()<0)
				return JsonResult.build(201, "门店信息不能为空");
			if(StringUtils.isEmpty(resSymptomType.getTitle()))
				return JsonResult.build(201, "服务名称不能为空");
			
//			SysUser user = null;
//			resSymptomType.setCreatedUser(user.getUserName());
//			resSymptomType.setModifiedUser(user.getUserName());
			
			Integer result=resSymptomTypeSvervise.addSymptomType(resSymptomType);
			if(result!=null && result!=0) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "新增失败");
	}
	
	/**
	 * 根据id删除资源配置信息
	 * @param resSymptomType
	 * @return
	 */
	@RequestMapping("/deleteSymptomType")
	@ResponseBody
	public JsonResult deleteSymptomType(Integer id, Integer orgId) {
		try {
			if(id==null||id<0)
				return JsonResult.build(201, "id不能为空");
			if(orgId==null||orgId<0)
				return JsonResult.build(201, "门店信息不能为空");
			
//			SysUser user = null;
//			if(user.getOrganizationId()!=orgId.longValue()) {
//				return JsonResult.build(201, "该账号无法删除该诊断表，请联系相关门店更改");
//			}
			
			Integer row=resSymptomTypeSvervise.deleteSymptomType(id, orgId);
			if(row !=null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "数据或已删除");
	}
	
	/**
	 * 查询门店下所有资源配置
	 * @param resSymptomType
	 * @return
	 */
	@RequestMapping("/getSymptomTypeListByOrgId")
	@ResponseBody
	public JsonResult getSymptomTypeListByOrgId( Integer orgId) {
		try {
			if(orgId==null||orgId<0)
				return JsonResult.build(201, "门店信息不能为空");
			List<ResSymptomType> symptomTypeList = resSymptomTypeSvervise.getSymptomTypeListByOrgId(orgId);
			if(symptomTypeList.size()==0) {
				return JsonResult.build(202, "该门店无训练配置，需添加资源配置");
			}
			return JsonResult.oK(symptomTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "数据或已删除");
	}
	
}


