package crv.messageBuilt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONData{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String filePath = "src/main/resources/classes.json";

    private static void addNewData(String newKey, String newValue) {
        try {
            JsonNode existingJson = objectMapper.readTree(new File(filePath));
            if (!existingJson.has(newKey)) {
                ((com.fasterxml.jackson.databind.node.ObjectNode) existingJson).put(newKey, newValue);
                objectMapper.writeValue(new File(filePath), existingJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> readJsonData() {
        Map<String, String> dataMap = new LinkedHashMap<>();

        try {
            JsonNode jsonData = objectMapper.readTree(new File(filePath));
            Iterator<Map.Entry<String, JsonNode>> fields = jsonData.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                String value = field.getValue().asText();

                dataMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataMap;
    }

    private static String getValueByKey(JsonNode jsonNode, String key) {
        if (jsonNode.has(key)) {
            return jsonNode.get(key).asText();
        } else {
            throw new IllegalArgumentException("Key '" + key + "' not found in JSON");
        }
    }
}

