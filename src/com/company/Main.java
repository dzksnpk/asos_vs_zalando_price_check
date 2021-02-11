package com.company;

import com.jaunt.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type name of item you want to search: ");
        String item = sc.nextLine();

        try{
            System.out.println("Asos: ");
            UserAgent userAgent = new UserAgent();
            userAgent.visit("http://asos.com");
            Document body = userAgent.doc;
            body.getForm(0).setTextField("q", item);
            body.getForm(0).submit();

            Elements items = userAgent.doc.findEach("<div class=\"_3J74XsK\">");
            Element pricesAsos = userAgent.doc.findEach("<p class=\"_1ldzWib\">");

            int counter = 0;

            for (Element el: items) {
                System.out.println("" + el.findFirst("<p>").getTextContent());
                System.out.println("" + pricesAsos.getElement(counter).getTextContent());

                counter++;
            }
            System.out.println();
            System.out.println();
            System.out.println("Zalando: ");
           UserAgent userAgent2 = new UserAgent();
           userAgent2.visit("http://zalando.uk");
            Document body2 = userAgent2.doc;
            body2.getForm(0).setTextField("q", item);
            body2.getForm(0).submit();

            Elements items2 = userAgent2.doc.findEach("<div class=\"hPWzFB\">");
            Element pricesZalando = userAgent2.doc.findEach("<div class=\"_0xLoFW u9KIT8 _7ckuOK\">");

            int counter2 = 0;

            for (Element el: items2) {
                System.out.println("" + el.getTextContent());
                System.out.println("" + pricesZalando.getElement(counter2).getTextContent());

                counter2++;
            }

            String priceToCompare = pricesAsos.getElement(0).getTextContent();

            // method to check if price is discounted on asos or not since it is 2nd element on asos
            if (priceToCompare.length() > 7) {
                priceToCompare = priceToCompare.substring(priceToCompare.
                        indexOf('£', 2)).replaceAll("£", "");
            } else {
                priceToCompare = priceToCompare.replaceAll("£", "");
            }

            // getting price after discount, which is 1st element on zalando
            String priceToCompare2 = pricesZalando.getElement(0)
                    .getTextContent().replaceAll("[^0-9.]","");

            int index = priceToCompare2.indexOf('.');
            if (index >= 0) {
                priceToCompare2 = priceToCompare2.substring(0, index+3);
            }

            System.out.println();
            System.out.println("Price on asos is: " + priceToCompare);
            System.out.println("Price on zalando.co.uk is: " + priceToCompare2);

            double priceDouble = Double.parseDouble(priceToCompare);
            double priceDouble2 = Double.parseDouble(priceToCompare2);

            if(priceDouble < priceDouble2) {
                System.out.println("Asos is cheaper.");
            } else if(priceDouble == priceDouble2) {
                System.out.println("The price is the same.");
            } else {
                System.out.println("Zalando is cheaper.");
            }
        }
        catch(JauntException e){
            System.out.println(e);
        }
    }
}
