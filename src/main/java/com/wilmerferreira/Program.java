package com.wilmerferreira;

import java.util.Arrays;

import static java.lang.IO.println;

public class Program {
    public static void main(String[] args) throws Exception {
        try {
            // TODO: Replace this logic and get the subscription key from a config file
            var subscriptionKey = "ad8dd80444fe45fcad376f82cf9a5ab4";
            var addressClient = new AddressClient(subscriptionKey);

            var addresses = addressClient.getAddresses("LS11 5DR");

            for (var address : addresses) {
                println(address.displayAddress());
            }

            if (addresses.length == 0) return; // TODO: Display an error before exiting

            var wasteClient = new WasteClient(subscriptionKey);
            var binDates = wasteClient.getBinDates(addresses[0].uprn());

            if (binDates.length == 0) return; // TODO: Display an error before exiting

            for (var binDate : binDates) {
                System.out.println(binDate.type() + ": " + binDate.date());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
