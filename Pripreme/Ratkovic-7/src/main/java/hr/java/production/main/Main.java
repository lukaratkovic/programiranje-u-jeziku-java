package hr.java.production.main;

import hr.java.production.enums.City;
import hr.java.production.exception.DuplicateArticleException;
import hr.java.production.exception.DuplicateCategoryException;
import hr.java.production.genericsi.FoodStore;
import hr.java.production.genericsi.TechnicalStore;
import hr.java.production.model.*;
import hr.java.production.sort.ProductionSorter;
import hr.java.production.sort.VolumeSorter;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.InflaterOutputStream;

/**
 * Runs the program
 */
public class Main {
    private static final int AMOUNT_OF_ITEMS = 5;

    public static final String CATEGORY_FILE = "dat/categories.txt";
    private static final String ITEM_FILE = "dat/items.txt";
    private static final String ADDRESS_FILE = "dat/addresses.txt";
    private static final String TECHNICAL_STORE_FILE = "dat/technical_store.txt";
    private static final String FOOD_STORE_FILE = "dat/food_store.txt";
    private static final String STORE_FILE = "dat/stores.txt";
    private static final String FACTORIES_FILE = "dat/factories.txt";
    private static final String STORE_SERIALIZATION_FILE = "dat/serialized_stores.ser";
    private static final String FACTORY_SERIALIZATION_FILE = "dat/serialized_factories.ser";

    public static final int LINES_PER_CATEGORY = 3;
    public static final int LINES_PER_ITEM = 12;
    public static final int LINES_PER_ADDRESS = 4;
    private static final int LINES_PER_TECHNICAL_STORE = 4;
    private static final int LINES_PER_FOOD_STORE = 4;
    private static final int LINES_PER_STORE = 4;
    private static final int LINES_PER_FACTORY = 4;

    /**
     * Method called when program is run
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<Category, List<Item>> categoryItemMap = new HashMap<>();

        /*Category input from file*/
        List<Category> categories = loadCategories();

        /*Item input from file*/
        List<Item> items = loadItems(categories);

        aboveAveragePrice(items);

        List<Item> sortedList = items.stream().filter(i -> i.getDiscount().discountAmount().compareTo(new BigDecimal(0)) > 0)
                .collect(Collectors.toList());
        Optional<List<Item>> test = (sortedList.size() == 0) ? Optional.empty() : Optional.of(sortedList);

        List<Store> storesList = new ArrayList<>();

        List<Address> addresses = loadAddresses();

        TechnicalStore<Laptop> technicalStore = loadTechnicalStore(items);
        System.out.println(technicalStore.getName());

        //Sort w/Lambda
        storesList.add(technicalStore);
        Instant start = Instant.now();
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


        FoodStore<Edible> foodStore = loadFoodStore(items);

        storesList.add(foodStore);
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

        /*Load stores from file*/
        List<Store> stores = loadStores(items, addresses);

        //Sort with lambdas
        start = Instant.now();
        stores.stream().forEach(s -> {
            s.getItems().stream()
                    .sorted((i1, i2) -> {
                        if (i1.getVolume().compareTo(i2.getVolume()) > 0) return 1;
                        else if (i1.getVolume().compareTo(i2.getVolume()) < 0) return -1;
                        else return 0;
                    })
                    .collect(Collectors.toList());
        });
        end = Instant.now();
        System.out.println("Sorting with lambdas duration: " + Duration.between(start, end));

        //Sort without lambdas
        start = Instant.now();
        for (Store s : stores) {
            Collections.sort(new ArrayList<Item>(s.getItems()), new VolumeSorter<>());
        }
        end = Instant.now();
        System.out.println("Sorting without lambdas duration: " + Duration.between(start, end));

        /*Load factories from file*/
        List<Factory> factories = loadFactories(items, addresses);

        List<Store> allStores = new ArrayList<>();
        allStores.add(technicalStore);
        allStores.add(foodStore);
        stores.stream().forEach(s -> allStores.add(s));

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

