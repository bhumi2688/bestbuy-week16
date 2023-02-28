package com.bestbuy.productinfo;


import com.bestbuy.constants.CategoriesEndPoint;
import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.constants.ServiceEndPoints;
import com.bestbuy.model.ProductPojo;
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
public class ProductCURDTest extends TestBase {
    static String name = "Wigily Bateries" + TestUtils.getRandomValue();
    static String type = "Slimcore" + TestUtils.getRandomValue();
    static Object productsId;

    @Title("This will create a new products")
    @Test
            public void test001() {
        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setManufacturer("Woogly");
        pojo.setModel("WC5967WSS");
        pojo.setUpc("0456789");
        pojo.setImage("img/abc");
       // pojo.setPrice(5);
        //pojo.setShipping(0);
        pojo.setDescription("east to fit");
        pojo.setUrl("http//www.curly.com");


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);

    }
    @Title("Verify if product was created")
    @Test
    public void test002(){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?> productsMapData =SerenityRest.given()
                .log().all()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1+name+part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(productsMapData, hasValue(name));
        productsId= productsMapData.get("id");
        System.out.println(productsId);

    }
    @Title("Update the name and verify the update information")
    @Test
    public void test003(){
        name = name + "winky";

        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID",productsId)
                .body(pojo)
                .when()
                .patch(ProductEndPoints.UPDATE_PRODUCT_BY_ID)
                .then().statusCode(200);

        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String, Object> productMapData = SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1 + name + part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(productMapData, hasValue(name));
    }
    @Title("Delete the product and verify if the product is deleted")
    @Test
    public void test004() {

        SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .delete(ProductEndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("productsID", productsId)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCT_ID)
                .then().log().all().statusCode(404);

    }



    }





