import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;

class Shopping {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: your port /online_shopping", "root", "enter your password")) {

            S1 s = new S1(con);
            s.New_old_user(); 
        }
    }
}

class Node {
    Product data;
    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }

    Node next;

    public Node(Product data ) {
        this.data = data;
     
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

class LinkedList {
    Node head;

    public LinkedList() {
        this.head = null;
    }

    public void add(Product product) {
        Node newNode = new Node(product);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    public Product find(String productType, String productColor, String productSize) {
        Node current = head;
        while (current != null) {
            if (current.data.productType.equals(productType) &&
                current.data.productColor.equals(productColor) &&
                current.data.productSize.equals(productSize)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    } 

    public void updateQuantity(String productType, String productColor, String productSize, int quantity) {
        Node current = head;
        while (current != null) {
            if (current.data.productType.equals(productType) &&
                current.data.productColor.equals(productColor) &&
                current.data.productSize.equals(productSize)) {
                current.data.productQuantity -= quantity;
                break;
            }
            current = current.next;
        }
    }
}

class Product {
    int userId;
    String productType;
    String productColor;
    String productSize;
    String productBrand;
    int productQuantity;
    double productPrice;

    public Product(int userId, String productType, String productColor, String productSize, String productBrand, int productQuantity, double productPrice) {
        this.userId = userId;
        this.productType = productType;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productBrand = productBrand;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "userId=" + userId +
                ", productType='" + productType + '\'' +
                ", productColor='" + productColor + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productBrand='" + productBrand + '\'' +
                ", productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                '}';
    }
}

class S1 {

    String f_name;
    String l_name;
    int age;
    long pin;
    String email;
    long phone;
    String password;
    String address;
    String role;
    Connection con;
    LinkedList productList;
    Scanner sc = new Scanner(System.in);

    public S1(Connection con) {
        this.con = con;
        this.productList = new LinkedList();
    }

    public void New_old_user() throws SQLException {
        System.out.println();
        System.out.println("-------------------------------------------------------------------");
        System.out.println("|             Do you already have an account? (yes/no):           |");
        System.out.println("-------------------------------------------------------------------");
        String response = sc.nextLine().toLowerCase();
        
        if (response.equals("yes")) {
            login();
        } else if (response.equals("no")) {
            setDetails();
        } else {
            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            New_old_user(); 
        }
    }

    public void login() throws SQLException {
        System.out.println("-------------------------------------------------------------------");
        System.out.print("Enter Your Email Address: ");
        email = sc.nextLine().trim();
        System.out.print("Enter Your Password: ");
        password = sc.nextLine().trim();
    
        String query = "SELECT user_id, role FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    role = rs.getString("role");
                    System.out.println("Login successful. Your user ID is: " + userId);
                    System.out.println("Your role is: " + role);
                    buyyer_or_seller(userId);
                } else {
                    System.out.println("Invalid email or password. Please try again.");
                    login(); // Retry
                }
            }
        }
    }    

    public void setDetails() throws SQLException {

    
        System.out.println("-------------------------------------------------------------------");
        System.out.println("========================== REGISTRATION ===========================");
        System.out.println("-------------------------------------------------------------------"); 
        System.out.println();
        System.out.print("Enter Your First Name: ");
        f_name = sc.nextLine();
        System.out.print("Enter Your Last Name: ");
        l_name = sc.nextLine();
        
        System.out.print("Enter Email Address: ");
        while (true) {
            email = sc.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Enter Valid Email Address!");
            }
        }
    
        System.out.print("Enter Your Phone Number: ");
        while (true) {
            phone = sc.nextLong();
            sc.nextLine(); 
            if (isValidPhone(phone)) {
                break;
            } else {
                System.out.println("----- Invalid phone number. Please enter a 10-digit phone number. ------");
            }
        }
    
        System.out.print("Enter Age: ");
        while (true) {
            age = sc.nextInt();
            sc.nextLine(); 
            if (age >= 10 && age <= 100) {
                break;
            } else {
                System.out.println("-------------------------- Enter Valid Age! ----------------------------");
            }
        }
    
        System.out.print("Enter Your Pincode: ");
        while (true) {
            pin = sc.nextLong();
            sc.nextLine(); 
            if (isValidPincode(pin)) {
                break;
            } else {
                System.out.println("----------- Invalid pincode. Please enter a 6-digit pincode. -----------");
            }
        }
    
        System.out.print("Enter Address: ");
        String address = sc.nextLine(); 
    
        System.out.print("Enter Password: ");
        while (true) {
            password = sc.nextLine();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("----  Password must be at least 8 characters long, include uppercase, lowercase letters, and special characters. -----");
            }
        }
    
    
        System.out.println();
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Are you a seller or buyer? (seller/buyer): ");
        System.out.println("-------------------------------------------------------------------");
        role = sc.nextLine().toLowerCase();
        while (!role.equals("seller") && !role.equals("buyer")) {
            System.out.println("----- Invalid choice. Please enter 'seller' or 'buyer'. -----");
            role = sc.nextLine().toLowerCase();
        }
    
        
        String query = "INSERT INTO users (first_name, last_name, email, phone_no, password, pin, address, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, f_name);
            pst.setString(2, l_name);
            pst.setString(3, email);
            pst.setLong(4, phone);
            pst.setString(5, password);
            pst.setLong(6, pin);
            pst.setString(7, address);
            pst.setString(8, role); 
    
            int r = pst.executeUpdate();
    
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    System.out.println("============================ WELCOME ==============================");
                    System.out.println("Your user ID is: " + userId);
                    buyyer_or_seller(userId);
                }
            }
        }
    }    

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    private boolean isValidPhone(long phone) {
        return phone >= 1000000000L && phone <= 9999999999L;
    }

    private boolean isValidPincode(long pin) {
        return pin >= 100000 && pin <= 999999;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasSpecialChar = password.matches(".*[!~@#$%^&*].*");
        return hasUpperCase && hasLowerCase && hasSpecialChar;
    }

    public void buyyer_or_seller(int userId) throws SQLException {
        if ("seller".equals(role)) {
            setProductDetails(userId);
        } else if ("buyer".equals(role)) {
            Buyer(userId);
        } else {
            System.out.println("Invalid role in the system.");
        }
    }

  public void setProductDetails(int userId) throws SQLException {
    System.out.println();
    System.out.println("-------------------------------------------------------------------");
    System.out.println("| ======================= ADD PRODUCT DETAILS ==================== |");
    System.out.println("-------------------------------------------------------------------");

    boolean addMore = true;
    while (addMore) {
        int productTypeChoice = -1;
        boolean validProductTypeInput = false;


        while (!validProductTypeInput) {
            System.out.println();
            System.out.println("Add Product Types you want to sell:");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("1. Tshirt for female");
            System.out.println("2. Tshirt for male");
            System.out.println("3. Shirt for female");
            System.out.println("4. Shirt for male");
            System.out.println("5. Jeans for female");
            System.out.println("6. Jeans for male");
            System.out.println("7. Female skirt");
            System.out.println("8. Saree for female");
            System.out.println("9. Kurta for male");
            System.out.println("10. Kurta for female");
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Choose a Product Type by entering the number: ");
            

            if (sc.hasNextInt()) {
                productTypeChoice = sc.nextInt();
                sc.nextLine(); 

                if (productTypeChoice >= 1 && productTypeChoice <= 10) {
                    validProductTypeInput = true; 
                } else {
                    System.out.println("***************************************************************");
                    System.out.println("*          Invalid number. Please choose a valid option       *");
                    System.out.println("***************************************************************");
                }
            } else {
                    System.out.println("***************************************************************");
                    System.out.println("*                Please enter a valid number                  *");
                    System.out.println("***************************************************************");
                    sc.next(); 
            }
        }

        String productType = "";
        switch (productTypeChoice) {
            case 1:
                productType = "tshirt for female";
                break;
            case 2:
                productType = "tshirt for male";
                break;
            case 3:
                productType = "shirt for female";
                break;
            case 4:
                productType = "shirt for male";
                break;
            case 5:
                productType = "jeans for female";
                break;
            case 6:
                productType = "jeans for male";
                break;
            case 7:
                productType = "female skirt";
                break;
            case 8:
                productType = "saree for female";
                break;
            case 9:
                productType = "kurta for male";
                break;
            case 10:
                productType = "kurta for female";
                break;
            default:
                break;
        }

        int colorChoice = -1;
        boolean validColorInput = false;

        while (!validColorInput) {
            System.out.println();
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Choose Colors you want to sell:");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("1. Red");
            System.out.println("2. Pink");
            System.out.println("3. Beige");
            System.out.println("4. Black");
            System.out.println("5. Blue");
            System.out.println("6. Brown");
            System.out.println("7. Green");
            System.out.println("8. Grey");
            System.out.println("9. Maroon");
            System.out.println("10. Orange");
            System.out.println("11. Yellow");
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Choose a Product Color by entering the number: ");
            
            if (sc.hasNextInt()) {
                colorChoice = sc.nextInt();
                sc.nextLine(); 

                if (colorChoice >= 1 && colorChoice <= 11) {
                    validColorInput = true; 
                } else {
                    System.out.println("***************************************************************");
                    System.out.println("*          Invalid number. Please choose a valid option       *");
                    System.out.println("***************************************************************");
                }
            } else {
                System.out.println("***************************************************************");
                System.out.println("*                Please enter a valid number                  *");
                System.out.println("***************************************************************");
                sc.next(); 
            }
        }

        String productColor = "";
        switch (colorChoice) {
            case 1:
                productColor = "red";
                break;
            case 2:
                productColor = "pink";
                break;
            case 3:
                productColor = "beige";
                break;
            case 4:
                productColor = "black";
                break;
            case 5:
                productColor = "blue";
                break;
            case 6:
                productColor = "brown";
                break;
            case 7:
                productColor = "green";
                break;
            case 8:
                productColor = "grey";
                break;
            case 9:
                productColor = "maroon";
                break;
            case 10:
                productColor = "orange";
                break;
            case 11:
                productColor = "yellow";
                break;
            default:
                break;
        }

        String productSize = "";
        boolean validSize = false;
        while (!validSize) {
            System.out.println();
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Enter Product Size (xxs, xs, s, m, l, xl, xxl): ");
            System.out.println();
            productSize = sc.nextLine().toLowerCase();

            if (Arrays.asList("xxs", "xs", "s", "m", "l", "xl", "xxl").contains(productSize)) {
                validSize = true;
            } else {
                System.out.println("Invalid size. Please enter one of the following: xxs, xs, s, m, l, xl, xxl.");
            }
        }
        
        System.out.println("-------------------------------------------------------------------");
        System.out.print("Enter Product Brand: ");
        String productBrand = sc.nextLine();
        System.out.println();

        int productQuantity = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                System.out.println("-------------------------------------------------------------------");
                System.out.print("Enter Product Quantity: ");
                productQuantity = sc.nextInt();
                sc.nextLine();  // Consume newline left-over
                validInput = true; // Input is valid, exit the loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the product quantity.");
                sc.nextLine();  // Clear the invalid input
            }
        }
        
        System.out.println();
         

        System.out.println("-------------------------------------------------------------------");
        System.out.print("Enter Product Price: ");
        double productPrice = sc.nextDouble();
        sc.nextLine();  
        System.out.println();

        Product newProduct = new Product(userId, productType, productColor, productSize, productBrand, productQuantity, productPrice);
        productList.add(newProduct);

        String query = "INSERT INTO product (user_id, product_type, p_color, p_size, p_brand, product_quantity, product_price) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, userId);
            pst.setString(2, productType);
            pst.setString(3, productColor);
            pst.setString(4, productSize);
            pst.setString(5, productBrand);
            pst.setInt(6, productQuantity);
            pst.setDouble(7, productPrice);

            int r = pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    int productId = rs.getInt(1);
                    System.out.println();
                    System.out.println("===================== Product added successfully =====================");
                    System.out.println(" Product ID: " + productId );
                }
            }
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.print("Do you want to add more products? (yes/no): ");
        addMore = sc.nextLine().equalsIgnoreCase("yes");

        System.out.println("Current product list:");
        productList.display();
    }
}

    
public void Buyer(int userId) throws SQLException {
    System.out.println("===================================================================");
    while (true) {
        String productType = "";
        String productColor = "";
        String productSize = "";
        int productQuantity = 0;

        while (true) {
            System.out.println("===================== Available Product Types: ====================");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("1. Tshirt for female");
            System.out.println("2. Tshirt for male");
            System.out.println("3. Shirt for female");
            System.out.println("4. Shirt for male");
            System.out.println("5. Jeans for female");
            System.out.println("6. Jeans for male");
            System.out.println("7. Female skirt");
            System.out.println("8. Saree for female");
            System.out.println("9. Kurta for male");
            System.out.println("10. Kurta for female");
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Choose a Product Type by entering the number: ");
            System.out.println();
            
            if (sc.hasNextInt()) {
                int productTypeChoice = sc.nextInt();
                sc.nextLine();  

                switch (productTypeChoice) {
                    case 1: productType = "tshirt for female"; break;
                    case 2: productType = "tshirt for male"; break;
                    case 3: productType = "shirt for female"; break;
                    case 4: productType = "shirt for male"; break;
                    case 5: productType = "jeans for female"; break;
                    case 6: productType = "jeans for male"; break;
                    case 7: productType = "female skirt"; break;
                    case 8: productType = "saree for female"; break;
                    case 9: productType = "kurta for male"; break;
                    case 10: productType = "kurta for female"; break;
                    default:
                    System.out.println("***************************************************************");
                    System.out.println("*          Invalid number. Please choose a valid option       *");
                    System.out.println("***************************************************************");
                        continue;
                }

                String query = "SELECT COUNT(*) FROM product WHERE product_type = ?";
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, productType);
                    try (ResultSet rs = pst.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) == 0) {
                            System.out.println("-------------------------------------------------------------------");
                            System.out.println(productType + " is not available. Do you want to choose another product type? (yes/no)");
                            String response = sc.nextLine().toLowerCase();
                            if (response.equals("yes")) {
                                continue;
                            } else {
                                System.out.println("========================================================================");
                                System.out.println("--------------- Thank you for visiting. Have a great day! --------------");
                                System.out.println("========================================================================");
                                return;
                            }
                        } else {
                            break;
                        }
                    }
                }
            } else {
                System.out.println("*******************************************************************");
                System.out.println("*                 Please enter a valid number                     *");
                System.out.println("*******************************************************************");
                sc.next(); 
            }
        }

        while (true) {
            System.out.println("========================= Available Colors: =======================");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("1. Red");
            System.out.println("2. Pink");
            System.out.println("3. Beige");
            System.out.println("4. Black");
            System.out.println("5. Blue");
            System.out.println("6. Brown");
            System.out.println("7. Green");
            System.out.println("8. Grey");
            System.out.println("9. Maroon");
            System.out.println("10. Orange");
            System.out.println("11. Yellow");
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Choose a Product Color by entering the number: ");
            System.out.println();
            
            if (sc.hasNextInt()) {
                int colorChoice = sc.nextInt();
                sc.nextLine(); 
                switch (colorChoice) {
                    case 1: productColor = "red"; break;
                    case 2: productColor = "pink"; break;
                    case 3: productColor = "beige"; break;
                    case 4: productColor = "black"; break;
                    case 5: productColor = "blue"; break;
                    case 6: productColor = "brown"; break;
                    case 7: productColor = "green"; break;
                    case 8: productColor = "grey"; break;
                    case 9: productColor = "maroon"; break;
                    case 10: productColor = "orange"; break;
                    case 11: productColor = "yellow"; break;
                    default:
                    System.out.println("***************************************************************");
                    System.out.println("*          Invalid number. Please choose a valid option       *");
                    System.out.println("***************************************************************");
                        continue;
                }

                String query = "SELECT COUNT(*) FROM product WHERE product_type = ? AND p_color = ?";
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, productType);
                    pst.setString(2, productColor);
                    try (ResultSet rs = pst.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) == 0) {
                            System.out.println("-------------------------------------------------------------------");
                            System.out.println("Color " + productColor + " is not available for " + productType + ". Do you want to choose another color? (yes/no)");
                            String response = sc.nextLine().toLowerCase();
                            if (response.equals("yes")) {
                                continue;
                            } else {
                                System.out.println("========================================================================");
                                System.out.println("--------------- Thank you for visiting. Have a great day! --------------");
                                System.out.println("========================================================================");
                                return;
                            }
                        } else {
                            break;
                        }
                    }
                }
            } else {
                System.out.println("************************************************************************");
                System.out.println("*                    Please enter a valid number.                      *");
                System.out.println("************************************************************************");
                sc.next(); 
            }
        }

        while (true) {
            System.out.println();
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Enter Product Size (xxs, xs, s, m, l, xl, xxl): ");
            productSize = sc.nextLine().toLowerCase();
            if (!Arrays.asList("xxs", "xs", "s", "m", "l", "xl", "xxl").contains(productSize)) {
                System.out.println("Invalid size. Please choose from the listed options.");
                continue;
            }

            String query = "SELECT COUNT(*) FROM product WHERE product_type = ? AND p_color = ? AND p_size = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, productType);
                pst.setString(2, productColor);
                pst.setString(3, productSize);
                try (ResultSet rs = pst.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) == 0) {
                        System.out.println("-------------------------------------------------------------------");
                        System.out.println("Size " + productSize + " is not available for " + productType + " in " + productColor + ". Do you want to choose another size? (yes/no)");
                        String response = sc.nextLine().toLowerCase();
                        if (response.equals("yes")) {
                            continue;
                        } else {
                            System.out.println("========================================================================");
                            System.out.println("--------------- Thank you for visiting. Have a great day! --------------");
                            System.out.println("========================================================================");
                            return;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        while (true) {
            System.out.println();
            System.out.println("-------------------------------------------------------------------");
            System.out.print("Enter Product Quantity: ");
            if (sc.hasNextInt()) {
                productQuantity = sc.nextInt();
                sc.nextLine(); 
                if (productQuantity <= 0) {
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                    
                }

                String query = "SELECT product_quantity FROM product WHERE product_type = ? AND p_color = ? AND p_size = ?";
                int availableQuantity = 0;
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, productType);
                    pst.setString(2, productColor);
                    pst.setString(3, productSize);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            availableQuantity = rs.getInt("product_quantity");
                        }
                    }
                }

                if (productQuantity > availableQuantity) {
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Only " + availableQuantity + " items are available. Do you want to purchase the available quantity? (yes/no)");
                    String response = sc.nextLine().toLowerCase();
                    if (response.equals("yes")) {
                        productQuantity = availableQuantity;
                        break;
                    } 
                    else if(availableQuantity==0)
                    {
                        System.out.println("product out of stock");
                    }
                    else {
                        System.out.println("-------------------------------------------------------------------");
                        System.out.println("Do you want to choose a different quantity? (yes/no)");
                        response = sc.nextLine().toLowerCase();
                        if (response.equals("yes")) {
                            continue;
                        } else {
                            System.out.println("========================================================================");
                            System.out.println("--------------- Thank you for visiting. Have a great day! --------------");
                            System.out.println("========================================================================");                            
                            return;
                        }
                    }
                } else {
                    break;
                }
            } else {
                System.out.println("-------------------------------------------------------------------");
                System.out.println("Please enter a valid integer for quantity.");
                sc.nextLine(); 
            }
        }

        String query = "SELECT product_price FROM product WHERE product_type = ? AND p_color = ? AND p_size = ?";
        double productPrice = 0;
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, productType);
            pst.setString(2, productColor);
            pst.setString(3, productSize);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    productPrice = rs.getDouble("product_price");
                } else {
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Error fetching product price.");
                    return;
                }
            }
        }

        double totalPrice = productPrice * productQuantity;
        double discountedPrice = totalPrice;
        if (productQuantity % 2 == 0 || productQuantity >= 2) {
            discountedPrice *= 0.7;  
        }

        double deliveryCharge = discountedPrice < 499 ? 99 : 0;
        double finalPrice = discountedPrice + deliveryCharge;
        LocalDateTime DT = LocalDateTime.now();

        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("*                              order invoice                           *");
        System.out.println("************************************************************************");

        System.out.println("Total Price before Discount: " + totalPrice);
        System.out.println("Total Price after Discount: " + discountedPrice);
        System.out.println("Delivery Charge: " + deliveryCharge);
        System.out.println("Final Price: " + finalPrice);
        System.out.println("order placed on: "+DT);

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Do you want to proceed with the purchase? (yes/no): ");
        System.out.println("-------------------------------------------------------------------");
        String purchaseConfirmation = sc.nextLine().toLowerCase();
        if (!purchaseConfirmation.equals("yes")) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("|          Purchase canceled. Thank you for visiting!             |");
            System.out.println("-------------------------------------------------------------------");
            return;
        }
        System.out.print("Please enter your delivery address: ");
        String address = sc.nextLine();


        String insertQuery = "INSERT INTO buyers (user_id, product_type, p_color, p_size, product_quantity, address, total_Price, discounted_price, delivery_charge, final_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(insertQuery)) {
            pst.setInt(1, userId);
            pst.setString(2, productType);
            pst.setString(3, productColor);
            pst.setString(4, productSize);
            pst.setInt(5, productQuantity);
            pst.setString(6, address);
            pst.setDouble(7, totalPrice);
            pst.setDouble(8, discountedPrice);
            pst.setDouble(9, deliveryCharge);
            pst.setDouble(10, finalPrice);
            pst.executeUpdate();
        }

        String updateQuery = "UPDATE product SET product_quantity = product_quantity - ? WHERE product_type = ? AND p_color = ? AND p_size = ?";
        try (PreparedStatement pst = con.prepareStatement(updateQuery)) {
            pst.setInt(1, productQuantity);
            pst.setString(2, productType);
            pst.setString(3, productColor);
            pst.setString(4, productSize);
            pst.executeUpdate();
        }

        System.out.println("========================================================================");
        System.out.println("--------------- Thank you for purchase, Have a great day! --------------");
        System.out.println("========================================================================");  
        System.out.print("Do you want to make another purchase? (yes/no): ");
        String anotherPurchase = sc.nextLine().toLowerCase();
        if (anotherPurchase.equals("yes")) {
            continue;
        } else {
            System.out.println("========================================================================");
            System.out.println("|          Thank you for shopping with us. Have a great day!           |");
            System.out.println("========================================================================");

            return;
        }
    }
}
}
