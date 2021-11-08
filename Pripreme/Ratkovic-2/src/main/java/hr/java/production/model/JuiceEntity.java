package hr.java.production.model;

import java.time.LocalDateTime;

public abstract class JuiceEntity {
    String name;
    LocalDateTime createDateTime;
    Item[] items;

    public JuiceEntity(String name, LocalDateTime createDateTime, Item[] items) {
        this.name = name;
        this.createDateTime = createDateTime;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
