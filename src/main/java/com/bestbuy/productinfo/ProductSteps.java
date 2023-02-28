package com.bestbuy.productinfo;

import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.constants.StoreEndPoints;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.model.StorePojo;
import cucumber.api.java.ca.I;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step("getting all information :{0}")
    public ValidatableResponse getAllProductsInfo() {
        return SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then();
    }

    @Step("creating products with name :{0},type :{1},Upc :{2},description :{3},manufacturer :{4},model :{5},url :{6},image :{7}")
    public ValidatableResponse createProducts(String name, String type, String Upc, String description, String manufacturer, String model, String url, String image) {
        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setUpc(Upc);
        pojo.setDescription(description);
        pojo.setManufacturer(manufacturer);
        pojo.setModel(model);
        pojo.setUrl(url);
        pojo.setImage(image);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().statusCode(201);
    }
    @Step("getting Products info by name:{0}")
    public HashMap<String, Object> getProductsinfoByName(String name) {
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
    }
    @Step("Update products info with productsId:{0},name:{1}")
    public ValidatableResponse updateProducts(int productsId ,String name){
        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID",productsId)
                .body(pojo)
                .when()
                .patch(ProductEndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }
    @Step("deleting products information with productsId:{0}")
    public ValidatableResponse deleteProductsInfoById(int productsId){
        return SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .delete(ProductEndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }
    @Step("getting products information by productsId:{0}")
    public ValidatableResponse getProductsInfoById(int productsId){
        return SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCT_ID)
                .then();
    }


    }

