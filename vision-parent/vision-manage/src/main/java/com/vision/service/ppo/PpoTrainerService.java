package com.vision.service.ppo;



import java.util.List;

import com.vision.pojo.ppo.PpoAppointmentTime;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.vo.PageObject;

public interface PpoTrainerService {

	int savePpoTrainer(PpoTrainer ppoTrainer);

	PageObject<PpoTrainer> findPpoTrainerAll(String trainerName, Integer pageCurrent, Integer pageSize, Long organizationId);

	int doUpdatePpoTrainer(PpoTrainer ppoTrainer);


	Integer savePpoTrainer(Integer ppoAppointmentId);

	Integer doInsertAppointmentTime(PpoAppointmentTime ppoAppointmentTime);

	List<PpoAppointmentTime> dofindappointmentTime(Long tarinerId);

}
