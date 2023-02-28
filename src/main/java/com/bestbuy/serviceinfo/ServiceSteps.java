package com.bestbuy.serviceinfo;

import com.bestbuy.constants.CategoriesEndPoint;
import com.bestbuy.constants.ServiceEndPoints;
import com.bestbuy.model.CategoriesPojo;
import com.bestbuy.model.ServicePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ServiceSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllServicesInfo(){
        return SerenityRest.given()
                .when()
                .get(ServiceEndPoints.GET_ALL_SERVICES)
                .then();
    }
    @Step("creating services with name:{0}")
    public ValidatableResponse createServices(String name){
        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(ServiceEndPoints.CREATE_NEW_SERVICES)
                .then().statusCode(201);
    }
    @Step("getting services info by name:{0}")
    public HashMap<String,Object> getservicesinfobyname(String name){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(ServiceEndPoints.GET_ALL_SERVICES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }
    @Step("Update services info with servicesId:{0},name:{1}")
    public ValidatableResponse updateServices(String name,int servicesId){
        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .body(pojo)
                .when()
                .patch(ServiceEndPoints.UPDATE_SERVICES_BY_ID)
                .then();
    }
    @Step("deleteing services information with servicesId:{0}")
    public ValidatableResponse deleteServicessInfobyId(int servicesId){
        return SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .delete(ServiceEndPoints.DELETE_SERVICES_BY_ID)
                .then();
    }
    @Step("getting services information by servicesId:{0}")
    public ValidatableResponse getservicesinfobyid(int servicesId){
        return SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .get(ServiceEndPoints.GET_SINGLE_SERVICES_ID)
                .then();
    }

}
