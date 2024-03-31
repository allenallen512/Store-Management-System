public class  Products{
    public int productID;
    public String productName;
    public double productPrice;

    public int supplierID;
    public String supplierName;

    public Products() {
        this.productID = 999;
        this.productName = "no name product";
        this.productPrice = 99.99;
        this.supplierID = 999;
        this.supplierName = "no supplier";
    }

    public Products(int id, String name, double price, int supplier, String supplierName) {
        this.productID = id;
        this.productName = name;
        this.productPrice = price;
        this.supplierID = supplier;
        this.supplierName = supplierName;
    }
}
