package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        File outDir = new File("output");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;

        int n = Integer.parseInt(sc.nextLine().trim());

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                String[] tokens = sc.nextLine().trim().split("\\s+");
                int id = Integer.parseInt(tokens[0]);
                double suma = Double.parseDouble(tokens[1]);
                String data = tokens[2];
                TipTranzactie tip = TipTranzactie.valueOf(tokens[3].toUpperCase());

                byte[] record = new byte[RECORD_SIZE];

                ByteBuffer bb = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);

                bb.putInt(0, id);

                bb.putDouble(4, suma);

                byte[] dataBytes = String.format("%-10s", data).getBytes();
                System.arraycopy(dataBytes, 0, record, 12, 10);

                record[22] = (byte) (tip == TipTranzactie.CREDIT ? 0 : 1);

                // Byte 23: status (0 pt PENDING)
                record[23] = 0;

                // Bytes 24-31 sunt 0 (padding)

                dos.write(record);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] tokens = line.split("\\s+");
                String cmd = tokens[0].toUpperCase();

                switch (cmd) {
                    case "READ": {
                        int idx = Integer.parseInt(tokens[1]);
                        readAndPrint(raf, idx);
                        break;
                    }
                    case "UPDATE": {
                        int idx = Integer.parseInt(tokens[1]);
                        String statusStr = tokens[2].toUpperCase();

                        byte statusByte = (byte) (statusStr.equals("PROCESSED") ? 1 : statusStr.equals("REJECTED") ? 2 : 0);

                        raf.seek(idx * RECORD_SIZE + 23);
                        raf.write(statusByte);

                        System.out.println("Updated [" + idx + "]: " + statusStr);
                        break;
                    }
                    case "PRINT_ALL": {
                        for (int i = 0; i < n; i++) {
                            readAndPrint(raf, i);
                        }
                        break;
                    }
                }
            }
        }
    }

    private static void readAndPrint(RandomAccessFile raf, int idx) throws IOException {
        raf.seek(idx * RECORD_SIZE);

        byte[] buf = new byte[RECORD_SIZE];
        raf.readFully(buf);

        ByteBuffer bb = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN);

        int id = bb.getInt(0);
        double suma = bb.getDouble(4);

        String data = new String(buf, 12, 10).trim();

        String tipStr = buf[22] == 0 ? "CREDIT" : "DEBIT";
        String statusStr = buf[23] == 0 ? "PENDING" : buf[23] == 1 ? "PROCESSED" : "REJECTED";

        System.out.printf( "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n",
                idx, id, data, tipStr, suma, statusStr);
    }
}