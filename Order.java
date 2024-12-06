//Written by Dylan Lesesne

public class Order implements Comparable<Order> {
    private String customer;
    private String foodOrder;
    private int cookingTime;
    private int arrivalTime;
    private int cookingTimeLeft;

    // Default constructor
    public Order() {
        this.customer = "none";
        this.foodOrder = "none";
        this.cookingTime = 1;
        this.arrivalTime = 0;
        this.cookingTimeLeft = this.cookingTime;
    }

    // Parameterized constructor
    public Order(String customer, String foodOrder, int cookingTime, int arrivalTime) {
        this.customer = (customer != null) ? customer : "none";
        this.foodOrder = (foodOrder != null) ? foodOrder : "none";
        this.cookingTime = (cookingTime > 0) ? cookingTime : 1;
        this.arrivalTime = (arrivalTime >= 0) ? arrivalTime : 0;
        this.cookingTimeLeft = this.cookingTime;
    }

    // Accessors
    public String getCustomer() {
        return customer;
    }

    public String getFoodOrder() {
        return foodOrder;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCookingTimeLeft() {
        return cookingTimeLeft;
    }

    // Mutators
    public void setCustomer(String customer) {
        if (customer != null) this.customer = customer;
    }

    public void setFoodOrder(String foodOrder) {
        if (foodOrder != null) this.foodOrder = foodOrder;
    }

    public void setCookingTime(int cookingTime) {
        if (cookingTime > 0) this.cookingTime = cookingTime;
    }

    public void setArrivalTime(int arrivalTime) {
        if (arrivalTime >= 0) this.arrivalTime = arrivalTime;
    }

    public void setCookingTimeLeft(int cookingTimeLeft) {
        if (cookingTimeLeft >= 0) this.cookingTimeLeft = cookingTimeLeft;
    }

    // cookForOneMinute method
    public void cookForOneMinute() {
        if (cookingTimeLeft > 0) cookingTimeLeft--;
    }

    // isDone method
    public boolean isDone() {
        return cookingTimeLeft == 0;
    }

    // compareTo method
    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.cookingTime, other.cookingTime);
    }

    // toString method
    @Override
    public String toString() {
        return "Customer: " + customer + ", Order: " + foodOrder + ", Cooking Time Left: " + cookingTimeLeft;
    }
}
