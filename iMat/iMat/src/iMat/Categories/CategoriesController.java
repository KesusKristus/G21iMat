package iMat.Categories;

import se.chalmers.cse.dat216.project.*;

import java.util.*;

public class CategoriesController {

    private final IMatDataHandler idh = IMatDataHandler.getInstance();

    private List<ShoppingItem> allShoppingItemList = new ArrayList<>();

    private List<ShoppingItem> dryckList = new ArrayList<>();

    private List<ShoppingItem> fruktBärList = new ArrayList<>();

    private List<ShoppingItem> grönsakerList = new ArrayList<>();

    private List<ShoppingItem> köttFiskList = new ArrayList<>();

    private List<ShoppingItem> mejeriList = new ArrayList<>();

    private List<ShoppingItem> potatisRisList = new ArrayList<>();

    private List<ShoppingItem> skafferiList = new ArrayList<>();

    private List<ShoppingItem> sötsakerSnacksList = new ArrayList<>();

    public enum Categories {
        DRYCK,
        FRUKT,
        GRÖNT,
        KÖTT,
        MEJERI,
        POTATIS,
        SKAFFERI,
        SÖTT
    }

    public CategoriesController() {

        //Create shoppingItems for all products
        for (Product prod : idh.getProducts()) {
            allShoppingItemList.add(new ShoppingItem(prod, 0d));
        }

        //Create lists of all categories for easy access
        dryckList = getShoppingItems(Categories.DRYCK);

        fruktBärList = getShoppingItems(Categories.FRUKT);

        grönsakerList = getShoppingItems(Categories.GRÖNT);

        köttFiskList = getShoppingItems(Categories.KÖTT);

        mejeriList = getShoppingItems(Categories.MEJERI);

        potatisRisList = getShoppingItems(Categories.POTATIS);

        skafferiList = getShoppingItems(Categories.SKAFFERI);

        sötsakerSnacksList = getShoppingItems(Categories.SÖTT);
    }

    private void sortShoppingItemList(List<ShoppingItem> sIList) {

        Collections.sort(sIList, new Comparator<ShoppingItem>() {
            @Override
            public int compare(ShoppingItem s1, ShoppingItem s2) {
                return s1.getProduct().getName().compareTo(s2.getProduct().getName());
            }
        });
    }

    //Is only used to set up the lists of ShoppingItems per category - returns all ShoppingItems within a category
    private List<ShoppingItem> getShoppingItems(Categories category) {

        List<ShoppingItem> returnList = new ArrayList<>();

        ProductCategory itemCategory;

        switch (category) {
            case DRYCK:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.COLD_DRINKS) || itemCategory.equals(ProductCategory.HOT_DRINKS)) {
                        returnList.add(sI);
                    }
                }
                break;

            case FRUKT:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.FRUIT) || itemCategory.equals(ProductCategory.CITRUS_FRUIT) ||
                            itemCategory.equals(ProductCategory.EXOTIC_FRUIT) || itemCategory.equals(ProductCategory.BERRY) ||
                            itemCategory.equals(ProductCategory.MELONS)) {
                        returnList.add(sI);
                    }
                }
                break;

            case GRÖNT:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.VEGETABLE_FRUIT) || itemCategory.equals(ProductCategory.POD) ||
                            itemCategory.equals(ProductCategory.ROOT_VEGETABLE) || itemCategory.equals(ProductCategory.CABBAGE)) {
                        returnList.add(sI);
                    }
                }
                break;

            case KÖTT:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.MEAT) || itemCategory.equals(ProductCategory.FISH)) {
                        returnList.add(sI);
                    }
                }
                break;

            case MEJERI:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.DAIRIES)) {
                        returnList.add(sI);
                    }
                }
                break;

            case POTATIS:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.POTATO_RICE)) {
                        returnList.add(sI);
                    }
                }
                break;

            case SKAFFERI:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.FLOUR_SUGAR_SALT) || itemCategory.equals(ProductCategory.HERB) ||
                            itemCategory.equals(ProductCategory.PASTA) || itemCategory.equals(ProductCategory.BREAD)) {
                        returnList.add(sI);
                    }
                }
                break;

            case SÖTT:

                for (ShoppingItem sI : allShoppingItemList) {
                    itemCategory = sI.getProduct().getCategory();

                    if (itemCategory.equals(ProductCategory.SWEET) || itemCategory.equals(ProductCategory.NUTS_AND_SEEDS)) {
                        returnList.add(sI);
                    }
                }
                break;
        }

        sortShoppingItemList(returnList);

        return returnList;
    }

    //Use this when searching products!
    public List<ShoppingItem> searchShoppingItems(String searchTerm) {

        //Used when searching for a specific product - Uses the backend search method and then returns all ShoppingItems
        // that corresponds to the products that the findProducts method returns

        List<ShoppingItem> returnList = new ArrayList<>();

        List<Product> foundProducts = idh.findProducts(searchTerm);

        if (foundProducts != null) {
            for (Product prod : foundProducts) {
                returnList.add(findShoppingItem(prod.getProductId()));
            }
        }

        return returnList;
    }

    //Finds and returns a ShoppingItem from the id number of a product
    public ShoppingItem findShoppingItem(int idNumber) {

        for (ShoppingItem sI : allShoppingItemList) {
            if (sI.getProduct().getProductId() == idNumber)
                return sI;
        }
        return null;
    }


    //Get all products (ShoppingItems)
    public List<ShoppingItem> getAllShoppingItemList() {
        return allShoppingItemList;
    }

    //Get for the ShoppingItems from a specific category
    public List<ShoppingItem> getCategoryList(Categories category) {

        switch (category) {
            case DRYCK:
                return dryckList;

            case FRUKT:
                return fruktBärList;

            case GRÖNT:
                return grönsakerList;

            case KÖTT:
                return köttFiskList;

            case MEJERI:
                return mejeriList;

            case POTATIS:
                return potatisRisList;

            case SKAFFERI:
                return skafferiList;

            case SÖTT:
                return sötsakerSnacksList;
        }
        //Should never happen considering that all possible categories from the Categories enum is covered
        return null;
    }

}
