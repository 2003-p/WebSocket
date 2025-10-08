package com.objetdirect.gwt.umldrawer.client.test;

import java.util.Scanner;

/**
 * @author saito
 *
 */
public class DistanceBetweenTwoPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the coordinates of the first point (x1 y1): ");
        double x1 = scanner.nextDouble();
        double y1 = scanner.nextDouble();

        System.out.println("Enter the coordinates of the second point (x2 y2): ");
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();

        double distance = calculateDistance(x1, y1, x2, y2);

        System.out.println("The distance between the two points is: " + distance);

        scanner.close();

	}

	public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}