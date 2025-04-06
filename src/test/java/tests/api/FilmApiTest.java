package tests.api;
import data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import objects.FilmData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.RestApiUtils;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;


public class FilmApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void findFilmWithLatestReleaseDate() {
        Response response = ApiCalls.getAllFilms();
        JSONArray films = RestApiUtils.getFilmResults(response);
        JSONObject latestFilm = RestApiUtils.getFilmWithLatestReleaseDate(films);
        Assert.assertNotNull(latestFilm);
        System.out.println("Latest FilmData: " + latestFilm.getString("title") +
                " (" + latestFilm.getString("release_date") + ")");
    }

    @Test
    public void findTallestCharacterFromLatestFilm() {
        Response response = ApiCalls.getAllFilms();
        JSONArray films = RestApiUtils.getFilmResults(response);
        JSONObject latestFilm = RestApiUtils.getFilmWithLatestReleaseDate(films);
        JSONArray characters = RestApiUtils.getCharactersFromFilm(latestFilm);
        JSONObject tallestCharacter = RestApiUtils.getTallestCharacter(characters);
        Assert.assertNotNull(tallestCharacter, "Tallest character should not be null");
        System.out.println("Tallest character in latest film: " + tallestCharacter.getString("name") +
                " with height: " + tallestCharacter.getString("height"));
    }

    @Test
    public void findTallestCharacterInAllFilms() {
        Response response = ApiCalls.getAllFilms();
        JSONArray films = RestApiUtils.getFilmResults(response);
        JSONObject tallest = RestApiUtils.getTallestCharacterInAllFilms(films);
        Assert.assertNotNull(tallest, "Tallest character in all films should not be null");
        System.out.println("Tallest character in all Star Wars films: " + tallest.getString("name") +
                " with height: " + tallest.getString("height"));
    }

    @Test
    public void validatePeopleApiContract() {
        Response response = ApiCalls.getPeople();
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Validating with schema:\n" + RestApiUtils.readSchemaFile("people-schema.json"));
        InputStream schema = RestApiUtils.getSchemaAsStream("people-schema.json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }

    @Test
    public void getLatestFilmUsingPOJO() {
        Response response = ApiCalls.getAllFilms();
        Assert.assertEquals(response.getStatusCode(), 200);
        List<FilmData> films = RestApiUtils.deserializeList(response, "results", FilmData.class);
        Assert.assertFalse(films.isEmpty(), "Film list should not be empty");
        FilmData latestFilm = null;
        LocalDate latestDate = LocalDate.MIN;

        for (FilmData film : films) {
            LocalDate releaseDate = LocalDate.parse(film.getRelease_date());
            if (releaseDate.isAfter(latestDate)) {
                latestDate = releaseDate;
                latestFilm = film;
            }
        }
        Assert.assertNotNull(latestFilm, "Latest film should not be null");
        System.out.println("Latest film using POJO: " + latestFilm.getTitle() + " (" + latestFilm.getRelease_date()+ ")");
    }

}


