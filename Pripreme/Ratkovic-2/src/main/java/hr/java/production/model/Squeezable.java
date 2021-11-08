package hr.java.production.model;

public sealed interface Squeezable permits Juice {
    int getSqueeze();
}
