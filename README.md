
Goals:
Create abstractions to create freely strategies

Strategies to implement:

1. Chatgpt strategy
2. Hourly sma and dayly smas strategies
3. SMA + RSI


TODO:
1. Update hardcoded functionality
2. When strategy is initialised we can decide which tradingBroker should we use we can for example use test and may use papertrading implementation


Purpose of TradeExecutor to add one more abstraction and at some point make test Executor which will extend TradeExecutor

Discuss if we should decouple TradingExecutor from Abstract Strategy.
Because Strategy is supposed to be only responsible for entering market or exiting market, everything else is done by TradingExecutor