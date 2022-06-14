package student.onlineretailer.service;

import java.util.Map;

public interface CartService {
    void addItemToCart(int id, int quantity);
    void removeItemFromCart(int id);
    Map<Integer, Integer> getAllItemsInCart();
    double calculateCartCost();
    String getContactEmail();

    Double getSalesTaxRate();


    double calculateSalesTax();
    double calculateDeliveryCharge();

}
