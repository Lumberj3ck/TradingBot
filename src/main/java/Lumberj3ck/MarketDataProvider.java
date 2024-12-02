package Lumberj3ck;

import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MarketDataProvider {

    public static void getDataFromMarket(String symbol){
        Dotenv dotenv = Dotenv.load();


        String api_key = dotenv.get("api_key");
        String api_secret_key = dotenv.get("api_secret_key");

        OkHttpClient client = new OkHttpClient();

        String requestUrl = String.format("https://data.alpaca.markets/v2/stocks/trades?symbols=%s&start=2024-09-01&limit=1000&feed=sip&sort=asc", symbol);
        Request request = new Request.Builder()
        .url(requestUrl)
        .get()
        .addHeader("accept", "application/json")
        .addHeader("APCA-API-KEY-ID", api_key)
        .addHeader("APCA-API-SECRET-KEY", api_secret_key)
        .build();

        try {
            Response response = client.newCall(request).execute(); 
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);

            System.out.println(Jobject.getString("next_page_token"));
            // System.out.println(Jobject.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
