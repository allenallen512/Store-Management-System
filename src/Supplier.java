public class Supplier {

    public int SupplierID;
    public String SupplierName;
    public String SupplierEmail;

    public Supplier() {
        this.SupplierID = 999;
        this.SupplierName = "new supplier";
        this.SupplierEmail = "newsupplier@gmail.com";
    }

    public Supplier(int ID, String name, String email) {
        this.SupplierID = ID;
        this.SupplierName = name;
        this.SupplierEmail = email;
    }

}
