package com.vision.service.ppo;



import java.util.List;

import com.vision.pojo.ppo.PpoAppointmentOrder;
import com.vision.pojo.ppo.PpoAppointmentTime;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.vo.PageObject;

public interface PpoAppointmentOrderService {

	PageObject<PpoAppointmentOrder> findPpoOderAll(PpoAppointmentOrder ppoAppointmentOrder);
	PageObject<PpoAppointmentOrder> findPpoOderAll(PpoAppointmentOrder ppoAppointmentOrder, Integer pageCurrent,
			Integer pageSize);

	int savePpoOrder(PpoAppointmentOrder ppoAppointmentOrder);

	int deletePpoOrder(Long orderId);

	int updatePpoOrder(PpoAppointmentOrder ppoAppointmentOrder);

	
	
	
}
