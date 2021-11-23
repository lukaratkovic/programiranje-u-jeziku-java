package hr.java.production.genericsi;

import hr.java.production.model.Edible;

import java.util.List;

public class FoodStore<T extends Edible> {
    List<T> lista;
}
