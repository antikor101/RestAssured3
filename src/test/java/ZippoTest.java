import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {


    @Test
    public void test() {
        given()


                .when()


                .then()


        ;
    }

    @Test
    public void statusCodeTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)


        ;
    }

    @Test
    public void contentTypeTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)


        ;
    }

    @Test
    public void checkStateInResponseBody() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country", equalTo("United States"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyJsonPathTest2() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyJsonPathTest3() {
        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'", hasItem("Çaputçu Köyü"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyArrayHasSizeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)
        ;
    }

    @Test
    public void combiningTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest() {
        given()
                .pathParam("Country", "us")
                .pathParam("ZipCode", "90210")
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipCode}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest2() {
        for (int i = 90210; i < 90214; i++) {
            given()
                    .pathParam("Country", "us")
                    .pathParam("ZipCode", i)
                    .log().uri()

                    .when()
                    .get("http://api.zippopotam.us/{Country}/{ZipCode}")

                    .then()
                    .log().body()
                    .body("places", hasSize(1))
                    .statusCode(200)
            ;
        }
    }

    @Test
    public void queryParamTest() {
        //https://gorest.co.in/public/v2/users?page=1
        given()
                .param("page", 1)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
                .statusCode(200)
        ;
    }

    @Test
    public void queryParamTest2() {
        //https://gorest.co.in/public/v2/users?page=1
        for (int pageNo = 1; pageNo <= 10; pageNo++) {

            given()
                    .param("page", pageNo)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(pageNo))
                    .statusCode(200)
            ;
        }
    }

    RequestSpecification requestSpecs;
    ResponseSpecification responseSpecs;

    @BeforeClass
    void setuo() {
        baseURI = "https://gorest.co.in/public/v1";
        requestSpecs = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecs = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void requestResponseSpecification() {
        given()
                .param("page", 1)
                .spec(requestSpecs)

                .when()
                .get("/users")

                .then()

                .body("meta.pagination.page", equalTo(1))
                .spec(responseSpecs)
        ;
    }

    @Test
    public void extractingJsonPath() {

        String placeName =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .statusCode(200)
                        .extract().path("places[0].'place name'");
        System.out.println("placeName = " + placeName);
    }

    @Test
    public void extractingJsonPathInt() {
        int limit =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit");
        System.out.println("limit = " + limit);
        Assert.assertEquals(limit, 10, "test sonucu");

    }

    @Test
    public void extractingJsonPathInt2() {
        int id =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data[2].id");
        System.out.println("id = " + id);


    }

    @Test
    public void extractingJsonPathIntList() {
        List<Integer> idler =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.id");
        System.out.println("idler = " + idler);
        Assert.assertTrue(idler.contains(4556));


    }

    @Test
    public void extractingJsonPathStringList() {
        List<String> nameler =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.name");
        System.out.println("nameler = " + nameler);
        Assert.assertTrue(nameler.contains("Aruna Adiga"));

    }
    @Test
    public void extractingJsonPathResponsAll() {
        Response response =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response()
                ;
        List<Integer> idler=response.path("data.id");
        List<String> isimler=response.path("data.name");
        int limit=response.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("isimler = " + isimler);
        System.out.println("limit = " + limit);
    }
    @Test
    public void extractingJsonPOJO() {
        
        Location yer=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().as(Location.class)
        ;
        System.out.println("yer = " + yer);

        System.out.println("yer.getCountry() = " + yer.getCountry());
        System.out.println("yer.getPlaces().get(0).getPlacename() = " + yer.getPlaces().get(0).getPlacename());
    }
}




























