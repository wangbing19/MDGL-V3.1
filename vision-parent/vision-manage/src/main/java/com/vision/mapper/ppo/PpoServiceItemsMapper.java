package com.vision.mapper.ppo;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.ppo.PpoServiceItems;

public interface PpoServiceItemsMapper extends BaseMapper<PpoServiceItems>{

	List<PpoServiceItems> selectByIds(List<Long> organizationIdList);

	int selectCountNum(List<Long> findOrganizationIdList);

}
