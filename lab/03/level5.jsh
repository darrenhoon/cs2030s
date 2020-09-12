/open Request.java
/open Driver.java
/open JustRide.java
/open TakeACab.java
/open ShareARide.java
/open NormalCab.java
/open PrivateCar.java
/open Booking.java

Booking findBestBooking(Request r, Driver[] driver) {

    if (driver.length == 1) {
        return new Booking(driver[0],r);
    } else {
        Booking b = new Booking(driver[0],r);
        for (Driver d: driver) {

            Booking current = new Booking(d,r);

            if (b.compareTo(current) > 0) {
                b = current;
            }
        }
        return b;
    }
}
