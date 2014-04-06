/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clasa folosita pentru vizualizarea optiunilor disponibile editarii unui contact.
 *
 * @author Robert
 */
public class ViewS extends IViewS {

    private Nodc nod;
    private ControllerS controller;
    private StringBuilder text;

    /**
     * Costructor cu 2 parametri.
     *
     * @param n folosit pentru setarea modelului
     * @param c folosit pentru setarea cotrollerului
     */
    ViewS(Nodc n, ControllerS c) {
        nod = n;
        controller = c;
        controller.associateModel(nod);
        controller.associateView(this);
    }

    @Override
    void showContact() {
        System.out.print("***************SETARI*******************\n");
        System.out.print("****************************************\n");
        nod.informatie.Afisare();
        System.out.print("****************************************\n");
    }

    @Override
    char getUserOption() {
        System.out.println();
        System.out.print("N: Editati nume.\n");
        System.out.print("P: Editati prenume.\n");
        System.out.print("T: Editati numarul de telefon.\n");
        System.out.print("E: Editati email-ul.\n");
        System.out.print("A: Editati atributul.\n");
        System.out.print("Q: Iesire\n");


        System.out.print("Optiune: ");
        Scanner sc = new Scanner(System.in);
        String line = new String(sc.nextLine());
        try {
            return line.charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            return ' ';
        }
    }

    @Override
    void getUserData(StringBuilder s) {
        Scanner sc;
        sc = new Scanner(System.in);
        System.out.print("Text: ");
        s.insert(0, sc.nextLine());
    }
}
