package hr.java.production.main;

import hr.java.production.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final int AMOUNT_OF_CATEGORIES = 3;
    private static final int AMOUNT_OF_ITEMS = 5;
    private static final int AMOUNT_OF_FACTORIES = 2;
    private static final int AMOUNT_OF_FACTORY_ITEMS = 2;
    private static final int AMOUNT_OF_STORES = 2;
    public static final int AMOUNT_OF_STORE_ITEMS = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*Category input*/
        Category[] categories = new Category[AMOUNT_OF_CATEGORIES];
        for (int i = 0; i < AMOUNT_OF_CATEGORIES; i++) {
            categories[i] = createCategory(scanner, i);
        }

        /*Item input*/
        Item[] items = new Item[AMOUNT_OF_ITEMS];
        for (int i = 0; i < AMOUNT_OF_ITEMS; i++) {
            items[i] = createItem(scanner, categories, i);
        }

        /*Factory input*/
        Factory[] factories = new Factory[AMOUNT_OF_FACTORIES];
        for (int i = 0; i < AMOUNT_OF_FACTORIES; i++) {
            factories[i] = createFactory(scanner, items, i);
        }

        /*Store input*/
        Store[] stores = new Store[AMOUNT_OF_STORES];
        for (int i = 0; i < AMOUNT_OF_STORES; i++) {
            stores[i] = createStore(scanner, items, i);
        }

        /*Find factory that manufactures item with the largest volume*/
        largestVolumeFactory(factories);

        /*Find store that sells the cheapest item*/
        cheapestArticleStore(stores);
    }

    private static void largestVolumeFactory(Factory[] factories) {
        /*Set first factory as one with largest volume*/
        Factory largestVolumeFactory = factories[0];
        /*Find largest volume item of FIRST factory*/
        Item largestVolumeItem = largestVolumeFactory.getItems()[0];
        BigDecimal largestVolume = largestVolumeItem.getVolume();
        for (int i = 1; i < AMOUNT_OF_FACTORY_ITEMS; i++) {
            if (largestVolumeFactory.getItems()[i].getVolume().compareTo(largestVolume) > 0) {
                largestVolumeItem = largestVolumeFactory.getItems()[i];
                largestVolume = largestVolumeItem.getVolume();
            }
        }
        /*Continue comparison*/
        /*Each factory*/
        for (int i = 1; i < AMOUNT_OF_FACTORIES; i++) {
            /*Each item*/
            for (int j = 0; j < AMOUNT_OF_FACTORY_ITEMS; j++) {
                /*If item [j] in factory [i] has volume larger than largestVolume*/
                if (factories[i].getItems()[j].getVolume().compareTo(largestVolume) > 0) {
                    largestVolumeFactory = factories[i];
                    largestVolumeItem = largestVolumeFactory.getItems()[j];
                    largestVolume = largestVolumeItem.getVolume();
                }
            }
        }
        /*Output*/
        System.out.println("Factory with largest volume item is " + largestVolumeFactory.getName());
        System.out.println("The item with largest volume is " + largestVolumeItem.getName() +
                " (Volume = " + largestVolumeItem.getVolume() + ")");
    }

    public static void cheapestArticleStore(Store[] stores) {
        /*Set first store as the one with cheapest item*/
        Store cheapestArticleStore = stores[0];
        /*Find cheapest item of FIRST store*/
        Item cheapestArticle = cheapestArticleStore.getItems()[0];
        BigDecimal cheapestArticlePrice = cheapestArticle.getSellingPrice();
        for (int i = 0; i < AMOUNT_OF_STORE_ITEMS; i++) {
            if (cheapestArticleStore.getItems()[i].getSellingPrice().compareTo(cheapestArticlePrice) > 0) {
                cheapestArticle = cheapestArticleStore.getItems()[i];
                cheapestArticlePrice = cheapestArticle.getSellingPrice();
            }
        }
        /*Continue comparison*/
        /*Each store*/
        for (int i = 0; i < AMOUNT_OF_STORES; i++) {
            /*Each item*/
            for (int j = 0; j < AMOUNT_OF_STORE_ITEMS; j++) {
                /*If item [j] in store [i] has price lower than cheapestArticlePrice*/
                if (stores[i].getItems()[j].getSellingPrice().compareTo(cheapestArticlePrice) < 0) {
                    cheapestArticleStore = stores[i];
                    cheapestArticle = cheapestArticleStore.getItems()[j];
                    cheapestArticlePrice = cheapestArticle.getSellingPrice();
                }
            }
        }
        /*Output*/
        System.out.println("Store with cheapest article is " + cheapestArticleStore.getName());
        System.out.println("The item with lowest price is " + cheapestArticle.getName() +
                " (Price = " + cheapestArticle.getSellingPrice() + ")");
    }

    public static Category createCategory(Scanner scanner, int n) {
        /*Input Name*/
        System.out.print("Enter " + (n + 1) + ". category name: ");
        String name = scanner.nextLine();

        /*Input Description*/
        System.out.print("Enter " + (n + 1) + ". category description: ");
        String description = scanner.nextLine();

        /*Return object*/
        return new Category(name, description);
    }

    public static Item createItem(Scanner scanner, Category[] categories, int n) {
        /*Input Name*/
        System.out.print("Enter " + (n + 1) + ". item name: ");
        String name = scanner.nextLine();

        /*Select Category*/
        System.out.println("Select category: ");
        for (int i = 0; i < AMOUNT_OF_CATEGORIES; i++) {
            System.out.println((i + 1) + " " + categories[i].getName());
        }
        int selectedCategory;
        /*Repeat input until a correct option is selected*/
        do {
            System.out.print("Category: ");
            selectedCategory = scanner.nextInt();
            if (selectedCategory < 1 || selectedCategory > AMOUNT_OF_CATEGORIES)
                System.out.println("That category does not exist.");
        } while (selectedCategory < 1 || selectedCategory > AMOUNT_OF_CATEGORIES);
        scanner.nextLine();
        Category category = categories[selectedCategory - 1];

        /*Input width*/
        System.out.print("Enter width: ");
        BigDecimal width = scanner.nextBigDecimal();

        /*Input height*/
        System.out.print("Enter height: ");
        BigDecimal height = scanner.nextBigDecimal();

        /*Input length*/
        System.out.print("Enter length: ");
        BigDecimal length = scanner.nextBigDecimal();

        /*Input production cost*/
        System.out.print("Enter production cost: ");
        BigDecimal productionCost = scanner.nextBigDecimal();

        /*Input selling price*/
        System.out.print("Enter selling price: ");
        BigDecimal sellingPrice = scanner.nextBigDecimal();

        scanner.nextLine();

        return new Item(name, category, width, height, length, productionCost, sellingPrice);
    }

    public static Factory createFactory(Scanner scanner, Item[] items, int n) {
        /*Input name*/
        System.out.print("Enter " + (n + 1) + ". factory name: ");
        String name = scanner.nextLine();

        /*Input Address*/
        Address address = createAddress(scanner);

        /*Select items*/
        Item[] factoryItems = new Item[AMOUNT_OF_FACTORY_ITEMS];
        for (int i = 0; i < AMOUNT_OF_FACTORY_ITEMS; i++) {
            System.out.println("Select " + (i + 1) + ". item: ");
            for (int j = 0; j < AMOUNT_OF_ITEMS; j++) {
                System.out.println((j + 1) + " " + items[j].getName());
            }
            int selectedItem;
            do {
                System.out.print("Item: ");
                selectedItem = scanner.nextInt();
                scanner.nextLine();
                if (selectedItem < 1 || selectedItem > AMOUNT_OF_ITEMS)
                    System.out.println("That item does not exist.");
            } while (selectedItem < 1 || selectedItem > AMOUNT_OF_ITEMS);
            factoryItems[i] = items[selectedItem - 1];
        }

        return new Factory(name, address, factoryItems);
    }

    public static Address createAddress(Scanner scanner) {
        /*Input street*/
        System.out.print("Enter street: ");
        String street = scanner.nextLine();

        /*input house number*/
        System.out.print("Enter house number: ");
        String houseNumber = scanner.nextLine();

        /*input city*/
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        /*Input postal code*/
        System.out.print("Enter postal code: ");
        String postalCode = scanner.nextLine();

        return new Address(street, houseNumber, city, postalCode);
    }

    public static Store createStore(Scanner scanner, Item[] items, int n) {
        /*Input name*/
        System.out.print("Enter " + (n + 1) + ". store name: ");
        String name = scanner.nextLine();

        /*Input web address*/
        System.out.print("Enter web address: ");
        String webAddress = scanner.nextLine();

        /*Select items*/
        Item[] storeItems = new Item[AMOUNT_OF_STORE_ITEMS];
        for (int i = 0; i < AMOUNT_OF_STORE_ITEMS; i++) {
            System.out.println("Select " + (i + 1) + ". item: ");
            for (int j = 0; j < AMOUNT_OF_ITEMS; j++) {
                System.out.println((j + 1) + " " + items[j].getName());
            }
            int selectedItem;
            do {
                System.out.print("Item: ");
                selectedItem = scanner.nextInt();
                scanner.nextLine();
                if (selectedItem < 1 || selectedItem > AMOUNT_OF_ITEMS)
                    System.out.println("That item does not exist.");
            } while (selectedItem < 1 || selectedItem > AMOUNT_OF_ITEMS);
            storeItems[i] = items[selectedItem - 1];
        }

        return new Store(name, webAddress, storeItems);
    }
}