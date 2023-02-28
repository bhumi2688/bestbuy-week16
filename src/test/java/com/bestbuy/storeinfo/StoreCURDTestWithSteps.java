package com.bestbuy.storeinfo;

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
public class StoreCURDTestWithSteps extends TestBase {
    static String name = "Pearls" + TestUtils.getRandomValue();
    static  String type ="cloth" + TestUtils.getRandomValue();
    static String address = "230 tata" + TestUtils.getRandomValue();
    static String address2 = "200 tutu" + TestUtils.getRandomValue();
    static String city = "tweet" + TestUtils.getRandomValue();
    static String state = "TS" + TestUtils.getRandomValue();
    static String zip = "S567890" + TestUtils.getRandomValue();
    static String hours = "Tue: 10-9; Wed: 10-9" + TestUtils.getRandomValue();

    static int storesId;
    @Steps
    StoreSteps storeSteps;

    @Title("This will create new store")
    @Test
    public void test001(){
        ValidatableResponse response =storeSteps.createStores(name,type,address,address2,city,state,zip,hours);
        response.statusCode(201);
    }
    @Title("verify if store is created")
    @Test
    public void test002() {
        HashMap<String,Object> storesMapData =storeSteps.getStoreinfoByName(name);
        Assert.assertThat(storesMapData,hasValue(name));
        storesId= (int) storesMapData.get("id");
        System.out.println(storesId);

    }
    @Title("Update the stores information")
    @Test
    public void test003(){
        name = name + "pearly";

        storeSteps.updateStores(storesId,name);
        HashMap<String,Object> storesMapData=storeSteps.getStoreinfoByName(name);
        Assert.assertThat(storesMapData,hasValue(name));


    }
    @Title("Delete stores info by storesID and verify its deleted")
    @Test
    public void test004() {

        storeSteps.deleteStoresInfobyId(storesId).statusCode(200);
        storeSteps.getstoresinfobyid(storesId).statusCode(404);

    }






}
