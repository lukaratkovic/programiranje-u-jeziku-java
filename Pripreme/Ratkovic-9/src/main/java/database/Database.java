package database;

import hr.java.production.enums.City;
import hr.java.production.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

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

            Item item = new Item(name, id, category, width, height, length,
                    productionCost, sellingPrice);

            items.add(item);
        }

        return items;
    }

    public static List<Store> fetchStores(Connection connection, List<Category> categories) throws SQLException {
        List<Store> stores = new ArrayList<>();

        Statement sql = connection.createStatement();
        ResultSet storeResultSet = sql.executeQuery("SELECT * FROM STORE");

        while (storeResultSet.next()) {
            Long id = storeResultSet.getLong("ID");
            String name = storeResultSet.getString("NAME");
            String webAddress = storeResultSet.getString("WEB_ADDRESS");

            Set<Item> storeItems = fetchStoreItems(connection, id, categories);

            Store store = new Store(name, webAddress, storeItems, id);
            stores.add(store);
        }

        return stores;
    }

    public static List<Address> fetchAddresses(Connection connection) throws SQLException {
        List<Address> addresses = new ArrayList<>();

        Statement sql = connection.createStatement();
        ResultSet addressResultSet = sql.executeQuery("SELECT * FROM ADDRESS");

        while (addressResultSet.next()) {
            Long id = addressResultSet.getLong("ID");
            String street = addressResultSet.getString("STREET");
            String houseNumber = addressResultSet.getString("HOUSE_NUMBER");
            String cityString = addressResultSet.getString("CITY");
            City city = switch (cityString) {
                case "Dugo Selo" -> City.DUGO_SELO;
                case "Koprivnica" -> City.KOPRIVNICA;
                case "New York" -> City.NEW_YORK;
                case "Rijeka" -> City.RIJEKA;
                case "Zagreb" -> City.ZAGREB;
                default -> throw new IllegalStateException("Unexpected City value.");
            };

            Address address = new Address(street, houseNumber, city);
            addresses.add(address);
        }

        return addresses;
    }

    public static List<Factory> fetchFactories(Connection connection, List<Address> addresses, List<Category> categories) throws SQLException {
        List<Factory> factories = new ArrayList<>();

        Statement sql = connection.createStatement();
        ResultSet factoryResultSet = sql.executeQuery("SELECT * FROM FACTORY");

        while (factoryResultSet.next()) {
            Long id = factoryResultSet.getLong("ID");
            String name = factoryResultSet.getString("NAME");
            Long addressID = factoryResultSet.getLong("ADDRESS_ID");

            Set<Item> factoryItems = fetchFactoryItems(connection, id, categories);
            Address address = fetchAddressById(connection, addressID);

            Factory factory = new Factory(name, address, factoryItems, id);
            factories.add(factory);
        }

        return factories;
    }

    public static Set<Item> fetchStoreItems(Connection connection, Long storeID, List<Category> categories) throws SQLException {
        Set<Item> storeItems = new HashSet<>();

        PreparedStatement itemStatement = connection.
                prepareStatement("SELECT * FROM STORE_ITEM SI, ITEM I WHERE SI.STORE_ID = ? AND SI.ITEM_ID = I.ID");
        itemStatement.setLong(1, storeID);
        ResultSet itemResultSet = itemStatement.executeQuery();

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

            storeItems.add(item);
        }

        return storeItems;
    }

    public static Set<Item> fetchFactoryItems(Connection connection, Long factoryID, List<Category> categories) throws SQLException {
        Set<Item> factoryItems = new HashSet<>();

        PreparedStatement itemStatement = connection.
                prepareStatement("SELECT * FROM FACTORY_ITEM FI, ITEM I WHERE FI.FACTORY_ID = ? AND FI.ITEM_ID = I.ID");
        itemStatement.setLong(1, factoryID);
        ResultSet itemResultSet = itemStatement.executeQuery();

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

            factoryItems.add(item);
        }

        return factoryItems;
    }

    public static Category fetchCategoryById(Connection connection, Long categoryId) throws SQLException {
        PreparedStatement categoryStatement = connection.prepareStatement("SELECT * FROM CATEGORY WHERE ID = ? LIMIT 1");
        categoryStatement.setLong(1, categoryId);
        ResultSet categoryResultSet = categoryStatement.executeQuery();

        categoryResultSet.next();
        Long id = categoryResultSet.getLong("ID");
        String name = categoryResultSet.getString("NAME");
        String description = categoryResultSet.getString("DESCRIPTION");

        return new Category(name, description, id);
    }

    public static Item fetchItemById(Connection connection, Long itemId, List<Category> categories) throws SQLException {
        PreparedStatement itemStatement = connection.prepareStatement("SELECT * FROM ITEM WHERE ID = ? LIMIT 1");
        itemStatement.setLong(1, itemId);
        ResultSet itemResultSet = itemStatement.executeQuery();

        itemResultSet.next();
        Long id = itemResultSet.getLong("ID");
        Long categoryId = itemResultSet.getLong("CATEGORY_ID");
        Category category = categories.stream().filter(c -> c.getId().equals(categoryId)).findFirst().get();
        String name = itemResultSet.getString("NAME");
        BigDecimal width = itemResultSet.getBigDecimal("WIDTH");
        BigDecimal height = itemResultSet.getBigDecimal("HEIGHT");
        BigDecimal length = itemResultSet.getBigDecimal("LENGTH");
        BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
        BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

        return new Item(name, category, width, height, length,
                productionCost, sellingPrice, new Discount(BigDecimal.ZERO), id);
    }

    public static Address fetchAddressById(Connection connection, Long addressId) throws SQLException {
        PreparedStatement addressStatement = connection.prepareStatement("SELECT * FROM ADDRESS WHERE ID = ? LIMIT 1");
        addressStatement.setLong(1, addressId);
        ResultSet addressResultSet = addressStatement.executeQuery();

        addressResultSet.next();
        String street = addressResultSet.getString("STREET");
        String houseNumber = addressResultSet.getString("HOUSE_NUMBER");
        String cityString = addressResultSet.getString("CITY");
        City city = switch (cityString) {
            case "Dugo Selo" -> City.DUGO_SELO;
            case "Koprivnica" -> City.KOPRIVNICA;
            case "New York" -> City.NEW_YORK;
            case "Rijeka" -> City.RIJEKA;
            case "Zagreb" -> City.ZAGREB;
            default -> throw new IllegalStateException("Unexpected City value.");
        };

        return new Address(street, houseNumber, city);
    }

    public static Factory fetchFactoryById(Connection connection, Long factoryId, List<Category> categories) throws SQLException {
        PreparedStatement factoryStatement = connection.prepareStatement("SELECT * FROM FACTORY WHERE ID = ? LIMIT 1");
        factoryStatement.setLong(1, factoryId);
        ResultSet factoryResultSet = factoryStatement.executeQuery();

        factoryResultSet.next();
        Long id = factoryResultSet.getLong("ID");
        String name = factoryResultSet.getString("NAME");
        Long addressID = factoryResultSet.getLong("ADDRESS_ID");

        Set<Item> factoryItems = fetchFactoryItems(connection, id, categories);
        Address address = fetchAddressById(connection, addressID);

        return new Factory(name, address, factoryItems, id);
    }

    public static Store fetchStoreById(Connection connection, Long storeId, List<Category> categories) throws SQLException {
        PreparedStatement storeStatement = connection.prepareStatement("SELECT * FROM STORE WHERE ID = ? LIMIT 1");
        storeStatement.setLong(1, storeId);
        ResultSet storeResultSet = storeStatement.executeQuery();

        storeResultSet.next();
        Long id = storeResultSet.getLong("ID");
        String name = storeResultSet.getString("NAME");
        String webAddress = storeResultSet.getString("WEB_ADDRESS");

        Set<Item> storeItems = fetchStoreItems(connection, id, categories);

        return new Store(name, webAddress, storeItems, id);
    }

    public static void insertCategory(Connection connection, Category category) throws SQLException {
        PreparedStatement categoryStatement = connection.
                prepareStatement("INSERT INTO CATEGORY (NAME, DESCRIPTION) VALUES(?, ?)");
        categoryStatement.setString(1, category.getName());
        categoryStatement.setString(2, category.getDescription());
        categoryStatement.executeUpdate();
    }

    public static void insertItem(Connection connection, Item item) throws SQLException {
        PreparedStatement itemStatement = connection.
                prepareStatement("INSERT INTO ITEM " +
                        "(CATEGORY_ID, NAME, WIDTH, HEIGHT, LENGTH, PRODUCTION_COST, SELLING_PRICE)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)");
        itemStatement.setLong(1, item.getCategory().getId());
        itemStatement.setString(2, item.getName());
        itemStatement.setBigDecimal(3, item.getWidth());
        itemStatement.setBigDecimal(4, item.getHeight());
        itemStatement.setBigDecimal(5, item.getLength());
        itemStatement.setBigDecimal(6, item.getProductionCost());
        itemStatement.setBigDecimal(7, item.getSellingPrice());
        itemStatement.executeUpdate();
    }
}
