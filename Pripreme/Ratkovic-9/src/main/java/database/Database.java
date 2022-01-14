package database;

import hr.java.production.model.Category;
import hr.java.production.model.Discount;
import hr.java.production.model.Item;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Database {

    public static Connection connectToDatabase() throws SQLException, IOException {
        Properties configuration = new Properties();
        configuration.load(new FileReader("dat/database.properties"));

        String databaseURL = configuration.getProperty("databaseURL");
        String databaseUsername = configuration.getProperty("databaseUsername");
        String databasePassword = configuration.getProperty("databasePassword");

        Connection connection = DriverManager
                .getConnection(databaseURL, databaseUsername, databasePassword);
        return connection;
    }

    public static List<Category> fetchCategories(Connection connection) throws SQLException {
        List<Category> categories = new ArrayList<>();

        Statement sql = connection.createStatement();
        ResultSet categoryResultSet = sql.executeQuery("SELECT * FROM CATEGORY");

        while (categoryResultSet.next()) {
            Long id = categoryResultSet.getLong("ID");
            String name = categoryResultSet.getString("NAME");
            String description = categoryResultSet.getString("DESCRIPTION");

            Category category = new Category(name, description, id);
            categories.add(category);
        }

        return categories;
    }

    public static List<Item> fetchItems(Connection connection, List<Category> categories) throws SQLException {
        List<Item> items = new ArrayList<>();

        Statement sql = connection.createStatement();
        ResultSet itemResultSet = sql.executeQuery("SELECT * FROM ITEM");

        while (itemResultSet.next()) {
            Long id = itemResultSet.getLong("ID");
            Long categoryId = itemResultSet.getLong("CATEGORY_ID");
            Category category = categories.stream().filter(c -> c.getId().equals(categoryId)).findFirst().get();
            String name = itemResultSet.getString("NAME");
            BigDecimal width = itemResultSet.getBigDecimal("WIDTH");
            BigDecimal height = itemResultSet.getBigDecimal("HEIGHT");
            BigDecimal length = itemResultSet.getBigDecimal("LENGTH");
            BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
            BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

            Item item = new Item(name, category, width, height, length,
                    productionCost, sellingPrice, new Discount(BigDecimal.ZERO), id);

            items.add(item);
        }

        return items;
    }

}
