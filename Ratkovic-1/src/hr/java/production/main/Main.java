package hr.java.production.main;

import hr.java.production.model.Category;
import hr.java.production.model.Item;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final int AMOUNT_OF_CATEGORIES = 3;
    private static final int AMOUNT_OF_ITEMS = 5;

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
            items[i] = createItem(scanner, categories);
            System.out.println(items[i].getCategory().getName());
        }
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

    public static Item createItem(Scanner scanner, Category[] categories) {
        /*Input Name*/
        System.out.print("Enter item name: ");
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

        return new Item(name, category, width, height, length, productionCost, sellingPrice);
    }
}