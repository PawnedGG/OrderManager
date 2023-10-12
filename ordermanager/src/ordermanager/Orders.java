/*
*Here we create the class "Orders" to use in our algorith.
*/
package ordermanager;

public class Orders{
    private String appID;
    private int orderID;
    private String date;
    private String clientName;
    private String itemName;
    private int quantity;
    private double netPrice;
    private double taxRate;
    
    //Constructors
    public Orders(){
    }

    public Orders(String appID, int orderID, String date, String clientName, 
                    String itemName, int quantity,double netPrice, double taxRate) {
        this.appID = appID;
        this.orderID = orderID;
        this.date = date;
        this.clientName = clientName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.netPrice = netPrice;
        this.taxRate = taxRate;
    }

    //Getters/Setters
    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    //Override "toString" so we can have the desired format of data in our files.
    @Override
    public String toString() {
        return appID + ";" + orderID + ";" + date + ";" + clientName + ";" + itemName + ";" + quantity + ";" + netPrice + ";" + taxRate;
    }

    //Method to calculate total cost(tax included) used in our stats window.
    public double getTotalCost(double netPrice, double taxRate){
        return (netPrice + (netPrice * taxRate));
    }
}    