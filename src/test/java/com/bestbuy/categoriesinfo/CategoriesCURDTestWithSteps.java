package com.bestbuy.categoriesinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class CategoriesCURDTestWithSteps extends TestBase {
    static String name = "pearljwels" + TestUtils.getRandomValue();
    static String id = "pear123456" + TestUtils.getRandomValue();
    static String categoriesId;
    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a new category")
    @Test
    public void test001() {

        ValidatableResponse response = categoriesSteps.createCategories(name, id);
        response.statusCode(201);
    }

    @Title("verify if categories is created")
    @Test
    public void test002() {
        HashMap<String,Object> categoriesMapData =categoriesSteps.getcategoriesinfobyname(name);
        Assert.assertThat(categoriesMapData,hasValue(name));
        categoriesId= (String) categoriesMapData.get("id");
        System.out.println(categoriesId);

    }
    @Title("update the user information")
    @Test
    public void test003(){
        name = name + "white";
        categoriesSteps.updateCategories(name,id);
        HashMap<String,Object> categoriesMapData = categoriesSteps.getcategoriesinfobyname(name);
        Assert.assertThat(categoriesMapData,hasValue(name));

    }
    @Title("Delete categories info by CategoriesID and verify its deleted")
    @Test
    public void test004(){
        categoriesSteps.deleteCategoriesInfobyId(categoriesId).statusCode(200);
        categoriesSteps.getcategogiesinfobyid(categoriesId).statusCode(404);


    }




}
