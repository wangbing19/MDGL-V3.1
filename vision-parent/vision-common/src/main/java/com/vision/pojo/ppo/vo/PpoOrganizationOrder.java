package com.vision.pojo.ppo.vo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.vision.pojo.ppo.PpoAppointmentOrder;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class PpoOrganizationOrder {
	private Long organizationId;
	private String organizationName;
	private String organizationAddress;
}
