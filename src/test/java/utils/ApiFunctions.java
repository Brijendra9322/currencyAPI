package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.Iterator;

import static io.restassured.RestAssured.given;

public class ApiFunctions {

    private static final Logger logger = Logger.getLogger(ApiFunctions.class);

    static {
        RestAssured.baseURI = "https://open.er-api.com";
    }

    public String getResponseBody(String endPoint) {
        Response response = given().when().get(endPoint);
        return response.getBody().asString();
    }

    public void validateSchema(String endPoint, String schema) {
        given().when().get(endPoint).then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }

    public int getStatusCode(String endPoint) {
        Response response = given().when().get(endPoint);
        return response.getStatusCode();
    }

    public String postRequestAndGetResponseBody(String url, String requestBody) {
        Response response = given().body(requestBody).when().post(url);
        return response.getBody().asString();
    }

    private String putRequestAndGetResponseBody(String endpointUrl, String requestBody) {
        Response response = given().body(requestBody).when().put(endpointUrl);
        return response.getBody().asString();
    }

    private String patchRequestAndGetResponseBody(String endpointUrl, String requestBody) {
        Response response = given().body(requestBody).when().patch(endpointUrl);
        return response.getBody().asString();
    }

    private String deleteRequestAndGetResponseBody(String endpointUrl) {
        Response response = given().when().delete(endpointUrl);
        return response.getBody().asString();
    }

    public static String generateSchema(String jsonResponse) throws Exception {
        // Parse the JSON response string into a JSON object
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(jsonResponse);

        // Create an empty JSON object for the schema
        ObjectNode schemaNode = objectMapper.createObjectNode();

        // Set the schema type to "object"
        schemaNode.put("type", "object");
        // Create an empty JSON object for the properties
        ObjectNode propertiesNode = objectMapper.createObjectNode();
        // Iterate over the properties of the response JSON
        responseJson.fields().forEachRemaining(entry -> {
            String propertyName = entry.getKey();
            JsonNode propertyValue = entry.getValue();
            String propertyType = getPropertyType(propertyValue);
            propertiesNode.set(propertyName, getPropertySchema(propertyValue, propertyType));
        });

        schemaNode.set("properties", propertiesNode);

        return schemaNode.toString();
    }

    private static JsonNode generatePropertySchema(JsonNode propertyValue, ObjectMapper objectMapper) {
        if (propertyValue.isObject()) {
            // If the property value is an object, recursively generate its schema
            ObjectNode objectSchema = objectMapper.createObjectNode();
            ObjectNode propertiesNode = objectMapper.createObjectNode();
            propertyValue.fields().forEachRemaining(entry -> {
                String nestedPropertyName = entry.getKey();
                JsonNode nestedPropertyValue = entry.getValue();
                JsonNode nestedPropertySchema = generatePropertySchema(nestedPropertyValue, objectMapper);
                propertiesNode.set(nestedPropertyName, nestedPropertySchema);
            });
            objectSchema.set("properties", propertiesNode);
            objectSchema.put("type", "object");
            return objectSchema;
        } else if (propertyValue.isArray()) {
            // If the property value is an array, recursively generate its schema
            ArrayNode arraySchema = objectMapper.createArrayNode();
            if (!propertyValue.isEmpty()) {
                JsonNode firstElement = propertyValue.get(0);
                JsonNode elementSchema = generatePropertySchema(firstElement, objectMapper);
                arraySchema.add(elementSchema);
            }
            return arraySchema;
        } else {
            // For other types, determine the property type
            String propertyType = getPropertyType(propertyValue);
            return objectMapper.createObjectNode().put("type", propertyType);
        }
    }

    private static ObjectNode getPropertySchema(JsonNode propertyValue, String propertyType) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode propertySchema = objectMapper.createObjectNode();
        propertySchema.put("type", propertyType);

        if (propertyType.equals("object")) {
            ObjectNode nestedProperties = objectMapper.createObjectNode();
            propertyValue.fields().forEachRemaining(entry -> {
                String nestedPropertyName = entry.getKey();
                JsonNode nestedPropertyValue = entry.getValue();
                String nestedPropertyType = getPropertyType(nestedPropertyValue);
                nestedProperties.set(nestedPropertyName, getPropertySchema(nestedPropertyValue, nestedPropertyType));
            });
            propertySchema.set("properties", nestedProperties);
        } else if (propertyType.equals("array")) {
            // Assuming array elements are homogeneous
            JsonNode arrayElement = propertyValue.get(0);
            String arrayElementType = getPropertyType(arrayElement);
            ObjectNode arrayItemSchema = objectMapper.createObjectNode();
            arrayItemSchema.put("type", arrayElementType);
            propertySchema.set("items", arrayItemSchema);
        }

        return propertySchema;
    }

    private static String getPropertyType(JsonNode propertyValue) {
        if (propertyValue.isInt() || propertyValue.isLong()) {
            return "integer";
        } else if (propertyValue.isDouble() || propertyValue.isFloat()) {
            return "number";
        } else if (propertyValue.isBoolean()) {
            return "boolean";
        } else if (propertyValue.isObject()) {
            return "object";
        } else if (propertyValue.isArray()) {
            return "array";
        } else {
            return "string";
        }

    }

    public static boolean compareSchemas(String expectedSchema, String actualSchema) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode expectedJson = objectMapper.readTree(expectedSchema);
            JsonNode actualJson = objectMapper.readTree(actualSchema);
            return compareJsonNodes(expectedJson, actualJson);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean compareJsonNodes(JsonNode expectedNode, JsonNode actualNode) {
        // Compare the type of nodes
        if (!expectedNode.getNodeType().equals(actualNode.getNodeType())) {
            return false;
        }

        // Compare the structure and content of objects and arrays
        if (expectedNode.isObject()) {
            if (!actualNode.isObject()) {
                return false;
            }
            for (Iterator<String> it = expectedNode.fieldNames(); it.hasNext(); ) {
                String fieldName = it.next();
                if (!actualNode.has(fieldName)) {
                    return false;
                }
                if (!compareJsonNodes(expectedNode.get(fieldName), actualNode.get(fieldName))) {
                    return false;
                }
            }
            return true;
        } else if (expectedNode.isArray()) {
            if (!actualNode.isArray()) {
                return false;
            }
            if (expectedNode.size() != actualNode.size()) {
                return false;
            }
            for (int i = 0; i < expectedNode.size(); i++) {
                if (!compareJsonNodes(expectedNode.get(i), actualNode.get(i))) {
                    return false;
                }
            }
            return true;
        }

        // For other node types, compare the content
        return expectedNode.equals(actualNode);
    }
}
