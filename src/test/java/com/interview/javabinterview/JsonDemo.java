package com.interview.javabinterview;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.junit.Test;

import java.util.Map;

/**
 * Description:
 *
 * @author Ju Baoquan
 * Created at  2020/9/25
 */
public class JsonDemo {

    @Test
    public void test_1() {
        String viewConfig = "{\n" +
                "\t\"studentAge\": \"$.student.age\",\n" +
                "\t\"studentName\": \"$.student.name\",\n" +
                "\t\"message\": {\n" +
                "\t\t\"messageDetail\": \"$.message\",\n" +
                "\t\t\"messageType\": 1000\n" +
                "\t}\n" +
                "}";

        String sourceData = "{\n" +
                "\t\"student\": {\n" +
                "\t\t\"name\": \"jubaoquan\",\n" +
                "\t\t\"age\": 10\n" +
                "\t},\n" +
                "\t\"message\": \"test\"\n" +
                "}";

        JSONObject jsonObject = processData(viewConfig, sourceData);
        System.out.println(jsonObject.toJSONString());
    }

    private JSONObject processData(String viewConfigStr, String sourceDataStr) {
        JSONObject sourceData = JSONObject.parseObject(sourceDataStr);
        JSONObject viewConfig = JSONObject.parseObject(viewConfigStr);
        converterToView(viewConfig, sourceData);
        return viewConfig;
    }

    private void converterToView(JSONObject viewConfig, JSONObject sourceData) {
        for (Map.Entry<String, Object> entry : viewConfig.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                viewConfig.put(entry.getKey(), read(value, sourceData));
            } else if (value instanceof JSONObject) {
                converterToView((JSONObject) value, sourceData);
            }
        }
    }

    private Object read(Object jsonPath, Object sourceData) {
        String value = (String) jsonPath;
        if (value.contains("$")) {
            return JSONPath.compile(value).eval(sourceData);
        } else {
            return jsonPath;
        }
    }
}
