## Order Book

### Part A

Compile and test command

`./mvn clean test`

### Part B

Production version would require multiple changes/additions:

* Based on projected number of orders on production, choose appropriate initial capacity and load factor to avoid frequent map rehashing
* Store 'O' and 'B' orders in separate collections to avoid frequent filtering
* Depending on how often sorting and aggregation functions are being invoked on production it might be more optimal to store orders already sorted by price and time
* Agree with OrderBook class consumer on more optimal error handling (i.e. it doesn't have to throw an exception when price or size is not found) 
* Add logs (ideally integrated with log analysing tool e.g. Splunk)
* Monitoring (memory usage, GC call intervals, etc.)
* Consider using appropriate DB (most likely in-memory DB e.g. Redis) to store orders

I'm happy to discuss above points and more during a call/meeting.
