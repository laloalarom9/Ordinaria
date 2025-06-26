package org.vaadin.example.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.vaadin.example.model.UserFront;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/usuarios";
    private static final Gson gson = new Gson();

    public static List<UserFront> obtenerUsuarios() {
        try {
            // Crear solicitud GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            // Ejecutar la solicitud
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Convertir JSON a lista de usuarios
            Type userListType = new TypeToken<List<UserFront>>() {}.getType();
            return gson.fromJson(response.body(), userListType);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Lista vacía si hay error
        }
    }
    public static void addUsuario(UserFront user) {
        try {
            String json = gson.toJson(user); // Convertir el objeto a JSON

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.discarding());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] generatePdf() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/pdf"))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("Error al obtener PDF: código " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void actualizarUsuario(UUID id, UserFront user) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
