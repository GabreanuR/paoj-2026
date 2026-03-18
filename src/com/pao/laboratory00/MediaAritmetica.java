package com.pao.laboratory00;

import java.util.Scanner;

public class MediaAritmetica {
    public static void main(String[] args){
        System.out.print("Media Aritmetica: ");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        int n;
        double[] array;

        n = scanner.nextInt(); // scannerul citeste primitive
        // declaram un array de lungimea n
        array = new double[n];

        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        double sum = 0;
        for (double j : array) {
            sum += j;
        }
        double ma =  (double) sum /array.length;

        System.out.print(ma);
    }
}
