package com.bestbuy.storeinfo;


import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.constants.ServiceEndPoints;
import com.bestbuy.constants.StoreEndPoints;
import com.bestbuy.model.ServicePojo;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class StoreCURDTest extends TestBase {
    static String name = "Pearl" + TestUtils.getRandomValue();
    static  String type ="clothes" + TestUtils.getRandomValue();
    static Object storesId;

    @Title("This will create a new Store")
    @Test
    public void test001() {
        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress("23 tata");
        pojo.setAddress2("20 tutu ");
        pojo.setCity("tweety");
        pojo.setState("TT");
        pojo.setZip("S56789");
      //  pojo.setLat(3);
        // pojo.setLng(5);
        pojo.setHours("Tue: 10-9; Wed: 10-9");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(StoreEndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);

    }
    @Title("Verify if stores was created")
            @Test
            public void test002(){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?> storesMapData =SerenityRest.given()
                .log().all()
                .when()
                .get(StoreEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1+name+part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(storesMapData, hasValue(name));
        storesId= storesMapData.get("id");
        System.out.println(storesId);

    }
    @Title("Update the name and verify the information")
    @Test
    public void test003() {
        name = name + "pearly";

        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID", storesId)
                .body(pojo)
                .when()
                .patch(StoreEndPoints.UPDATE_STORES_BY_ID)
                .then().statusCode(200);

        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String, Object> storesMapData = SerenityRest.given()
                .when()
                .get(StoreEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1 + name + part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(storesMapData, hasValue(name));
    }
    @Title("Delete the stores and verify if the stores has been deleted ")
    @Test
    public void test004(){
            SerenityRest.given()
                    .pathParam("storesID",storesId)
                    .when()
                    .delete(StoreEndPoints.DELETE_STORES_BY_ID)
                    .then().log().all().statusCode(200);

            SerenityRest.given()
                    // .contentType(ContentType.JSON)
                    .pathParam("storesID",storesId)
                    .when()
                    .get(StoreEndPoints.GET_STORES_BY_ID)
                    .then().log().all().statusCode(404);

        }




}
