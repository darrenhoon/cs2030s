Pair<Integer,String> pair = Pair.of(1, "one");
pair.first()
pair.second()
Pair<Long,Long> pair = Pair.of(0L, 100L);
pair.first()
pair.second()
// one available server with no waiting customer
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new Server(1,true,false,0)))).first()
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new Server(1,true,false,0)))).second()
// one busy server with no waiting customer
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new Server(1,false,false,1.0)))).first()
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new Server(1,false,false,1.0)))).second()
// one busy server with waiting customer
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new Server(1,false,true,2.0)))).first()
new ArriveEvent(new Customer(1, 1.0)).execute(new Shop(List.of(new Server(1,false,true,2.0)))).second()
