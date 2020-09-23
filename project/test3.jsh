/open Customer.java
/open Server.java
/open Event.java
/open ArriveEvent.java
/open WaitEvent.java
/open ServeEvent.java
/open DoneEvent.java
/open LeaveEvent.java
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, true, false, 0)))
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, true, false, 0))).execute()
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, true, false, 0))).execute().execute()
new ArriveEvent(new Customer(2, 0.6), Arrays.asList(new Server(1, false, false, 1.0)))
new ArriveEvent(new Customer(2, 0.6), Arrays.asList(new Server(1, false, false, 1.0))).execute()
new ArriveEvent(new Customer(2, 0.6), Arrays.asList(new Server(1, false, false, 1.0))).execute().execute()
new ArriveEvent(new Customer(2, 0.6), Arrays.asList(new Server(1, false, false, 1.0))).execute().execute().execute()
new ArriveEvent(new Customer(3, 0.6), Arrays.asList(new Server(1, false, true, 1.0)))
new ArriveEvent(new Customer(3, 0.6), Arrays.asList(new Server(1, false, true, 1.0))).execute()
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, false, false, 0), new Server(2, true, false, 0))).execute()
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, false, false, 0), new Server(2, true, false, 0))).execute().execute()
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, false, true, 5.0), new Server(2, false, false, 10.0))).execute()
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, false, true, 5.0), new Server(2, false, false, 10.0))).execute().execute()
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, false, true, 5.0), new Server(2, false, false, 10.0))).execute().execute().execute()
new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, false, true, 5.0), new Server(2, false, true, 10.0))).execute()
/exit
