package com.vision.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.vision.annotation.RequiresLog;
import com.vision.mapper.sys.SysLogsMapper;
import com.vision.pojo.sys.SysLogs;

public class SysLogAspect {
	@Autowired
	private SysLogsMapper sysLogsMapper;
	
	 /**
     * 1)@Pointcut 注解用于定义切入点
     * 2)bean(...) 为一种切入点表达式(值为bean的id)
     */
	  //@Pointcut("bean(*ServiceImpl)") //粗粒度切入点表达式
	  //@Pointcut("bean(sysUserServiceImpl)")
	  //细粒度切入点表达式(可以精确到方法)
	  @Pointcut("@annotation(com.db.common.annotation.RequiresLog)")
	  public void logPointCut(){}//空方法(相当于为切入点起了个别名)
	  
	  /**
	   * 说明:
	   * 1)@Around 描述方法为一个环绕通知
	   * @param jp 为一个连接点(切入点中的某个方法信息对象)
	   * @return 目标方法的执行结果
	   * @throws Throwable
	   */ 
	  
	  @Around("logPointCut()")
	  public Object around(ProceedingJoinPoint jp)
			  throws Throwable{
		  long start=System.currentTimeMillis();
		  Object result=jp.proceed();//执行目标方法
		  long end=System.currentTimeMillis();
		  //System.out.println("execute time :"+(end-start));
		  saveObject(jp,end-start);
		  return result;
	  } 
	  
	  private void saveObject(ProceedingJoinPoint jp,
			  long totalTime) throws NoSuchMethodException, SecurityException{
		  //1.获取日志信息
		//  String username=ShiroUtils.getUser().getUsername();
		  String ip=IPUtils.getIpAddr();
		  //获取方法签名信息(包含了方法名以及参数列表信息)
		  Signature s=jp.getSignature();
		  //System.out.println(s.getClass().getName());
		  MethodSignature ms=(MethodSignature)s;
		  //获取目标对象的类对象(字节码对象：反射的起点)
		  Class<?> targetCls=jp.getTarget().getClass();
		  //获取目标方法对象
		  Method targetMethod=targetCls.getDeclaredMethod(
		  ms.getName(),ms.getParameterTypes());
		  //获取目标方法上的RequiresLog注解
		  RequiresLog requiresLog=
		  targetMethod.getDeclaredAnnotation(RequiresLog.class);
		  //获取注解中value属性的值
		  String operation=requiresLog.value();
		  String method=targetCls.getName()+"."+
		                targetMethod.getName();
		  String params=Arrays.toString(jp.getArgs());
		  //2.封装日志信息(封装到SysLog对象)
		  SysLogs log=new SysLogs();
		 // log.setUsername(username);加用户信息
		  log.setIp(ip);
		  log.setOperation(operation);
		  log.setMethod(method);
		  log.setParams(params);
		  log.setTime(totalTime);
		  log.setCreatedTime(new Date());
		  //3.存储日志信息(写入到数据库)
		 
		  sysLogsMapper.insert(log);
	  }
}
