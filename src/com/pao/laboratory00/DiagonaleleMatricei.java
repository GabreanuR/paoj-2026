package com.pao.laboratory00;

import java.util.Scanner;

public class DiagonaleleMatricei {
    public static void main(String[] args) {
        System.out.print("Diagonalele Matricei: ");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        int n;
        double[][] matrice;

        n = scanner.nextInt();

        matrice = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrice[i][j] = scanner.nextDouble();
            }
        }

        System.out.print("Suma Diagonalei Principale: ");
        System.out.println();

        double sum = 0;

        for (int i = 0; i < n; i++) {
            sum += matrice[i][i];
        }
        System.out.print(sum);
        System.out.println();

        System.out.print("Produsul Diagonalei Secundare: ");
        System.out.println();

        double prod = 1;

        for (int i = 0; i < n; i++) {
            prod *= matrice[i][n-i-1];
        }
        System.out.print(prod);
        System.out.println();

    }
}