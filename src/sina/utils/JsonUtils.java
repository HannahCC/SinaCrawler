package sina.utils;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import sina.json.msg.PreLoginResponseMessage;

/**
 * @author zc
 * 解析JSON格式的数据工具类
 */
public class JsonUtils {


	//将预登录时的json字符串转换为对应的bean
	public static PreLoginResponseMessage jsontoPreLoginResponseMessage(String jsondata){
		JSONObject jsonobj=JSONObject.fromObject(jsondata);
		PreLoginResponseMessage message=(PreLoginResponseMessage) JSONObject.toBean(jsonobj, PreLoginResponseMessage.class);
		return message;
	} 

	//把一个bean对象转换为jsonArray字符串
	public static String beantojsonArraystr(Object obj){
		JSONArray jsonarray=JSONArray.fromObject(obj);
		return jsonarray.toString();
	}
	//把一个bean对象转换为json字符串
	public static String beantojson(Object obj){
		JSONObject jsonobj=JSONObject.fromObject(obj);
		return jsonobj.toString();
	}

	//将jsonArray转换成有序的字符串(array的每一个子对象都是JSONObject)
	public static String jsonarraytoString(JSONArray jsonarray){
		JSONStringer js = new JSONStringer();
		try{
			js.array();
			JSONObject[] array = (JSONObject[]) jsonarray.toArray();
			for(JSONObject json : array){
				js.value(jsontoString(json));
			}
			js.endArray();
		}catch (Exception e){
			e.printStackTrace();
		}
		return js.toString();
	}
	//将json转换成有序的字符串
	public static String jsontoString(JSONObject json){
		JSONStringer js = new JSONStringer();
		try{
			js.object();
			@SuppressWarnings("unchecked")
			Set<String> key_set = json.keySet();
			for(String key : key_set){
				js.key(key);
				js.value(json.get(key));
			}
			js.endObject();
		}catch (Exception e){
			e.printStackTrace();
		}
		return js.toString();
	}

}
