package com.vision.pojo.ppo.vo;

import java.util.Date;

import com.vision.pojo.BasePojo;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.ppo.PpoAppointmentOrder;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class PpoOrderCorrlation  extends BasePojo{
	private Long orderId;
	private String serviceItems;
	private Date appointmentTime;
	private String trainerName; 
	private String appointmentRemark;
	private Integer appointmentState;
	private Long cusCustomerId;
	private String cusCustomerName;
	private String cusCustomerTel;
	private String organizationName;
	private String organizationAddress;
	//private PpoOrganizationOrder ppoOrganizationOrder;
}
