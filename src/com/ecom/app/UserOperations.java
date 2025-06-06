package com.ecom.app;
import java.sql.*;
import java.util.Scanner;
public class UserOperations {
	
	


	    static Scanner sc = new Scanner(System.in);

	    public static void register() {
	        try (Connection con = DBConnection.getConnection()) {
	            System.out.print("Enter Username: ");
	            String username = sc.nextLine();
	            System.out.print("Enter Password: ");
	            String password = sc.nextLine();

	            PreparedStatement pst = con.prepareStatement("insert into users (username, password) values (?, ?)");
	            pst.setString(1, username);
	            pst.setString(2, password);
	            pst.executeUpdate();

	            System.out.println("Registration successful.");
	        } catch (Exception e) {
	            System.out.println("Registration failed: " + e.getMessage());
	        }
	    }

	    public static int login() {
	        try (Connection con = DBConnection.getConnection()) {
	            System.out.print("Enter Username: ");
	            String username = sc.nextLine();
	            System.out.print("Enter Password: ");
	            String password = sc.nextLine();

	            PreparedStatement pst = con.prepareStatement("select id from users where username=? and password=?");
	            pst.setString(1, username);
	            pst.setString(2, password);
	            ResultSet rs = pst.executeQuery();

	            if (rs.next()) {
	                System.out.println("Login successful!");
	                return rs.getInt("id");
	            } else {
	                System.out.println("Invalid credentials.");
	            }
	        } catch (Exception e) {
	            System.out.println("Login error: " + e.getMessage());
	        }
	        return -1;
	    }

	    public static void viewProducts() {
	        try (Connection con = DBConnection.getConnection();
	             Statement st = con.createStatement()) {
	            ResultSet rs = st.executeQuery("select * from products");
	            System.out.println("Available Products:");
	            while (rs.next()) {
	                System.out.printf("%d: %s - ₹%.2f (Stock: %d)\n",
	                        rs.getInt("product_id"),
	                        rs.getString("name"),
	                        rs.getDouble("price"),
	                        rs.getInt("quantity"));
	            }
	        } catch (Exception e) {
	            System.out.println("Error fetching products: " + e.getMessage());
	        }
	    }

	    public static void buyProduct(int userId) {
	        viewProducts();
	        try (Connection con = DBConnection.getConnection()) {
	            System.out.print("Enter product ID to buy: ");
	            int pid = sc.nextInt();
	            System.out.print("Enter quantity: ");
	            int qty = sc.nextInt();
	            sc.nextLine(); // for new line

	            PreparedStatement check = con.prepareStatement("select quantity from products where product_id=?");
	            check.setInt(1, pid);
	            ResultSet rs = check.executeQuery();

	            if (rs.next() && rs.getInt("quantity") >= qty) {
	                PreparedStatement addToCart = con.prepareStatement("insert into cart (user_id, product_id, quantity) values (?, ?, ?)");
	                addToCart.setInt(1, userId);
	                addToCart.setInt(2, pid);
	                addToCart.setInt(3, qty);
	                addToCart.executeUpdate();

	                PreparedStatement updateQty = con.prepareStatement("update products set quantity=quantity-? where product_id=?");
	                updateQty.setInt(1, qty);
	                updateQty.setInt(2, pid);
	                updateQty.executeUpdate();

	                PreparedStatement history = con.prepareStatement("insert into purchase_history (user_id, product_id, quantity) values (?, ?, ?)");
	                history.setInt(1, userId);
	                history.setInt(2, pid);
	                history.setInt(3, qty);
	                history.executeUpdate();

	                System.out.println("Purchase successful.");
	            } else {
	                System.out.println("Insufficient stock.");
	            }
	        } catch (Exception e) {
	            System.out.println("Purchase failed: " + e.getMessage());
	        }
	    }

	    public static void viewCart(int userId) {
	        try (Connection con = DBConnection.getConnection()) {
	            PreparedStatement pst = con.prepareStatement(
	                "select p.name, p.price, c.quantity from cart c join products p on c.product_id = p.product_id where c.user_id=?");
	            pst.setInt(1, userId);
	            ResultSet rs = pst.executeQuery();

	            System.out.println("Your Cart:");
	            while (rs.next()) {
	                System.out.printf("%s - ₹%.2f x %d\n",
	                        rs.getString("name"),
	                        rs.getDouble("price"),
	                        rs.getInt("quantity"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


