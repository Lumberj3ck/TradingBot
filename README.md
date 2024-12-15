## Traidy

### Description
This project is trading bot which allows you to trade using implemented strategies, but also you can define your own strategies.

### Instalation 

For downloading please go to the [releases](https://github.com/Lumberj3ck/TradingBot/releases)

Jar file require for you to have a .env file in the root file along with jar file.

```bash
   └───tradingApp  
           ├───TradingBot.jar  
           ├───.env
```

.Env file is required for bot to access api. Since we don't want to store any secrets in the repository we will require you to go by [following link](www.google.com) and download the .env with test secret key and test account created. Place .env file into the same folder with jar file.

Run jar file:
```bash
java -jar TradingBot.jar
```

### Logging 

In order to see more logging output, set the logging level in log4j2.xml file inside of resourses folder to debug mode. For decreasing logging output set higher level of logging for example error. 


Goals:  
Create abstractions to create freely strategies

Strategies to implement:
1. Chatgpt strategy
2. Hourly sma and dayly smas strategies
3. SMA + RSI

TODO:

1.  Discuss if we should decouple TradingExecutor from Abstract Strategy.  Because Strategy is supposed to be only responsible for entering market or exiting market, everything else is done by TradingExecutor
2. Move isPositionOpen from strategy to Executor
3. Update hardcoded functionality  
4. When strategy is initialised we can decide which tradingBroker should we use we can for example use test and may use papertrading implementation  
5. Add logging  
6. Add a bigger list of stocks  
7. make alpaca api url more changeable  
8. Add stops orders for buying