        /*Serialize stores with 5 or more items*/
        stores.stream().forEach(s -> {
            if (s.getItems().size() >= 5) {
                try (ObjectOutputStream objectWriter =
                             new ObjectOutputStream(new FileOutputStream(STORE_SERIALIZATION_FILE))) {
                    objectWriter.writeObject(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        /*Serialize factories with 5 or more items*/
        factories.stream().forEach(f -> {
            if (f.getItems().size() >= 5) {
                try (ObjectOutputStream objectWriter =
                             new ObjectOutputStream(new FileOutputStream(FACTORY_SERIALIZATION_FILE))) {
                    objectWriter.writeObject(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static List<Factory> loadFactories(List<Item> items, List<Address> addresses) {
        List<Factory> factories = new ArrayList<>();

        /*Model: id, name, addressID, items*/
        Optional<Long> id = Optional.empty();
        Optional<String> name = Optional.empty();
        Optional<Address> address = Optional.empty();
        Set<Item> itemList = new HashSet<>();

        File factoryFile = new File(FACTORIES_FILE);
        try (BufferedReader factoryFileReader = new BufferedReader(new FileReader(factoryFile))) {
            int lineCounter = 0;
            String line;
            while ((line = factoryFileReader.readLine()) != null) {
                switch (lineCounter % LINES_PER_FACTORY) {
                    case 0:
                        id = Optional.of(Long.parseLong(line));
                        break;
                    case 1:
                        name = Optional.of(line);
                        break;
                    case 2:
                        String finalLine = line;
                        address = addresses.stream()
                                .filter(a -> a.getId().equals(Long.parseLong(finalLine)))
                                .findFirst();
                        break;
                    case 3:
                        Set<Item> finalItemList = itemList;
                        Arrays.asList(line.split(",")).stream().forEach(itemid -> {
                            finalItemList.add(items.stream().
                                    filter(i -> i.getId().equals(Long.parseLong(itemid)))
                                    .findFirst().get());
                        });
                        factories.add(new Factory(name.get(), address.get(), itemList, id.get()));
                        itemList = new HashSet<>();
                        break;
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return factories;
    }

    private static List<Store> loadStores(List<Item> items, List<Address> addresses) {
        List<Store> stores = new ArrayList<>();

        Optional<Long> id = Optional.empty();
        Optional<String> name = Optional.empty();
        Optional<String> webAddress = Optional.empty();
        Set<Item> itemList = new HashSet<>();

        File storeFile = new File(STORE_FILE);
        try (BufferedReader storeFileReader = new BufferedReader(new FileReader(storeFile))) {
            int lineCounter = 0;
            String line;
            while ((line = storeFileReader.readLine()) != null) {
                switch (lineCounter % LINES_PER_STORE) {
                    case 0:
                        id = Optional.of(Long.parseLong(line));
                        break;
                    case 1:
                        name = Optional.of(line);
                        break;
                    case 2:
                        webAddress = Optional.of(line);
                        break;
                    case 3:
                        Set<Item> finalItemList = itemList;
                        Arrays.asList(line.split(",")).stream().forEach(itemid -> {
                            finalItemList.add(items.stream().
                                    filter(i -> i.getId().equals(Long.parseLong(itemid)))
                                    .findFirst().get());
                        });
                        itemList = new HashSet<>();
                        stores.add(new Store(name.get(), webAddress.get(), itemList, id.get()));
                        break;
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stores;
    }

    /**
     * Loads a food store from FOOD_STORE_FILE
     *
     * @param items List of Item objects
     * @return Food store loaded from file
     */
    private static FoodStore<Edible> loadFoodStore(List<Item> items) {
        /*Model: id, name, webAdress, itemList*/
        Optional<Long> id = Optional.empty();
        Optional<String> name = Optional.empty();
        Optional<String> webAddress = Optional.empty();
        List<Edible> itemList = new ArrayList<>();

        File fstoreFile = new File(FOOD_STORE_FILE);
        try (BufferedReader fstoreFileReader = new BufferedReader(new FileReader(fstoreFile))) {
            int lineCounter = 0;
            String line;
            while ((line = fstoreFileReader.readLine()) != null) {
                switch (lineCounter % LINES_PER_FOOD_STORE) {
                    case 0:
                        id = Optional.of(Long.parseLong(line));
                        break;
                    case 1:
                        name = Optional.of(line);
                        break;
                    case 2:
                        webAddress = Optional.of(line);
                        break;
                    case 3:
                        Arrays.asList(line.split(",")).stream().forEach(itemid -> {
                            itemList.add((Edible) items.stream().
                                    filter(i -> i.getId().equals(Long.parseLong(itemid)))
                                    .findFirst().get());
                        });
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FoodStore<>(name.get(), webAddress.get(), itemList, id.get());
    }

    /**
     * Loads a technical store from TECHNICAL_STORE_FILE
     *
     * @param items List of Item objects
     * @return Technical Store loaded from file
     */
    private static TechnicalStore<Laptop> loadTechnicalStore(List<Item> items) {
        /*Model: id, name, webAddress, itemList*/
        Optional<Long> id = Optional.empty();
        Optional<String> name = Optional.empty();
        Optional<String> webAddress = Optional.empty();
        List<Laptop> itemList = new ArrayList<>();

        File tstoreFile = new File(TECHNICAL_STORE_FILE);
        try (BufferedReader tstoreFileReader = new BufferedReader(new FileReader(tstoreFile))) {
            int lineCounter = 0;
            String line;
            while ((line = tstoreFileReader.readLine()) != null) {
                switch (lineCounter % LINES_PER_TECHNICAL_STORE) {
                    case 0:
                        id = Optional.of(Long.parseLong(line));
                        break;
                    case 1:
                        name = Optional.of(line);
                        break;
                    case 2:
                        webAddress = Optional.of(line);
                        break;
                    case 3:
                        Arrays.asList(line.split(",")).stream().forEach(itemid -> {
                            itemList.add((Laptop) items.stream()
                                    .filter(i -> i.getId().equals(Long.parseLong(itemid)))
                                    .findFirst().get());
                        });
                        break;
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new TechnicalStore<>(name.get(), webAddress.get(), itemList, id.get());

    }

    /**
     * Loads a list of Address from ADDRESS_FILE
     *
     * @return List of Address objects
     */
    public static List<Address> loadAddresses() {
        List<Address> addresses = new ArrayList<>();
        /*Model: street, houseNumber, City*/

        Address.Builder addressBuilder = new Address.Builder();

        /*Open file*/
        File addressFile = new File(ADDRESS_FILE);
        try (BufferedReader addressFileReader = new BufferedReader(new FileReader(addressFile))) {
            int lineCounter = 0;
            String line;
            while ((line = addressFileReader.readLine()) != null) {
                switch (lineCounter % LINES_PER_ADDRESS) {
                    case 0:
                        addressBuilder.withId(Long.parseLong(line));
                    case 1:
                        addressBuilder.withStreet(line);
                        break;
                    case 2:
                        addressBuilder.withHouseNumber(line);
                        break;
                    case 3:
                        addressBuilder.withCity(switch (line) {
                            case "ZAGREB" -> City.ZAGREB;
                            case "DUGO SELO" -> City.DUGO_SELO;
                            default -> City.RIJEKA;
                        });
                        addresses.add(addressBuilder.build());
                        break;
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    /**
     * Loads a list of Items from ITEM_FILE
     *
     * @return List of Item objects
     */
    public static List<Item> loadItems(List<Category> categories) {
        /*Model: ID, name, CategoryID, type (Basic, Fries, GummyBears, Laptop), width, height, length, weight,
        productionCost, sellingCost, discount, warranty*/
        List<Item> items = new ArrayList<>();
        Optional<Long> id = Optional.empty();
        Optional<String> name = Optional.empty();
        Optional<Long> categoryID = Optional.empty();
        Optional<String> type = Optional.empty();
        Optional<BigDecimal> width = Optional.empty();
        Optional<BigDecimal> height = Optional.empty();
        Optional<BigDecimal> length = Optional.empty();
        Optional<BigDecimal> weight = Optional.empty();
        Optional<BigDecimal> productionCost = Optional.empty();
        Optional<BigDecimal> sellingPrice = Optional.empty();
        Optional<Discount> discount = Optional.empty();
        Optional<Integer> warranty = Optional.empty();

        /*Open file*/
        File itemsFile = new File(ITEM_FILE);
        try (BufferedReader itemsFileReader = new BufferedReader(new FileReader(itemsFile))) {
            int lineCounter = 0;
            String line;
            while ((line = itemsFileReader.readLine()) != null) {
                switch (lineCounter % LINES_PER_ITEM) {
                    case 0:
                        id = Optional.of(Long.parseLong(line));
                        break;
                    case 1:
                        name = Optional.of(line);
                        break;
                    case 2:
                        categoryID = Optional.of(Long.parseLong(line));
                        break;
                    case 3:
                        type = Optional.of(line);
                        break;
                    case 4:
                        width = Optional.of(new BigDecimal(line));
                        break;
                    case 5:
                        height = Optional.of(new BigDecimal(line));
                        break;
                    case 6:
                        length = Optional.of(new BigDecimal(line));
                        break;
                    case 7:
                        weight = Optional.of(new BigDecimal(line));
                        break;
                    case 8:
                        productionCost = Optional.of(new BigDecimal(line));
                        break;
                    case 9:
                        sellingPrice = Optional.of(new BigDecimal(line));
                        break;
                    case 10:
                        discount = Optional.of(new Discount(new BigDecimal(line)));
                        break;
                    case 11:
                        warranty = Optional.of(Integer.parseInt(line));
                        Optional<Long> finalCategoryID = categoryID;
                        Optional<Category> category = categories.stream()
                                .filter(c -> c.getId().equals(finalCategoryID.get()))
                                .findFirst();
                        items.add(switch (type.get()) {
                            case "GummyBears" -> new GummyBears(name.get(), category.get(), width.get(), height.get(),
                                    length.get(), productionCost.get(), sellingPrice.get(), discount.get(),
                                    weight.get(), id.get());
                            case "Fries" -> new Fries(name.get(), category.get(), width.get(), height.get(),
                                    length.get(), productionCost.get(), sellingPrice.get(), discount.get(),
                                    weight.get(), id.get());
                            case "Laptop" -> new Laptop(name.get(), category.get(), width.get(), height.get(),
                                    length.get(), productionCost.get(), sellingPrice.get(), discount.get(),
                                    warranty.get(), id.get());
                            default -> new Item(name.get(), category.get(), width.get(), height.get(),
                                    length.get(), productionCost.get(), sellingPrice.get(), discount.get(), id.get());
                        });
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Loads a list of categories from CATEGORY_FILE
     *
     * @return List of Category objects
     */
    public static List<Category> loadCategories() {
        List<Category> categories = new ArrayList<>();
        Optional<String> name = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<Long> id = Optional.empty();

        File categoriesFile = new File(CATEGORY_FILE);
        try (BufferedReader categoriesFileReader = new BufferedReader(new FileReader(categoriesFile))) {
            int lineCounter = 0;
            String line;
            while ((line = categoriesFileReader.readLine()) != null) {
                switch (lineCounter % LINES_PER_CATEGORY) {
                    case 0:
                        id = Optional.of(Long.parseLong(line));
                        break;
                    case 1:
                        name = Optional.of(line);
                        break;
                    case 2:
                        description = Optional.of(line);
                        categories.add(new Category(name.get(), description.get(), id.get()));
                        break;
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
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
    private static void largestVolumeFactory(List<Factory> factories) {
        /*Set first factory as one with largest volume*/
        Factory largestVolumeFactory = factories.get(0);
        /*Find largest volume item of FIRST factory*/
        Item largestVolumeItem = largestVolumeFactory.getItems().stream().toList().get(0);
        BigDecimal largestVolume = largestVolumeItem.getVolume();
        for (Factory f : factories) {
            for (Item i : f.getItems()) {
                if (i.getVolume().compareTo(largestVolume) > 0) {
                    largestVolumeFactory = f;
                    largestVolumeItem = i;
                    largestVolume = i.getVolume();
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
    public static void cheapestArticleStore(List<Store> stores) {
        /*Set first store as the one with cheapest item*/
        Store cheapestArticleStore = stores.get(0);
        /*Find cheapest item of FIRST store*/
        Item cheapestArticle = cheapestArticleStore.getItems().stream().toList().get(0);
        BigDecimal cheapestArticlePrice = cheapestArticle.getSellingPrice();

        for (Store s : stores) {
            for (Item i : s.getItems()) {
                if (i.getSellingPrice().compareTo(cheapestArticlePrice) < 0) {
                    cheapestArticleStore = s;
                    cheapestArticle = i;
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
        for (Item i : items) {

        }
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
            System.out.println("Exception: " + ex);
            System.out.println("Value defaulted to 0.");
        } finally {
            scanner.nextLine();
        }
        return input;
    }
}