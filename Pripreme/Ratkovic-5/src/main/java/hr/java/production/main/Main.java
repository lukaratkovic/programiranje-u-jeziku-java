package hr.java.production.main;

import hr.java.production.enums.City;
import hr.java.production.exception.DuplicateArticleException;
import hr.java.production.exception.DuplicateCategoryException;
import hr.java.production.genericsi.FoodStore;
import hr.java.production.genericsi.TechnicalStore;
import hr.java.production.model.*;
import hr.java.production.sort.ProductionSorter;
import hr.java.production.sort.VolumeSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Runs the program
 */
public class Main {
    private static final int AMOUNT_OF_CATEGORIES = 3;
    private static final int AMOUNT_OF_ITEMS = 5;
    private static final int AMOUNT_OF_FACTORIES = 2;
    private static final int AMOUNT_OF_FACTORY_ITEMS = 2;
    private static final int AMOUNT_OF_STORES = 2;
    public static final int AMOUNT_OF_STORE_ITEMS = 2;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Method called when program is run
     *
     * @param args
     */
    public static void main(String[] args) {
        logger.info("The program has been started.");
        Scanner scanner = new Scanner(System.in);

        Map<Category, List<Item>> categoryItemMap = new HashMap<>();

        /*Category input*/
        System.out.print("Skip category input? (Default categories will be created): ");
        String input = scanner.nextLine();
        Category[] categories = new Category[AMOUNT_OF_CATEGORIES];
        if (input.toUpperCase(Locale.ROOT).equals("Y")) {
            for (int i = 0; i < AMOUNT_OF_CATEGORIES; i++) {
                categories[i] = new Category("Category" + (i + 1), "");
            }
        } else {
            for (int i = 0; i < AMOUNT_OF_CATEGORIES; i++) {
                Category created;
                boolean isValid = false;
                do {
                    created = createCategory(scanner, i);
                    try {
                        for (int j = 0; j < i; j++) {
                            if (categories[j].equals(created))
                                throw new DuplicateCategoryException("This category already exists!");
                        }
                        isValid = true;
                    } catch (DuplicateCategoryException ex) {
                        logger.error("An error has occurred", ex);
                        System.out.println(ex.getMessage());
                    }
                } while (!isValid);
                categories[i] = created;
            }
        }

        /*Item input*/
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_ITEMS; i++) {
            Item created = createItem(scanner, categories, i);
            items.add(created);
            if (categoryItemMap.containsKey(created.getCategory())) {
                List<Item> currentItems = categoryItemMap.get(created.getCategory());
                currentItems.add(created);
                categoryItemMap.put(created.getCategory(), currentItems);
            } else {
                List<Item> currentItems = new ArrayList<>();
                currentItems.add(created);
                categoryItemMap.put(created.getCategory(), currentItems);
            }
        }

        aboveAveragePrice(items);

