package com.vision;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vision.mapper") 
public class ManageMain {

	public static void main(String[] args) {
		SpringApplication.run(ManageMain.class, args);
	}

}
