package Lumberj3ck;

import io.github.cdimascio.dotenv.Dotenv;

public class AlpacaKeysManager {
    public final String api_key;
    public final String api_secret_key;
    public final String url;

    AlpacaKeysManager(){
        Dotenv de = Dotenv.load();

        this.api_key = de.get("api_key");
        this.api_secret_key = de.get("api_secret_key");
        this.url = "https://paper-api.alpaca.markets/v2/orders";
    }
}