        /**
         * Technical Store input
         */
        //Sort w/Lambda
        Instant start = Instant.now();
        TechnicalStore<Laptop> technicalStore = createTechnicalStore(scanner, items);
        List<Technical> sortedTechnicals = technicalStore.getItemList().stream()
                .sorted((i1, i2) -> {
                    if (i1.getVolume().compareTo(i2.getVolume()) > 0) {
                        return 1;
                    } else if (i1.getVolume().compareTo(i2.getVolume()) < 0) {
                        return -1;
                    } else return 0;
                })
                .collect(Collectors.toList());
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));

        //Sort without lambdas
        start = Instant.now();
        Collections.sort(technicalStore.getItemList(), new VolumeSorter<Technical>());
        end = Instant.now();
        System.out.println(Duration.between(start, end));

        System.out.println("Technical store items, sorted by volume: ");
        sortedTechnicals.stream()
                .forEach(i -> System.out.println(((Item) i).getName() + " (" + ((Item) i).getVolume() + ")"));


        /**
         * Food Store input
         */
        FoodStore<Edible> foodStore = createFoodStore(scanner, items);
        //Sort with lambdas
        start = Instant.now();
        List<Edible> sortedEdibles = foodStore.getItemsList().stream()
                .sorted((i1, i2) -> {
                    if (((Item) i1).getVolume().compareTo(((Item) i2).getVolume()) > 0) {
                        return 1;
                    } else if (((Item) i1).getVolume().compareTo(((Item) i2).getVolume()) > 0) {
                        return -1;
                    } else return 0;
                })
                .collect(Collectors.toList());
        end = Instant.now();
        System.out.println(Duration.between(start, end));

        //Sort without lambdas
        start = Instant.now();
        Collections.sort(foodStore.getItemsList(), new VolumeSorter<Edible>());
        end = Instant.now();
        System.out.println(Duration.between(start, end));

        System.out.println("Food store items, sorted by volume: ");
        sortedEdibles.stream()
                .forEach(i -> System.out.println(((Item) i).getName() + "(" + (((Item) i).getVolume()) + ")"));


        /*Factory input*/
        Factory[] factories = new Factory[AMOUNT_OF_FACTORIES];
        for (int i = 0; i < AMOUNT_OF_FACTORIES; i++) {
            factories[i] = createFactory(scanner, items, i);
        }

        /*Store input*/
        Store[] stores = new Store[AMOUNT_OF_STORES];
        for (int i = 0; i < AMOUNT_OF_STORES; i++) {
            stores[i] = createStore(scanner, items, i);
            //Sort with lambdas
            start = Instant.now();
            List<Item> sortedStoreItems = stores[i].getItems().stream()
                    .sorted((i1, i2) -> {
                        if (i1.getVolume().compareTo(i2.getVolume()) > 0) return 1;
                        else if (i1.getVolume().compareTo(i2.getVolume()) < 0) return -1;
                        else return 0;
                    })
                    .collect(Collectors.toList());
            end = Instant.now();
            System.out.println(Duration.between(start, end));

            //Sort without lambdas
            start = Instant.now();
            Collections.sort(new ArrayList<Item>(stores[i].getItems()), new VolumeSorter<Item>());
            end = Instant.now();
            System.out.println(Duration.between(start, end));

            System.out.println("Store items, sorted by volume: ");
            sortedStoreItems.stream().forEach(item -> System.out.println(item.getName() + "(" + item.getVolume() + ")"));
        }

        List<Store> allStores = new ArrayList<>();
        allStores.add(technicalStore);
        allStores.add(foodStore);
        Arrays.stream(stores).forEach(s -> allStores.add(s));

        /*Find factory that manufactures item with the largest volume*/
        largestVolumeFactory(factories);

        /*Find store that sells the cheapest item*/
        cheapestArticleStore(stores);

        /*Find item with most kcal*/
        mostKCAL(items);

        /*Find laptop with shortest warranty*/
        shortestWarrantyLaptop(items);

        /*Find cheapest and most expensive items for each category*/
        categoryMinMax(categoryItemMap);

        /*Find cheapest and most expensive items from objects that implement Edible and Technical interface*/
        EdibleTechnicalMinMax(items);

        logger.info("The program has finished to completion");
    }

    /**
     * Prints out the average price of items with above-average volume
     *
     * @param items Item list
     */
    private static void aboveAveragePrice(List<Item> items) {
        BigDecimal average = items.stream()
                .map(i -> i.getVolume())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(items.stream().count()));
        List<Item> itemsAboveAverage = items.stream()
                .filter(i -> i.getVolume().compareTo(average) == 1)
                .collect(Collectors.toList());
        BigDecimal averagePriceOfItemsAboveAverage = itemsAboveAverage.stream()
                .map(i -> i.getSellingPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(itemsAboveAverage.stream().count()));

        System.out.println("Average price of all items whose volume is above average is " + averagePriceOfItemsAboveAverage);
    }

    /**
     * Finds cheapest and most expensive items of Edible and Technical interfaces
     *
     * @param items list of items
     */
    private static void EdibleTechnicalMinMax(List<Item> items) {
        List<Item> edibles = new ArrayList<>();
        List<Item> technicals = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Edible)
                edibles.add(item);
            else if (item instanceof Technical)
                technicals.add(item);
        }
        Comparator<Item> compare = new ProductionSorter();
        Collections.sort(edibles, compare);
        Collections.sort(technicals, compare);
        System.out.println("Cheapest edible: " + edibles.get(0).getName() +
                " (" + edibles.get(0).getSellingPrice() + ")");
        System.out.println("Most expensive edible: " + edibles.get(edibles.size() - 1).getName() +
                " (" + edibles.get(edibles.size() - 1).getSellingPrice() + ")");
        System.out.println("Cheapest technical: " + technicals.get(0).getName() +
                " (" + technicals.get(0).getSellingPrice() + ")");
        System.out.println("Cheapest technical: " + technicals.get(technicals.size() - 1).getName() +
                " (" + technicals.get(technicals.size() - 1).getSellingPrice() + ")");
    }

    /**
     * Finds cheapest and most expensive item for each category
     *
     * @param categoryItemMap Map (Category key, Item list value)
     */
    private static void categoryMinMax(Map<Category, List<Item>> categoryItemMap) {
        Comparator<Item> compare = new ProductionSorter();
        for (var entry : categoryItemMap.entrySet()) {
            System.out.println("Category " + entry.getKey().getName());
            Collections.sort(entry.getValue(), compare);
            /*Empty category*/
            if (entry.getValue().size() == 0)
                System.out.println("This category has no items.");
                /*Category with only 1 item*/
            else if (entry.getValue().size() == 1) {
                System.out.println("The only item in this category is " + entry.getValue().get(0).getName() +
                        " (" + entry.getValue().get(0).getSellingPrice() + ")");
            }
            /*Category with 2 or more items*/
            else {
                Item cheapest = entry.getValue().get(0);
                Item mostExpensive = entry.getValue().get(entry.getValue().size() - 1);
                System.out.println("Cheapest item: ");
                System.out.println(cheapest.getName() +
                        " (" + cheapest.getSellingPrice() + ")");
                System.out.println("Most expensive item: ");
                System.out.println(mostExpensive.getName() +
                        " (" + mostExpensive.getSellingPrice() + ")");
            }
        }
    }

    /**
     * Outputs the name of Factory with largest volume Item
     *
     * @param factories List of factories to be checked
     */
    private static void largestVolumeFactory(Factory[] factories) {
        /*Set first factory as one with largest volume*/
        Factory largestVolumeFactory = factories[0];
        /*Find largest volume item of FIRST factory*/
        Item largestVolumeItem = largestVolumeFactory.getItems().stream().toList().get(0);
        BigDecimal largestVolume = largestVolumeItem.getVolume();
        /*Each factory*/
        for (int i = 0; i < AMOUNT_OF_FACTORIES; i++) {
            /*Each item*/
            for (Item item : factories[i].getItems()) {
                if (item.getVolume().compareTo(largestVolume) > 0) {
                    largestVolumeFactory = factories[i];
                    largestVolumeItem = item;
                    largestVolume = largestVolumeItem.getVolume();
                }
            }
        }
        /*Output*/
        System.out.println("Factory with largest volume item is " + largestVolumeFactory.getName());
        System.out.println("The item with largest volume is " + largestVolumeItem.getName() +
                " (Volume = " + largestVolumeItem.getVolume() + ")");
    }

    /**
     * Outputs the name of Store with cheapest Item
     *
     * @param stores List of stores to be checked
     */
    public static void cheapestArticleStore(Store[] stores) {
        /*Set first store as the one with cheapest item*/
        Store cheapestArticleStore = stores[0];
        /*Find cheapest item of FIRST store*/
        Item cheapestArticle = cheapestArticleStore.getItems().stream().toList().get(0);
        BigDecimal cheapestArticlePrice = cheapestArticle.getSellingPrice();
        /*Each store*/
        for (int i = 0; i < AMOUNT_OF_STORES; i++) {
            /*Each item*/
            for (Item item : stores[i].getItems()) {
                if (item.getSellingPrice().compareTo(cheapestArticlePrice) < 0) {
                    cheapestArticleStore = stores[i];
                    cheapestArticle = item;
                    cheapestArticlePrice = cheapestArticle.getSellingPrice();
                }
            }
        }
        /*Output*/
        System.out.println("Store with cheapest article is " + cheapestArticleStore.getName());
        System.out.println("The item with lowest price is " + cheapestArticle.getName() +
                " (Price = " + cheapestArticle.getSellingPrice() + ")");
    }

    /**
     * Outputs the name of Edible with highest kilocalorie count
     *
     * @param items List of items to be checked
     */
    public static void mostKCAL(List<Item> items) {
        boolean foundOne = false;
        Item mostKcalItem = null;
        BigDecimal mostKcal = new BigDecimal(0);
        for (int i = 0; i < AMOUNT_OF_ITEMS; i++) {
            if (items.get(i) instanceof Fries fries) {
                if (!foundOne) {
                    mostKcalItem = fries;
                    mostKcal = fries.totalKCAL();
                    foundOne = true;
                } else if (fries.totalKCAL().compareTo(mostKcal) > 0) {
                    mostKcalItem = fries;
                    mostKcal = fries.totalKCAL();
                }
            } else if (items.get(i) instanceof GummyBears gummy) {
                if (!foundOne) {
                    mostKcalItem = gummy;
                    mostKcal = gummy.totalKCAL();
                    foundOne = true;
                } else if (gummy.totalKCAL().compareTo(mostKcal) > 0) {
                    mostKcalItem = gummy;
                    mostKcal = gummy.totalKCAL();
                }
            }
        }
        if (mostKcalItem == null) System.out.println("No edible items were inputed.");
        else System.out.println("Edible item with most kcal is: " + mostKcalItem.getName() + " (" + mostKcal + ")");
    }

    /**
     * Outputs the name of Laptop with lowest warranty
     *
     * @param items List of items to be checked
     */
    public static void shortestWarrantyLaptop(List<Item> items) {
        boolean foundLaptop = false;
        Laptop shortestWarrantyLaptop = null;
        int shortestWarranty = 0;
        for (int i = 0; i < AMOUNT_OF_ITEMS; i++) {
            if (items.get(i) instanceof Laptop laptop) {
                if (!foundLaptop) {
                    shortestWarrantyLaptop = (Laptop) items.get(i);
                    shortestWarranty = shortestWarrantyLaptop.getWarranty();
                    foundLaptop = true;
                } else if (((Laptop) items.get(i)).getWarranty() < shortestWarranty) {
                    shortestWarrantyLaptop = (Laptop) items.get(i);
                    shortestWarranty = shortestWarrantyLaptop.getWarranty();
                }
            }
        }
        if (shortestWarrantyLaptop != null)
            System.out.println("Laptop with shortest warranty is " + shortestWarrantyLaptop.getName() +
                    " (" + shortestWarrantyLaptop.getWarranty() + " months)");
    }

    /**
     * Inputs an integer from System.in with handling of InputMismatchException
     *
     * @param scanner Allows input from System.in
     * @return int
     */
    public static int inputInt(Scanner scanner) {
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException ex) {
            logger.error("An error has occurred", ex);
            System.out.println("Exception: " + ex);
        } finally {
            scanner.nextLine();
        }
        return input;
    }

    /**
     * Inputs a BigDecimal from System.in with handling of InputMismatchException
     *
     * @param scanner Allows input from System.in
     * @return BigDecimal
     */
    public static BigDecimal inputBigDecimal(Scanner scanner) {
        BigDecimal input = new BigDecimal(0);
        try {
            input = scanner.nextBigDecimal();
        } catch (InputMismatchException ex) {
            logger.error("An error has occurred", ex);
            System.out.println("Exception: " + ex);
            System.out.println("Value defaulted to 0.");
        } finally {
            scanner.nextLine();
        }
        return input;
    }

    /**
     * Requests input of parameters for Category
     *
     * @param scanner Allows input from System.in
     * @param n       Index of category that is being created
     * @return Object of Category class
     */
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

    /**
     * Requests input of parameters for Item
     *
     * @param scanner    Allows input from System.in
     * @param categories Category[], list of categories that can be selected
     * @param n          Index of item that is being created
     * @return Object of Item class
     */
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
            selectedCategory = inputInt(scanner);
        } while (selectedCategory < 1 || selectedCategory > AMOUNT_OF_CATEGORIES);
        Category category = categories[selectedCategory - 1];

        /*Input width*/
        System.out.print("Enter width: ");
        BigDecimal width = inputBigDecimal(scanner);

        /*Input height*/
        System.out.print("Enter height: ");
        BigDecimal height = inputBigDecimal(scanner);

        /*Input length*/
        System.out.print("Enter length: ");
        BigDecimal length = inputBigDecimal(scanner);

        /*Input production cost*/
        System.out.print("Enter production cost: ");
        BigDecimal productionCost = inputBigDecimal(scanner);

        /*Input selling price*/
        System.out.print("Enter selling price: ");
        BigDecimal sellingPrice = inputBigDecimal(scanner);

        /*Input discount*/
        System.out.print("Enter discount percentage: ");
        BigDecimal discount = inputBigDecimal(scanner);

        /*Check if edible*/
        System.out.println("Select item type: ");
        System.out.println("1 Edible");
        System.out.println("2 Laptop");
        System.out.println("3 Other");
        int selection;
        do {
            System.out.print("Item type: ");
            selection = inputInt(scanner);
        } while (selection < 1 || selection > 3);

        /*Select edible item*/
        if (selection == 1) {
            System.out.println("Select item: ");
            System.out.println("1 Fries");
            System.out.println("2 Gummy Bears");
            /*Repeat selection until valid*/
            int foodSelection;
            do {
                System.out.print("Food: ");
                foodSelection = inputInt(scanner);
            } while (foodSelection < 1 || foodSelection > 2);
            /*Input weight*/
            System.out.print("Input weight (kg): ");
            BigDecimal weight = inputBigDecimal(scanner);
            /*Create object of proper type*/
            Item returnItem;
            if (foodSelection == 1) {
                returnItem = new Fries(name, category, width, height, length, productionCost, sellingPrice, new Discount(discount), weight);
                /*Total kcal, price*/
                System.out.println("Total kcal: " + ((Fries) returnItem).totalKCAL());
                System.out.println("Total price: " + ((Fries) returnItem).calculatePrice());
            } else {
                returnItem = new GummyBears(name, category, width, height, length, productionCost, sellingPrice, new Discount(discount), weight);
                /*Total kcal, price*/
                System.out.println("Total kcal: " + ((GummyBears) returnItem).totalKCAL());
                System.out.println("Total price: " + ((GummyBears) returnItem).calculatePrice());
            }
            return returnItem;
        }
        /*Laptop selected*/
        else if (selection == 2) {
            System.out.print("Enter laptop warranty: ");
            int warranty = inputInt(scanner);
            return new Laptop(name, category, width, height, length, productionCost, sellingPrice, new Discount(discount), warranty);
        } else {
            return new Item(name, category, width, height, length, productionCost, sellingPrice, new Discount(discount));
        }
    }

    /**
     * Requests input of parameters for Factory
     *
     * @param scanner Allows input from System.in
     * @param items   Item[] list of articles that can be added to Factory
     * @param n       Index of factory that is being created
     * @return Object of Factory class
     */
    public static Factory createFactory(Scanner scanner, List<Item> items, int n) {
        /*Input name*/
        System.out.print("Enter " + (n + 1) + ". factory name: ");
        String name = scanner.nextLine();

        /*Input Address*/
        Address address = createAddress(scanner);

        /*Select items*/
        Set<Item> factoryItems = new HashSet<>();
        for (int i = 0; i < AMOUNT_OF_FACTORY_ITEMS; i++) {
            System.out.println("Select " + (i + 1) + ". item: ");
            for (Item item : items) {
                System.out.println(item.getName());
            }
            int selectedItem;
            do {
                System.out.print("Item: ");
                try {
                    selectedItem = inputInt(scanner);
                    for (Item factoryItem : factoryItems) {
                        if (factoryItem.equals(items.get(selectedItem - 1)))
                            throw new DuplicateArticleException("This item already exists in factory.");
                    }
                } catch (DuplicateArticleException ex) {
                    logger.error("An error has occurred", ex);
                    System.out.println(ex.getMessage());
                    selectedItem = 0;
                }
            } while (selectedItem < 1 || selectedItem > AMOUNT_OF_ITEMS);
            factoryItems.add(items.get(selectedItem - 1));
        }

        return new Factory(name, address, factoryItems);
    }

    /**
     * Requests input of parameters for Address
     *
     * @param scanner Allows input from System.in
     * @return Object of Address class
     */
    public static Address createAddress(Scanner scanner) {
        /*Input street*/
        System.out.print("Enter street: ");
        String street = scanner.nextLine();

        /*input house number*/
        System.out.print("Enter house number: ");
        String houseNumber = scanner.nextLine();

        /*Select city*/
        //TODO Output city options from enum
        boolean validInput = false;
        City selected = null;
        do {
            System.out.print("Select city " + Arrays.asList(City.values()) + ": ");
            try {
                selected = City.valueOf(scanner.nextLine().toUpperCase(Locale.ROOT));
                validInput = true;
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input!");
            }
        } while (!validInput);

        return new Address.Builder()
                .withStreet(street)
                .withHouseNumber(houseNumber)
                .withCity(selected)
                .build();
    }

    /**
     * Requests input of parameters for Store
     *
     * @param scanner Allows input from System.in
     * @param items   Item[] list of articles that can be added to Store
     * @param n       Index of store that is being created
     * @return Object of Store class
     */
    public static Store createStore(Scanner scanner, List<Item> items, int n) {
        /*Input name*/
        System.out.print("Enter " + (n + 1) + ". store name: ");
        String name = scanner.nextLine();

        /*Input web address*/
        System.out.print("Enter web address: ");
        String webAddress = scanner.nextLine();

        /*Select items*/
        Set<Item> storeItems = new HashSet<>();
        for (int i = 0; i < AMOUNT_OF_STORE_ITEMS; i++) {
            System.out.println("Select " + (i + 1) + ". item: ");
            for (int j = 0; j < AMOUNT_OF_ITEMS; j++) {
                System.out.println((j + 1) + " " + items.get(j).getName());
            }
            int selectedItem;
            do {
                System.out.print("Item: ");
                try {
                    selectedItem = inputInt(scanner);
                    /*Compare item [selectedItem-1] with all previous entries*/
                    for (Item item : storeItems) {
                        if (item.equals(items.get(selectedItem - 1)))
                            throw new DuplicateArticleException("This item already exists in store.");
                    }
                } catch (DuplicateArticleException ex) {
                    logger.error("An error has occurred", ex);
                    System.out.println(ex.getMessage());
                    selectedItem = 0;
                }
            } while (selectedItem < 1 || selectedItem > AMOUNT_OF_ITEMS);
            storeItems.add(items.get(selectedItem - 1));
        }

        return new Store(name, webAddress, storeItems);
    }

    /**
     * Requests input of parameters for TechnicalStore
     */
    public static TechnicalStore createTechnicalStore(Scanner scanner, List<Item> items) {
        System.out.print("Technical store name: ");
        String name = scanner.nextLine();
        System.out.println("Web address: ");
        String webAddress = scanner.nextLine();
        List<Technical> technicalItems = new ArrayList<>();
        for (Item i : items) {
            if (i instanceof Technical) technicalItems.add((Technical) i);
        }
        return new TechnicalStore<>(name, webAddress, technicalItems);
    }

    /**
     * Requests input of parameters for FoodStore
     */
    public static FoodStore createFoodStore(Scanner scanner, List<Item> items) {
        System.out.print("Food store name: ");
        String name = scanner.nextLine();
        System.out.println("Web address: ");
        String webAddress = scanner.nextLine();
        List<Edible> technicalItems = new ArrayList<>();
        for (Item i : items) {
            if (i instanceof Edible) technicalItems.add((Edible) i);
        }
        return new FoodStore(name, webAddress, technicalItems);
    }
}