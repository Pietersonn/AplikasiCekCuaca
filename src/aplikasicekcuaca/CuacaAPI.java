/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasicekcuaca;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CuacaAPI {

    private static final String API_KEY = "ecebbdea2303982b4b0fde041792d944"; // Ganti dengan API key OpenWeatherMap Anda

    public static String getCuaca(String kota) {
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + API_KEY + "&units=metric&lang=id";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return "Error: Gagal mendapatkan data. Kode respons: " + responseCode;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return parseCuacaResponse(response.toString());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String parseCuacaResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        String cuaca = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("main");
        String deskripsi = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
        double suhu = jsonResponse.getJSONObject("main").getDouble("temp");
        return "Cuaca: " + cuaca + "\nDeskripsi: " + deskripsi + "\nSuhu: " + suhu + "°C";
    }
}
