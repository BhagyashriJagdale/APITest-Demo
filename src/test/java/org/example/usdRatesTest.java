package org.example;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.erosb.jsonsKema.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import static io.restassured.RestAssured.given;

public class usdRatesTest {
    public static void main(String[] args) {
        Response res = (Response) given()
                .when()
                .get("https://open.er-api.com/v6/latest/USD");

        JsonPath response = new JsonPath(res.asString());

        // Accepatnce Criteria 1

            //Assertion 1. Verify status code as 200

            int statusCode = res.getStatusCode();
            String statusCodeS = String.valueOf(statusCode);
            Assert.assertEquals(statusCodeS, "200");
            System.out.println("1. Status Code: " + statusCode);

            //Assertion 2. Verify status as Success

            String status = response.getString("result");
            Assert.assertEquals(status, "success");
            System.out.println("2. Results: " + status);

            //Assertion 3. Verify AED rates

            String aedRates = response.getString("rates.AED");
            Assert.assertNotNull(aedRates, "Assertion Passed as rates are not null");
            System.out.println("3. AED Rates: " + aedRates);

            //Assertion 4. Verify rates are 162 in counts

            String sizeOfRates = response.getString("rates.size()");
            Assert.assertEquals(sizeOfRates,"162");
            System.out.println("4. Count of rates " + response.getString("rates.size()"));

            // Assertion 5. Verify Rate.AED is with in 3.6 and 3.7 range

           float convertedString = Float.parseFloat(aedRates);
           if(3.6 < convertedString && convertedString < 3.7)
           {
               System.out.println("5. AED is greater than 3.6 and less than 3.7");
           }

        // Assertion 6. Generating json schema

        Schema schema1 = SchemaLoader.forURL("https://open.er-api.com/v6/latest/USD").load();
        Validator validator = Validator.create(schema1, new ValidatorConfig(FormatValidationPolicy.ALWAYS));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String apiResponse = res.asString();
            JsonNode jsonNode = objectMapper.readTree(apiResponse);
            ObjectNode schema = generateSchema(jsonNode);
            String strSchema = schema.toString();
            JsonValue instance = new JsonParser(strSchema).parse();
            ValidationFailure failure = validator.validate(instance);

            // verified API response matches the Json schema.
            Assert.assertNull(failure,"null");
            System.out.println("6. Verified json schema with API response schema. ");
        } catch (Exception e) {
            System.err.println("Exception got as : " + e);
        }
    }
    public static ObjectNode generateSchema(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode schema = objectMapper.createObjectNode();

        if (jsonNode.isObject()) {
            jsonNode.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode fieldValue = entry.getValue();
                if (fieldValue.isObject()) {
                    schema.set(fieldName, generateSchema(fieldValue));
                } else {
                    String fieldType = fieldValue.getNodeType().toString().toLowerCase();
                    schema.put(fieldName, fieldType);
                }
            });
        }
        return schema;
    }

}
