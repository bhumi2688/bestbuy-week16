package com.bestbuy.productinfo;


import com.bestbuy.categoriesinfo.CategoriesSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Step;
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
public class ProductCURDTestWithSteps extends TestBase {
    static String name = "Wigily Bateries" + TestUtils.getRandomValue();
    static String type = "Slimcore" + TestUtils.getRandomValue();
    static String Upc = "0456789" + TestUtils.getRandomValue();
    static String description = "easy to fit" + TestUtils.getRandomValue();
    static String manufacturer = "Woogly" + TestUtils.getRandomValue();
    static String model = "WC5967WSS" + TestUtils.getRandomValue();
    static String url = "http//www.curly.com" + TestUtils.getRandomValue();
    static String image = "img/abc" + TestUtils.getRandomValue();
    static int productsId;
    @Steps
    ProductSteps productSteps;
    @Title("This will create new products")
    @Test
    public void test001(){
        ValidatableResponse response = productSteps.createProducts(name,type,Upc,description,manufacturer,model,url,image);
        response.statusCode(201);
    }
    @Title("verify if product is created")
    @Test
    public void test002() {
        HashMap<String,Object> productsMapData =productSteps.getProductsinfoByName(name);
        Assert.assertThat(productsMapData,hasValue(name));
        productsId= (int) productsMapData.get("id");
        System.out.println(productsId);

    }
    @Title("update the product information")
    @Test
    public void test003(){
        name = name + "winky";
        productSteps.updateProducts(productsId,name);
        HashMap<String, Object> productsMapData = productSteps.getProductsinfoByName(name);
        Assert.assertThat(productsMapData, hasValue(name));

    }
    @Title("Delete the product by id")
    @Test
    public void test004(){
        productSteps.deleteProductsInfoById(productsId).statusCode(200);
        productSteps.getProductsInfoById(productsId).statusCode(404);
    }






}
