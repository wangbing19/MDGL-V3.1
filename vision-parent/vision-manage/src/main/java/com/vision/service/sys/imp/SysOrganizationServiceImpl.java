package com.vision.service.sys.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.vision.exception.ServiceException;
import com.vision.mapper.sys.SysOrganizationMapper;
import com.vision.pojo.sys.SysOrganization;
import com.vision.service.sys.SysOrganizationService;
import com.vision.vo.Node;
import com.vision.vo.Node2;

@Service
public class SysOrganizationServiceImpl implements SysOrganizationService{
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;

	@Override
	public int updateOrganization(SysOrganization sysOrganization) {
		//1.合法验证
				if(sysOrganization==null)
				throw new ServiceException("保存对象不能为空");
				if(StringUtils.isEmpty(sysOrganization.getOrganizationName()))
				throw new ServiceException("组织不能为空");
				int rows;
				//2.更新数据
				try{
					
				rows=sysOrganizationMapper.updateById(sysOrganization);
				}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException("更新失败");
				}
				//3.返回数据
				return rows;
	}

	@Override
	public int deleteOrganization(Long organizationId) {
		//1.合法性验证
				if(organizationId==null||organizationId<=0)
				throw new ServiceException("数据不合法,organizationId="+organizationId);
				//2.执行删除操作
				//2.1判定此id对应的菜单是否有子元素
				int childCount=sysOrganizationMapper.getChildCount(organizationId);
				if(childCount>0) {
					return -1;
					
					
				}
				
				//2.2判定此部门是否有用户
				//int userCount=sysUserDao.getUserCountByDeptId(id);
				//if(userCount>0)
				//throw new ServiceException("此部门有员工，不允许对部门进行删除");
				//2.2判定此部门是否已经被用户使用,假如有则拒绝删除
				//2.3执行删除操作
				int rows=sysOrganizationMapper.deleteById(organizationId);
				if(rows==0)
				throw new ServiceException("此信息可能已经不存在");
				return rows;
		
	}

	@Override
	public int saveOrganization(SysOrganization sysOrganization) {
				//1.合法验证
				if(sysOrganization==null)
				throw new ServiceException("保存对象不能为空");
				if(StringUtils.isEmpty(sysOrganization.getOrganizationName()))
				throw new ServiceException("组织不能为空");
				//2.保存数据
				int rows=sysOrganizationMapper.insert(sysOrganization);
				//if(rows==1)
				//throw new ServiceException("save error");
				//3.返回数据
				return rows;
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<SysOrganization> findOrganization() {
		//List<Map<String, Object>> result = sysOrganizationMapper.findObjects();
		List<SysOrganization> result =sysOrganizationMapper.findObjectsAll();
		return result;
	}

	@Override
	public List<Node2> findZTreeNodes() {
		List<Node2> result = sysOrganizationMapper.findZTreeNodes();
		
		return result;
	}
	/**
	 * 基于id获取组织信息
	 * @param id
	 * @return
	 */
	@Override
	public SysOrganization getSysOrganizationById(Long id) {
		SysOrganization organization = sysOrganizationMapper.selectById(id);
		return organization;
	}
	
	
}
