package stepDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.testng.Assert;
import utils.ApiFunctions;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static utils.ApiFunctions.generateSchema;


public class CurrencyApi {
    ApiFunctions commonApiMethods = new ApiFunctions();
    private static final Logger logger = Logger.getLogger(CurrencyApi.class);
    public String response = "";
    public int statusCode;
    public String apiResponseSchema;
    public String schema = "";

    @Given("^User has '(.+)'$")
    public void user_has(String url) {
        // Write code here that turns the phrase above into concrete actions
        response = commonApiMethods.getResponseBody(url);
        logger.info("Response of the body is: " + response);
    }

    @When("^User hit the '(.+)' for currency$")
    public void user_hit_the_for_currency(String url) {
        response = commonApiMethods.getResponseBody(url);
        statusCode = commonApiMethods.getStatusCode(url);

    }

    @Then("^API should return the '(.+)' for currency$")
    public void api_should_return_the_for_currency(int code) {
        assertEquals(statusCode, code);
        logger.info("Status code is: " + statusCode);
    }

    @Then("^API should return the '(.+)' message$")
    public void api_should_return_the_message_for(String status) {
        String result = JsonPath.read(response, "$.result");
        assertEquals(result, status);
        logger.info("Status is: " + result);
    }

    @Then("^API should fetch the USD price against AED with '(.+)'$")
    public void api_should_fetch_the_u_sd_price_against_aed_with(String range) {
        double aed = JsonPath.read(response, "$.rates.AED");
        logger.info("price of AED is: " + aed);
        String[] price = range.split("-");
        double lowerPrice = Double.parseDouble(price[0]);
        double upperPrice = Double.parseDouble(price[1]);
        Assert.assertTrue(aed >= lowerPrice && aed <= upperPrice);
    }

    @Then("^API should return '(.+)' currency pairs$")
    public void api_should_return_currency_pairs(int pairs) {
        List<String> currencyCount = JsonPath.read(response, "$.rates.*");
        int count = currencyCount.size();
        assertEquals(count, pairs);
        logger.info("Number of currency pairs is: " + pairs);
    }

    @Then("response is converted into Json schema")
    public void response_is_converted_into_json_schema() throws ProcessingException, JsonProcessingException {
        try {
            schema = generateSchema(response);
            System.out.println(schema);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @And("^response and Json schema should be match with '(.+)'$")
    public void response_and_json_schema_should_be_match(String url) throws Exception {
        commonApiMethods.validateSchema(url, schema);
    }
}

