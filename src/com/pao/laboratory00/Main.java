package com.pao.laboratory00;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        int[] array;
        n = scanner.nextInt(); // scannerul citeste primitive
        // declaram un array de lungimea n
        array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        System.out.print("Array-ul nostru: ");

        //afisam elementele array-ului
        for (int num : array) {
            System.out.print(num);
            System.out.print(" ");
        }
    }
}