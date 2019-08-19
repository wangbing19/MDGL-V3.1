package com.vision.service.ppo;



import java.util.ArrayList;
import java.util.List;

import com.vision.pojo.ppo.PpoAppointmentTime;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.pojo.ppo.vo.PpoTrainerOrganization;
import com.vision.vo.PageObject;

public interface PpoTrainerService {

	int savePpoTrainer(PpoTrainer ppoTrainer);

	PageObject<PpoTrainer> findPpoTrainerAll(String trainerName, Integer pageCurrent, Integer pageSize, Long organizationId);

	int doUpdatePpoTrainer(PpoTrainer ppoTrainer);


	Integer savePpoTrainer(Integer ppoAppointmentId);

	Integer doInsertAppointmentTime(PpoAppointmentTime ppoAppointmentTime);

	List<PpoAppointmentTime> dofindappointmentTime(Long tarinerId);

	int dodeleteAppointmentTime(Long id);

	int updateAppointmentTime(PpoAppointmentTime ppoAppointmentTime);

	Integer dodeletePpoTrainer(List<Long> list);

	PpoTrainerOrganization findPpoTrainerOne(PpoTrainer ppoTrainer);

}
