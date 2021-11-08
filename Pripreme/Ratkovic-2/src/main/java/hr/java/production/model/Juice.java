package hr.java.production.model;

import java.time.LocalDateTime;

public non-sealed class Juice extends JuiceEntity implements Squeezable{


    public Juice(String name, LocalDateTime createDateTime, Item[] items) {
        super(name, createDateTime, items);
    }

    @Override
    public int getSqueeze() {
        return 50;
    }
}
