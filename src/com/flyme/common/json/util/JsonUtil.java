package com.flyme.common.json.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.flyme.app.base.vo.BaseResponse;
import com.flyme.common.constants.InfoConstants;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.model.CheckHas;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.filters.CriteriaQuery;

/**
 * JSON字符串工具类
 */
public class JsonUtil {
	private static ObjectMapper mapper;
	private static JsonGenerator jsonGenerator;

	private static final JsonFactory JSONFACTORY = new JsonFactory();
	private static SimpleFilterProvider filter;

	/**
	 * 获取ObjectMapper实例
	 */
	public static synchronized JsonGenerator getJsonGenerator() {
		try {
			jsonGenerator = getMapperInstance().getFactory().createGenerator(System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonGenerator;
	}

	/**
	 * 获取ObjectMapper实例
	 */
	public static synchronized JsonGenerator getResponseJsonGenerator() {
		try {
			jsonGenerator = getMapperInstance().getFactory().createGenerator(System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonGenerator;
	}

	public static void setFilter(String filterName, String... property) {
		filter = new SimpleFilterProvider().setFailOnUnknownId(false);
		filter.addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(property));
	}

	/**
	 * 获取ObjectMapper实例 Inclusion Inclusion.ALWAYS 全部列入 Inclusion
	 * Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入 Inclusion Inclusion.NON_EMPTY
	 * 字段为NULL或者""的时候不会列入 Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入
	 */
	public static synchronized ObjectMapper getMapperInstance() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
				@Override
				public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
					arg1.writeString("");
				}
			});
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 当找不到对应的序列化器时
																				// 忽略此字段
			SimpleModule module = new SimpleModule();
			module.addSerializer(String.class, new StringUnicodeSerializer());// 使Jackson支持Unicode编码非ASCII字符
			mapper.registerModule(module);
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);// 允许空字符串转换为空对象
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);// 允许空数组转换为空对象
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));// 所有日期格式都统一为以下样式
			mapper.setSerializationInclusion(Include.ALWAYS);// 设置null值不参与序列化(字段不被显示)
			/*mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
				private static final long serialVersionUID = 1L;

				// 反序列化时调用
				@Override
				public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
					return method.getName().substring(3);
				}
			});
			mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
				@Override
				public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
					arg1.writeString("");
				}
			});*/
		}
		return mapper;
	}

	/**
	 * 将java对象转换成json字符串
	 */
	public static String beanToJson(Object obj) {
		ObjectMapper objectMapper = getMapperInstance();
		String json = "";
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeObj(HttpServletResponse response, Object obj) {
		ObjectMapper objectMapper = getMapperInstance();
		ApiJson j = new ApiJson();
		try {
			j.setObject(obj);
			if (filter != null) {
				objectMapper.writeValue(response.getWriter(), j);
			} else {
				objectMapper.writer(filter).writeValue(response.getWriter(), j);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeMap(HttpServletResponse response, CriterionMap map) {
		ApiJson j = new ApiJson();
		j.setObject(map);
		return writeApiJson(response, j);
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeMap(HttpServletResponse response, CriterionMap map, String info) {
		ApiJson j = new ApiJson();
		j.setObject(map);
		j.setAppInfo(info);
		return writeApiJson(response, j);
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeErrorMap(HttpServletResponse response, CriterionMap map, String info) {
		ApiJson j = new ApiJson();
		j.setObject(map);
		j.setAppError(info);
		return writeApiJson(response, j);
	}

	/**
	 * 将json对象写入response
	 */
	public static void writeCq(HttpServletResponse response, List<CriterionMap> map, CriteriaQuery cq) {
		ObjectMapper objectMapper = getMapperInstance();
		PqGrid pqGrid = PqGrid.toPqGrid(map, cq);
		ApiJson j = new ApiJson();
		try {
			j.setObject(pqGrid);
			if (filter != null) {
				objectMapper.writeValue(response.getWriter(), j);
			} else {
				objectMapper.writer(filter).writeValue(response.getWriter(), j);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 将json对象写入response
	 */
	public static void writePqGrid(HttpServletResponse response, PqGrid pqGrid) {
		ObjectMapper objectMapper = getMapperInstance();
		ApiJson j = new ApiJson();
		try {
			j.setObject(pqGrid);
			if (filter != null) {
				objectMapper.writeValue(response.getWriter(), j);
			} else {
				objectMapper.writer(filter).writeValue(response.getWriter(), j);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeError(HttpServletResponse response, String msg) {
		ApiJson j = new ApiJson();
		j.setAppError(msg);
		return writeApiJson(response, j);
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeNoParams(HttpServletResponse response) {
		ApiJson j = new ApiJson();
		j.setAppError(InfoConstants.NO_PARAMS);
		return writeApiJson(response, j);
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeOk(HttpServletResponse response, String info) {
		ApiJson j = new ApiJson();
		j.setAppInfo(info);
		return writeApiJson(response, j);
	}

	/**
	 * 返回添加结果
	 */
	public static ApiJson add(HttpServletResponse response, Integer i) {
		ApiJson j = new ApiJson();
		if (i > 0) {
			j.setAppInfo("添加成功！");
		} else {
			j.setAppError("添加失败");
		}
		return writeApiJson(response, j);
	}

	/**
	 * 返回添加结果
	 */
	public static ApiJson info(HttpServletResponse response, Integer i, String info) {
		ApiJson j = new ApiJson();
		if (i > 0) {
			j.setAppInfo(info + InfoConstants.SUCCESS);
		} else {
			j.setAppError(info + InfoConstants.FAIL);
		}
		return writeApiJson(response, j);
	}

	/**
	 * 返回添加结果
	 */
	public static ApiJson info(HttpServletResponse response, Integer i) {
		ApiJson j = new ApiJson();
		if (i > 0) {
			j.setAppInfo("");
		} else {
			j.setAppError("");
		}
		return writeApiJson(response, j);
	}

	/**
	 * 返回更新结果
	 */
	public static ApiJson update(HttpServletResponse response, Integer i) {
		ApiJson j = new ApiJson();
		if (i > 0) {
			j.setAppInfo("更新成功！");
		} else {
			j.setAppError("更新失败");
		}
		return writeApiJson(response, j);
	}

	public static ApiJson update(HttpServletResponse response, Integer i, Object object) {
		ApiJson j = new ApiJson();
		j.setObject(object);
		if (i.intValue() > 0) {
			j.setAppInfo("更新成功！");
		} else {
			j.setAppError("更新失败");
		}
		return writeApiJson(response, j);
	}

	/**
	 * 返回删除结果
	 */
	public static ApiJson delete(HttpServletResponse response, Integer i) {
		ApiJson j = new ApiJson();
		if (i > 0) {
			j.setAppInfo("删除成功！");
		} else {
			j.setAppError("删除失败");
		}
		return writeApiJson(response, j);
	}

	/**
	 * 返回回复结果
	 */
	public static ApiJson reply(HttpServletResponse response, Integer i) {
		ApiJson j = new ApiJson();
		if (i > 0) {
			j.setAppInfo("回复成功！");
		} else {
			j.setAppError("回复失败");
		}
		return writeApiJson(response, j);
	}

	/**
	 * 返回删除结果
	 */
	public static ApiJson accept(HttpServletResponse response, Integer i) {
		ApiJson j = new ApiJson();
		if (i > 0) {
			j.setAppInfo("审核成功！");
		} else {
			j.setAppError("审核失败");
		}
		return writeApiJson(response, j);
	}

	/**
	 * 将json对象写入response
	 */
	public static ApiJson writeApiJson(HttpServletResponse response, ApiJson j) {
		ObjectMapper objectMapper = getMapperInstance();
		j.setClose(null);
		try {
			if (filter != null) {
				objectMapper.writeValue(response.getWriter(), j);
			} else {
				objectMapper.writer(filter).writeValue(response.getWriter(), j);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 将json字符串转换成java对象
	 */
	public static <T> T jsonToBean(String json, Class<T> cls) {
		ObjectMapper objectMapper = getMapperInstance();
		T object = null;
		try {
			object = objectMapper.readValue(json, cls);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 将json字符串转换成java对象
	 */
	public static <T> T jsonToBean(String json, TypeReference<?> typeReference) {
		ObjectMapper objectMapper = getMapperInstance();
		T object = null;
		try {
			object = objectMapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	public static TypeReference<BaseResponse<?>> getTypeReference(Class<?> cls) {
		return new TypeReference<BaseResponse<?>>() {
			@Override
			public Type getType() {
				return cls;
			}
		};
	}

	@SuppressWarnings("deprecation")
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		ObjectMapper objectMapper = getMapperInstance();
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static <T> List<T> toList(String json, Class<?> class1) {
		JavaType javaType = getCollectionType(ArrayList.class, class1);
		ObjectMapper objectMapper = getMapperInstance();
		List<T> beanList = ObjectUtils.getArrayList();
		try {
			beanList = objectMapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return beanList;

	}

	/**
	 * 转换Json String 为 HashMap
	 */
	@SuppressWarnings("unchecked")
	public static CriterionMap jsonToMap(String json, boolean collToString) {
		try {
			CriterionMap map = getMapperInstance().readValue(json, CriterionMap.class);
			if (collToString) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof Collection || entry.getValue() instanceof Map) {
						entry.setValue(beanToJson(entry.getValue()));
					}
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换Json String 为 HashMap
	 */
	public static CriterionMap jsonToCriterionMap(String json) {
		CriterionMap map = new CriterionMap();
		try {
			map = getMapperInstance().readValue(json, CriterionMap.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	/**
	 * List 转换成json
	 */
	public static String listToJson(HttpServletResponse response, Collection<?> list) {
		JsonGenerator jsonGenerator = null;
		StringWriter sw = new StringWriter();
		try {
			jsonGenerator = getJsonGenerator();
			if (filter != null) {
				getMapperInstance().writer(filter).writeValue(response.getWriter(), list);
				getMapperInstance().writer(filter).writeValue(sw, list);
			} else {
				getMapperInstance().writeValue(response.getWriter(), list);
				getMapperInstance().writeValue(sw, list);
			}
			jsonGenerator.flush();
			return sw.toString();
		} catch (Exception e) {
			return null;
		} finally {
			if (jsonGenerator != null) {
				try {
					jsonGenerator.flush();
					jsonGenerator.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 转换Java Bean 为 HashMap
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object o) {
		try {
			return getMapperInstance().readValue(beanToJson(o), HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json 转List
	 */
	@SuppressWarnings("unchecked")
	public static List<?> jsonToList(String json) {
		ObjectMapper objectMapper = getMapperInstance();
		try {
			if (json != null && !"".equals(json.trim())) {
				JsonParser jsonParse = JSONFACTORY.createParser(new StringReader(json));
				ArrayList<Map<String, String>> arrayList = (ArrayList<Map<String, String>>) objectMapper.readValue(jsonParse, ArrayList.class);
				return arrayList;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		List<CheckHas> checkHasList =JsonUtil.toList("[{\"id\":\"105102\",\"opt\":\"add\"}]", CheckHas.class);
		for (CheckHas checkHas : checkHasList) {
			System.out.println(checkHas.getOpt());
		}
	}

}
