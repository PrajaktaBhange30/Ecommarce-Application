package com.ecom.app;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
			System.out.println("--- Ecommerce App ---");
			System.out.println("1. Register:");
			System.out.println("2. Login:");
			System.out.println("3. Admin Panel:");
			System.out.println("4. Guest:");
			System.out.println("5. Exit");
			int choice = sc.nextInt();
			sc.nextLine(); // come to new line

			switch (choice) {
			case 1:
				UserOperations.register();
				break;
			case 2: {
				int userId = UserOperations.login();
				if (userId != -1) {
					boolean loop = true;
					while (loop) {
						System.out.println("1. View Products:");
						System.out.println("2. Buy Product:");
						System.out.println("3. View Cart:");
						System.out.println("4. Logout:");
						int op = sc.nextInt();
						sc.nextLine();
						switch (op) {
						case 1:
							UserOperations.viewProducts();
							break;
						case 2:
							UserOperations.buyProduct(userId);
							break;
						case 3:
							UserOperations.viewCart(userId);
							break;
						case 4:
							loop = false;
							break;
						}
					}
				}
			}
			case 3: {
				boolean adminLoop = true;
				while (adminLoop) {
					System.out.println("-- Admin operation --");
					System.out.println("1. Add Product:");
					System.out.println("2.View User:");
					System.out.println("3.Calculate Bill:");
					System.out.println("4. Purchase History:");
					System.out.println("5. main menu");
					int adminChoice = sc.nextInt();
					sc.nextLine();
					switch (adminChoice) {
					case 1:
						AdminOperations.addProduct();
						break;
					case 2:
						AdminOperations.viewRegisteredUsers();
						break;
					case 3:
						sc.nextLine();
						System.out.println("Enter username for calculate the bill");

						String username = sc.nextLine();
						AdminOperations.calculateBill(username);
						break;
					case 4:
						AdminOperations.viewPurchaseHistory();
						break;
					case 5:
						adminLoop = false;
						break;
					}
				}
			}
			case 4:
				GuestOperations.viewProducts();
				break;
			case 5: {
				System.exit(0);

			}
			default:
				System.out.println("Invalid choice");
			}
		}
	}
}
