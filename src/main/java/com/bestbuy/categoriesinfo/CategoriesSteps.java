package com.bestbuy.categoriesinfo;

import com.bestbuy.constants.CategoriesEndPoint;
import com.bestbuy.model.CategoriesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.HashMap;

public class CategoriesSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllCategoriesInfo(){
        return SerenityRest.given()
                .when()
                .get(CategoriesEndPoint.GET_ALL_CATEGORIES)
                .then();
    }
    @Step("creating categories with name:{0},id:{1}")
    public ValidatableResponse createCategories(String name ,String id){
        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(CategoriesEndPoint.CREATE_NEW_CATEGORIES)
                .then().statusCode(201);

    }
    @Step("getting categories info by name:{0},id{1}")
    public HashMap<String,Object> getcategoriesinfobyname(String name){
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoint.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }
    @Step("Update categories info with name:{0},id{1}")
    public ValidatableResponse updateCategories(String categoriesId ,String name){
        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesId)
                .body(pojo)
                .when()
                .patch(CategoriesEndPoint.UPDATE_SINGLE_CATEGORIES_BY_ID)
                .then();
    }
    @Step("deleteing categories information with id:{1}")
    public ValidatableResponse deleteCategoriesInfobyId(String categoriesId){
        return SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .delete(CategoriesEndPoint.DELETE_CATEGORIES_BY_ID)
                .then();

    }
    @Step("getting categories information by id:{1}")
    public ValidatableResponse getcategogiesinfobyid(String categoriesId){
        return SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .get(CategoriesEndPoint.GET_SINGLE_CATEGORIES_ID)
                .then();
    }

}
