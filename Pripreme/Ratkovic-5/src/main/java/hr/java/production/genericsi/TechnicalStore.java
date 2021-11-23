package hr.java.production.genericsi;

import hr.java.production.model.Technical;

import java.util.List;

public class TechnicalStore<T extends Technical> {
    List<T> lista;
}
