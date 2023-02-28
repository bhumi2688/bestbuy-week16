package com.bestbuy.serviceinfo;

import com.bestbuy.constants.ServiceEndPoints;
import com.bestbuy.model.ServicePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class ServiceCURDTest extends TestBase {
    static String name = "polishing" + TestUtils.getRandomValue();
    static Object servicesId;

    @Title("This will create a new Service")
    @Test
    public void test001(){
        ServicePojo pojo =new ServicePojo();
        pojo.setName(name);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(ServiceEndPoints.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);

    }
    @Title("Verify if services was created")
    @Test
    public void test002(){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?> servicesMapData =SerenityRest.given()
                .log().all()
                .when()
                .get(ServiceEndPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1+name+part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(servicesMapData, hasValue(name));
        servicesId= servicesMapData.get("id");
        System.out.println(servicesId);

    }
    @Title("Update the name and verify the information")
    @Test
    public void test003(){
        name = name + "white";

        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .body(pojo)
                .when()
                .patch(ServiceEndPoints.UPDATE_SERVICES_BY_ID)
                .then().statusCode(200);

        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String, Object> servicesMapData = SerenityRest.given()
                .when()
                .get(ServiceEndPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1 + name + part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(servicesMapData, hasValue(name));
    }
    @Title("Delete the services and verify if the services is deleted")
    @Test
    public void test004(){
        SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .delete(ServiceEndPoints.DELETE_SERVICES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
               // .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .when()
                .get(ServiceEndPoints.GET_SINGLE_SERVICES_ID)
                .then().log().all().statusCode(404);


    }



}
