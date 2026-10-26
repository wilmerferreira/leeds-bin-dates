package com.wilmerferreira;

public record Address(String lpiKey,
                      String uprn,
                      String parentUPRN,
                      String usrn,
                      int x,
                      int y,
                      String displayAddress,
                      String organisation,
                      String street,
                      String locality,
                      String town,
                      String postcode) {
}
