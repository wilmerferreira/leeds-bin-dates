package com.wilmerferreira;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import static java.lang.IO.*;

public class Program {
    static void main() throws Exception {
        var subscriptionKey = getSubscriptionKey();
        var addressClient = new AddressClient(subscriptionKey);
        var scanner = new Scanner(System.in);

        var postcode = readln("Enter your postcode: ");

        Address[] addresses;

        try {
            addresses = addressClient.getAddresses(postcode);

            if (addresses.length == 0) return; // TODO: Display an error before exiting
        } catch (Exception ex) {
            println(ex.getMessage());
            ex.printStackTrace();
            return;
        }

        int selectedAddressIndex;

        do {
            println("Select one of the following addresses:");

            for (var i = 0; i < addresses.length; i++) {
                var address = addresses[i];
                println("- [" + (i + 1) + "] : " + address.displayAddress());
            }

            println();

            println("Address: ");
            selectedAddressIndex = scanner.nextInt() - 1;
        } while (selectedAddressIndex < 0 || selectedAddressIndex > addresses.length - 1);

        println();

        var wasteClient = new WasteClient(subscriptionKey);
        BinDate[] binDates;

        try {
            binDates = wasteClient.getBinDates(addresses[selectedAddressIndex].uprn());
        } catch (Exception ex) {
            println(ex.getMessage());
            ex.printStackTrace();
            return;
        }

        if (binDates.length == 0) {
            println("No upcoming dates for " + addresses[selectedAddressIndex].displayAddress());
            return;
        }

        println("Upcoming dates for " + addresses[selectedAddressIndex].displayAddress());

        for (var binDate : binDates) {
            System.out.println("- " + binDate.type() + ": " + binDate.date());
        }
    }

    static String getSubscriptionKey() throws IOException {
        Properties properties = new Properties();

        try (InputStream inputStream = Program.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("config.properties was not found on the classpath");
            }

            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load config.properties", e
            );
        }

        return properties.getProperty("app.subscription");
    }
}
