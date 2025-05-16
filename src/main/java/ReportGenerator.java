import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class ReportGenerator {
    static class TaskRunnable implements Runnable {
        private final String path;
        private double totalCost;
        private int totalAmount;
        private int totalDiscountSum;
        private int totalLines;
        private Product mostExpensiveProduct;
        private double highestCostAfterDiscount;

        public TaskRunnable(String path) {
            this.path = path;
            this.totalCost = 0;
            this.totalAmount = 0;
            this.totalDiscountSum = 0;
            this.totalLines = 0;
            this.highestCostAfterDiscount = 0;
            this.mostExpensiveProduct = null;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                int productID;
                int amount;
                int discount;
                double price;

                while((line = reader.readLine()) != null){
                    int start = 0 , j = 0 ;

                    productID = Integer.parseInt(line.substring(0,1)) ;
                    productID--;
                    line = line.substring(2) ;

                    for (  ; line.charAt(j) != ',' ; j++ ) ;
                    amount = Integer.parseInt(line.substring(start, j));
                    totalAmount += amount ;

                    start = j + 1;
                    discount = Integer.parseInt(line.substring(start));

                    price = productCatalog[productID].price;

                    totalDiscountSum += amount*discount;
                    price *= (double)(100 - discount)/100;
                    totalCost += price;

                    if ( highestCostAfterDiscount < amount * price ){
                        highestCostAfterDiscount = amount * price ;
                        mostExpensiveProduct = productCatalog[productID] ;
                    }
                    totalLines++;
                }

                if ( path.equals("src\\main\\resources\\2021_order_details.txt"))
                    Thread.sleep(100);
                else if (path.equals("src\\main\\resources\\2022_order_details.txt"))
                    Thread.sleep(200);
                else if (path.equals("src\\main\\resources\\2023_order_details.txt"))
                    Thread.sleep(300);
                else
                    Thread.sleep(400);

                makeReport();

            } catch (IOException | InterruptedException e) {
                System.err.println("Error reading file" + e.getMessage());
            }
        }

        public void makeReport() {
            DecimalFormat df = new DecimalFormat("#.##");

            System.out.println("The work related to this file has been completed.\n" + path + "\n");
            System.out.println("total cost : " + df.format(totalCost) );
            System.out.println("total lines read : " + totalLines );
            System.out.println("total items bought : " + totalAmount );
            System.out.println("average discount : " + df.format((double)(totalDiscountSum) / totalAmount) );
            System.out.println("most expensive purchase after discount : " + df.format(highestCostAfterDiscount));
            System.out.println("details of the most expensive product : " + mostExpensiveProduct);
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    static class Product {
        private int productID;
        private String productName;
        private double price;

        public Product(int productID, String productName, double price) {
            this.productID = productID;
            this.productName = productName;
            this.price = price;
        }

        public int getProductID() { return productID; }

        public String getProductName() { return productName; }

        public double getPrice() {return price;}

        @Override
        public String toString() {
            return productID + " " + productName + " " + price ;
        }
    }
    static Product[] productCatalog = new Product[9];

    public static void loadProducts() throws IOException {
        String path = "src\\main\\resources\\Products.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int productID;
            String productName;
            double price;
            for (int i=0 ; (line = reader.readLine()) != null ; i++) {
                int start = 0 , j = 0 ;
                productID = Integer.parseInt(line.substring(0,1)) ;
                line = line.substring(2) ;
                for (  ; line.charAt(j) != ',' ; j++ ) ;
                productName = (line.substring(start, j));

                start = j + 1;
                price = Double.parseDouble(line.substring(start));

                productCatalog[i] = new Product(productID, productName, price);
            }
        } catch (IOException e) {
            System.err.println("Error reading file" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            loadProducts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread t1 = new Thread(new TaskRunnable("src\\main\\resources\\2021_order_details.txt"));
        Thread t2 = new Thread(new TaskRunnable("src\\main\\resources\\2022_order_details.txt"));
        Thread t3 = new Thread(new TaskRunnable("src\\main\\resources\\2023_order_details.txt"));
        Thread t4 = new Thread(new TaskRunnable("src\\main\\resources\\2024_order_details.txt"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}