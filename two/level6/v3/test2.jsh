/open Customer.java
/open Server.java
/open Shop.java
/open Pair.java
/open Event.java
/open ArriveEvent.java
/open WaitEvent.java
/open ServeEvent.java
/open LeaveEvent.java
/open DoneEvent.java

// one available server with no waiting customer
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new
                Server(1,true,false,0)))).first()
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new
                Server(1,true,false,0)))).second()
// one busy server with no waiting customer
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new
                Server(1,false,false,1.0)))).first()
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new
                Server(1,false,false,1.0)))).second()
// one busy server with waiting customer
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new
                Server(1,false,true,2.0)))).first()
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new
                Server(1,false,true,2.0)))).second()

/exit
