/open Request.java
/open Driver.java
/open Services.java
/open JustRide.java
/open TakeACab.java
/open ShareARide.java
/open NormalCab.java
/open PrivateCar.java
/open Booking.java

new NormalCab("SHA1234", 5)
new Booking(new NormalCab("SHA1234", 5), new Request(20, 3, 1000))
new NormalCab("SHA2345", 10)
new Booking(new NormalCab("SHA2345", 10), new Request(10, 1, 900))
Booking b1 = new Booking(new NormalCab("SHA2345", 10), new Request(10, 1,
900))
Booking b2 = new Booking(new NormalCab("SHA2345", 10), new Request(10, 1,
900))
