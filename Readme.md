To make the simpliest api call we need to include two headers for the authentication


```bash
curl -X GET -v https://data.alpaca.markets/v2/stocks/bars?symbols=AAPL \ 
    -H "APCA-API-KEY-ID: PK0057UXMJ8NV2USCFDE" \
    -H "APCA-API-SECRET-KEY: MmFj706Jr6CD52vFDnsHw4upgPj2NUsHDT2vam2H"\
```

Nice thing to check api: https://docs.alpaca.markets/reference/stockbars


Check list of Stocks (paginated)
https://docs.alpaca.markets/reference/stocktrades-1

if  "next_page_token" is null no pages are there


1. Implement way to create interface for stategies so we can just implement something 
2. GPT strategy using Newsletter
3. Golden Cross and RSI
4. CHeck sma50 hours and sma50days two diff strat

Different Strategies:
https://alpaca.markets/learn/automated-trading-list-1

Check Strategy interface here.


1. git add .