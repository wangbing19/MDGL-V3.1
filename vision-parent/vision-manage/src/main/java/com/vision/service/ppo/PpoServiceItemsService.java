package com.vision.service.ppo;

import java.util.List;

import com.vision.pojo.ppo.PpoServiceItems;
import com.vision.vo.PageObject;

public interface PpoServiceItemsService {

	int saveServiceItems(PpoServiceItems ppoServiceItems);

	PageObject<PpoServiceItems> findServiceItems(Long organizationId,Integer pageCurrent,Integer pageSize);

	int deleteServiceItems(Long rederId);

	int updeteServiceItems(PpoServiceItems ppoServiceItems);

	PpoServiceItems findServiceItemOne(PpoServiceItems ppoServiceItems);



}
