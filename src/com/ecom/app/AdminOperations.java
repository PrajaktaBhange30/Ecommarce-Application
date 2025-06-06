package com.ecom.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminOperations {

	static Scanner sc = new Scanner(System.in);

	// add product
	public static void addProduct() {
		try (Connection con = DBConnection.getConnection()) {
			System.out.print("Enter Product Name: ");
			String name = sc.nextLine();
			System.out.print("Enter Price: ");
			double price = sc.nextDouble();
			System.out.print("Enter Quantity: ");
			int qty = sc.nextInt();
			sc.nextLine(); // consume newline

			PreparedStatement pst = con
					.prepareStatement("insert into products (name, price, quantity) values (?, ?, ?)");
			pst.setString(1, name);
			pst.setDouble(2, price);
			pst.setInt(3, qty);
			pst.executeUpdate();

			System.out.println("Product added.");
		} catch (Exception e) {
			System.out.println("Error adding product: " + e.getMessage());
		}
	}
	// view registred users
	public static void viewRegisteredUsers() {
		try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement()) {
			ResultSet rs = st.executeQuery("select * from users");
			System.out.println("Registered Users:");
			while (rs.next()) {
				System.out.printf("ID: %d | Username: %s\n", rs.getInt("id"), rs.getString("username"));
			}
		} catch (Exception e) {
			System.out.println("Error fetching users: " + e.getMessage());
		}
	}
	
	 public static void calculateBill(String username) {
		 
	        double totalBill = 0.0;

	        String query = "select c.quantity, p.price from cart c " +
	                       "join products p on c.product_id = p.product_id " +
	                       "WHERE c.username = ?";

	        try 
	        (Connection conn = DBConnection.getConnection()) {
		        PreparedStatement pstmt = conn.prepareStatement(query);

	            pstmt.setString(1, username);
	            ResultSet rs = pstmt.executeQuery();

	            System.out.println("Bill Details: " + username + " -----");

	            while (rs.next()) {
	                int quantity = rs.getInt("quantity");
	                double price = rs.getDouble("price");
	                double itemTotal = quantity * price;
	                totalBill += itemTotal;

	                System.out.println("Item Price: " + price + " x Quantity: " + quantity + " = " + itemTotal);
	            }

	            System.out.println("----------------------------------------------");
	            System.out.println("Total Bill: " + totalBill);

	        } catch (SQLException e) {
	            System.out.println("Error calculating bill: " + e.getMessage());
	        }
	    }
	

	// view purchase history
	public static void viewPurchaseHistory() {
		try (Connection con = DBConnection.getConnection()) {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select u.username, p.name, h.quantity, h.purchase_date FROM purchase_history h join users u ON h.user_id = u.id JOIN products p ON h.product_id = p.product_id");
			System.out.println("Purchase History:");
			while (rs.next()) {
				System.out.printf("%s bought %s x %d on %s\n",
						rs.getString("username"), 
						rs.getString("name"),
						rs.getInt("quantity"),
						rs.getTimestamp("purchase_date"));
			}
		} catch (Exception e) {
			System.out.println("Error fetching history: " + e.getMessage());
		}
	}
}
