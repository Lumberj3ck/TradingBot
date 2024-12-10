Goals:
Create abstractions to create freely strategies

Strategies to implement:

1. Chatgpt strategy
2. Hourly sma and dayly smas strategies
3. SMA + RSI


TODO:
1. Update hardcoded functionality
2. When strategy is initialised we can decide which tradingBroker should we use we can for example use test and may use papertrading implementation
3. Add logging
4. Add a bigger list of stocks
5. make alpaca api url more changeable
6. Add stops orders for buying


Purpose of TradeExecutor to add one more abstraction and at some point make test Executor which will extend TradeExecutor

- [x] Discuss if we should decouple TradingExecutor from Abstract Strategy.  Because Strategy is supposed to be only responsible for entering market or exiting market, everything else is done by TradingExecutor
- [x] Move isPositionOpen from strategy to Executor