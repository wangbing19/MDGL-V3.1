package com.vision.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResult implements Serializable {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    /*
     * 200	成功
     * 201	错误
     * 400	参数错误
     */
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static JsonResult build(Integer status, String msg, Object data) {
        return new JsonResult(status, msg, data);
    }

    public static JsonResult oK(Object data) {
        return new JsonResult(data);
    }
    
    public static JsonResult oK(String msg) {
    	return new JsonResult(msg);
    }


    public static JsonResult oK() {
        return new JsonResult(null);
    }

    public static JsonResult error(){
        return JsonResult.build(201,"error","服务器异常");
    }

    public static JsonResult error(Object data){
        return JsonResult.build(201,"error","服务器异常");
    }

    public JsonResult() {

    }

    public static JsonResult build(Integer status, String msg) {
        return new JsonResult(status, msg, null);
    }

    public JsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Object data) {
        this.status = 200;
        this.msg = "操作成功";
        this.data = data;
    }
    
    public JsonResult(String msg) {
    	this.status = 200;
        this.msg = msg;
        this.data = null;
    }


    public Boolean isOk() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为SysResult对象
     *
     * @param jsonData json数据
     * @param clazz SysResult中的object类型
     * @return
     */
    public static JsonResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JsonResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static JsonResult format(String json) {
        try {
            return MAPPER.readValue(json, JsonResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static JsonResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public String toString() {
		return "JsonResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}


}
