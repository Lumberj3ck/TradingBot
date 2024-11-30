
To make the simpliest api call we need to include two headers for the authentication


```bash
curl -X GET -v https://data.alpaca.markets/v2/stocks/bars?symbols=AAPL \ 
    -H "APCA-API-KEY-ID: PK0057UXMJ8NV2USCFDE" \
    -H "APCA-API-SECRET-KEY: MmFj706Jr6CD52vFDnsHw4upgPj2NUsHDT2vam2H"\
```

Nice thing to check api: https://docs.alpaca.markets/reference/stockbars