/open Customer.java
/open Server.java
/open ArriveEvent.java

new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, true, false, 0))) // server is available
System.out.println(new ArriveEvent(new Customer(1, 0.5), Arrays.asList(new Server(1, true, false, 0))) )




new ArriveEvent(new Customer(2, 0.6), Arrays.asList(new Server(1, false, false, 1.0))) // server is busy, but no waiting customer

System.out.println(new ArriveEvent(new Customer(2, 0.6), Arrays.asList(new Server(1, false, false, 1.0))) )




new ArriveEvent(new Customer(3, 0.6), Arrays.asList(new Server(1, false, true, 1.0))) // server is busy with waiting customer

System.out.println(new ArriveEvent(new Customer(3, 0.6), Arrays.asList(new Server(1, false, true, 1.0))) )
