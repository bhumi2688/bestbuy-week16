package com.bestbuy.serviceinfo;


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
public class ServiceCURDTestWithSteps extends TestBase {
    static String name = "polishing" + TestUtils.getRandomValue();
    static int servicesId;
    @Steps
    ServiceSteps serviceSteps;

    @Title("This will create a new service")
    @Test
    public void test001() {

        ValidatableResponse response = serviceSteps.createServices(name);
        response.statusCode(201);
    }

    @Title("verify if service is created")
    @Test
    public void test002() {
        HashMap<String,Object> servicesMapData =serviceSteps.getservicesinfobyname(name);
        Assert.assertThat(servicesMapData,hasValue(name));
        servicesId = (int) servicesMapData.get("id");
        System.out.println(servicesId);

    }
    @Title("update the information")
    @Test
    public void test003(){
        name = name + "white";

        serviceSteps.updateServices(name,servicesId);
        HashMap<String,?> servicesMapData = serviceSteps.getservicesinfobyname(name);
        Assert.assertThat(servicesMapData,hasValue(name));

    }
    @Title("Delete store info by storeID and verify its deleted")
    @Test
    public void test004(){
        serviceSteps.deleteServicessInfobyId(servicesId).statusCode(200);
        serviceSteps.getservicesinfobyid(servicesId).statusCode(404);

    }

}
