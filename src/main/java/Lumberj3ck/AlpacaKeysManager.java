package Lumberj3ck;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AlpacaKeysManager {
    private final String api_key;
    private final String api_secret_key;
    public OkHttpClient client;

    AlpacaKeysManager(){
        Dotenv de = Dotenv.load();

        this.api_key = de.get("api_key");
        this.api_secret_key = de.get("api_secret_key");

        this.client = new OkHttpClient.Builder()
        .addInterceptor(chain -> {
            Request originalRequest = chain.request();
            Request newRequest = originalRequest.newBuilder()
                .addHeader("APCA-API-KEY-ID", this.api_key)
                .addHeader("APCA-API-SECRET-KEY", this.api_secret_key)
                .addHeader("accept", "application/json")
                .build();
            return chain.proceed(newRequest);
        })
        .build();

    }
}
