package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RestApiUtils {

    public static JSONArray getFilmResults(Response response) {
        JSONObject json = new JSONObject(response.asString());
        return json.getJSONArray("results");
    }

    public static <T> List<T> deserializeList(Response response, String key, Class<T> clazz) {
        return response.jsonPath().getList(key, clazz);
    }

    public static JSONObject getFilmWithLatestReleaseDate(JSONArray films) {
        JSONObject latestFilm = null;
        LocalDate latestDate = LocalDate.MIN;
        for (int i = 0; i < films.length(); i++) {
            JSONObject film = films.getJSONObject(i);
            LocalDate releaseDate = LocalDate.parse(film.getString("release_date"));

            if (releaseDate.isAfter(latestDate)) {
                latestDate = releaseDate;
                latestFilm = film;
            }
        }
        return latestFilm;
    }

    public static JSONArray getCharactersFromFilm(JSONObject film) {
        return film.getJSONArray("characters");
    }

    public static JSONObject getTallestCharacter(JSONArray characterUrls) {
        int maxHeight = -1;
        JSONObject tallestCharacter = null;

        for (int i = 0; i < characterUrls.length(); i++) {
            String characterUrl = characterUrls.getString(i);
            Response response = io.restassured.RestAssured.get(characterUrl);
            if (response.getStatusCode() == 200) {
                JSONObject character = new JSONObject(response.asString());
                try {
                    int height = Integer.parseInt(character.getString("height"));
                    if (height > maxHeight) {
                        maxHeight = height;
                        tallestCharacter = character;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return tallestCharacter;
    }

    public static JSONObject getTallestCharacterInAllFilms(JSONArray films) {
        JSONObject tallest = null;
        int tallestHeight = -1;

        for (int i = 0; i < films.length(); i++) {
            JSONObject film = films.getJSONObject(i);
            JSONArray characters = film.getJSONArray("characters");

            for (int j = 0; j < characters.length(); j++) {
                String characterUrl = characters.getString(j);
                Response response = RestAssured.get(characterUrl);

                if (response.getStatusCode() == 200) {
                    JSONObject character = new JSONObject(response.asString());
                    try {
                        int height = Integer.parseInt(character.getString("height"));
                        if (height > tallestHeight) {
                            tallestHeight = height;
                            tallest = character;
                        }
                    } catch (NumberFormatException | org.json.JSONException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return tallest;
    }

    public static String readSchemaFile(String fileName) {
        InputStream inputStream = RestApiUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new RuntimeException("Schema file not found: " + fileName);
        }
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    public static InputStream getSchemaAsStream(String fileName) {
        InputStream inputStream = RestApiUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new RuntimeException("Schema file not found: " + fileName);
        }
        return inputStream;
    }

}


