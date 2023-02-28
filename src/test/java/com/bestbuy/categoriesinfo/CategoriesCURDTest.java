package com.bestbuy.categoriesinfo;

import com.bestbuy.constants.CategoriesEndPoint;
import com.bestbuy.model.CategoriesPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.ws.Endpoint;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class CategoriesCURDTest extends TestBase {


    static String name = "pearljwels" + TestUtils.getRandomValue();
    static String id = "pear123456" + TestUtils.getRandomValue();
    static Object categoriesId;

    @Title("This will create new categories")
    @Test
    public void test001() {
        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(CategoriesEndPoint.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);

    }

    @Title("Verify if categories was created")
    @Test
    public void test002() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String, ?> categoriesMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoint.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(categoriesMapData, hasValue(name));
        categoriesId = categoriesMapData.get("id");
        System.out.println(categoriesId);

    }

    @Title("Update the name and verify the updated information")
    @Test
    public void test003() {
        // categoriesId=  + "l";
        name = name + "white";

        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID", categoriesId)
                .body(pojo)
                .when()
                .patch(CategoriesEndPoint.UPDATE_SINGLE_CATEGORIES_BY_ID)
                .then().statusCode(200);

        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";

        HashMap<String, Object> categoriesMapData = SerenityRest.given()
                .when()
                .get(CategoriesEndPoint.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1 + name + part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(categoriesMapData, hasValue(name));
        // categoriesId= categoriesMapData.get("id");
        //System.out.println(categoriesId);
    }

    @Title("Delete the categories and verify if the categories is deleted")
    @Test
    public void test004() {

        SerenityRest.given()
                .pathParam("categoriesID", categoriesId)
                .when()
                .delete(CategoriesEndPoint.DELETE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID", categoriesId)
                .when()
                .get(CategoriesEndPoint.GET_SINGLE_CATEGORIES_ID)
                .then().log().all().statusCode(404);

    }

}
