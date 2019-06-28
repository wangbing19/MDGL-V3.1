package com.vision.service.ppo;

import java.util.List;

import com.vision.pojo.ppo.PpoServiceItems;

public interface PpoServiceItemsService {

	int saveServiceItems(PpoServiceItems ppoServiceItems);

	List<PpoServiceItems> findServiceItems(Long organizationId);

}
