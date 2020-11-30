//**** OO Design (a)
//**** Write your answer below
A a1 = new A();
A a2 = new A();
a1.set(a2);
a2.set(a1);
a1.get() == a2;
a2.get() == a1;
