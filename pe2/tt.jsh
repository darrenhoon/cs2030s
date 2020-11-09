/open GameObject.java
/open Candle.java
/open Sword.java
/open Troll.java
/open Room.java
/open actions.jsh

Room r1 = new Room("foyer").add(new Candle())
Room r2 = r1.go(x -> new Room("dining").add(new Troll()))
Room r3 = r2.go(x -> new Room("library").add(new Sword()))
