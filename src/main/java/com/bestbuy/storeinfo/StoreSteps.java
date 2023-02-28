package com.bestbuy.storeinfo;

import com.bestbuy.constants.CategoriesEndPoint;
import com.bestbuy.constants.StoreEndPoints;
import com.bestbuy.model.CategoriesPojo;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllServicesinfo(){
        return SerenityRest.given()
                .when()
                .get(StoreEndPoints.GET_ALL_STORES)
                .then();
    }
    @Step("Creating store with name :{0},type :{1},address :{2},address2 :{3},city :{4},state :{5},zip :{6},hours :{7}")
            public ValidatableResponse createStores(String name, String type, String address, String address2, String city, String state, String zip, String hours) {
        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress(address);
        pojo.setAddress2(address2);
        pojo.setCity(city);
        pojo.setState(state);
        pojo.setZip(zip);
        pojo.setHours(hours);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(StoreEndPoints.CREATE_NEW_STORES)
                .then().statusCode(201);
    }
    @Step("getting store info by name:{0}")
    public HashMap<String, Object> getStoreinfoByName(String name) {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(StoreEndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }
    @Step("Update stores info with name:{0},id{1}")
    public ValidatableResponse updateStores(int storesId ,String name){
        StorePojo pojo = new StorePojo();
        pojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID",storesId)
                .body(pojo)
                .when()
                .patch(StoreEndPoints.UPDATE_STORES_BY_ID)
                .then();
    }
    @Step("deleteing stores information with id:{1}")
    public ValidatableResponse deleteStoresInfobyId(int storesId){
        return SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .delete(StoreEndPoints.DELETE_STORES_BY_ID)
                .then();
    }
    @Step("getting stores information by id:{1}")
    public ValidatableResponse getstoresinfobyid(int storesId){
        return SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .get(StoreEndPoints.GET_STORES_BY_ID)
                .then();
    }






}
