package Lumberj3ck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AlpacaKeysManager {
    private final String api_key;
    private final String api_secret_key;
    public OkHttpClient client;
    private static final Logger logger = LogManager.getRootLogger();

    AlpacaKeysManager(){
        logger.debug("Initializing AlpacaKeysManager");
        Dotenv de = Dotenv.load();

        this.api_key = de.get("api_key");
        this.api_secret_key = de.get("api_secret_key");
        
        if (this.api_key == null || this.api_secret_key == null) {
            logger.error("Failed to load API keys from environment variables");
            throw new IllegalStateException("API keys not found in environment");
        }
        
        logger.debug("Building OkHttpClient with interceptor");
        this.client = new OkHttpClient.Builder()
        .addInterceptor(chain -> {
            Request originalRequest = chain.request();
            logger.debug("Intercepting request to: {}", originalRequest.url());
            Request newRequest = originalRequest.newBuilder()
                .addHeader("APCA-API-KEY-ID", this.api_key)
                .addHeader("APCA-API-SECRET-KEY", this.api_secret_key)
                .addHeader("accept", "application/json")
                .build();
            return chain.proceed(newRequest);
        })
        .build();
        logger.info("AlpacaKeysManager initialized successfully");
    }
}
