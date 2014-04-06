/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clasa folosita pentru vizualizarea proprietarilor care contin fiecare cate o agenda.
 *
 * @author Robert
 */
public class ViewO extends IViewO {

    private Listao owneri;
    private ControllerO controller;
/**
 * Constructor cu 2 parametri.
 * 
 * @param m folosit pentru setarea modelului
 * @param c folosit pentru setarea controllerului
 */
    ViewO(Listao m, ControllerO c) {
        owneri = m;
        controller = c;
        controller.associateModel(owneri);
        controller.associateView(this);
    }

    @Override
    char getUserOption() {
        System.out.println();
        System.out.println("w: Selectati optiunea precedenta.");
        System.out.println("s: Selectati optiunea urmatoare.");
        System.out.println("e: Afisati contactele proprietarului selectat.");
        System.out.println("n: Adaugati un nou proprietar.");
        System.out.println("d: Stergeti proprietarul curent(inclusiv agenda sa).");
        System.out.println("k: Salvati agendele.");
        System.out.println("l: Incarcati lista de agende.");
        System.out.println("q: Iesire");

        System.out.println("Optiune: ");

        Scanner sc = new Scanner(System.in);
        String line = new String(sc.nextLine());
        try {
            return line.charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            return ' ';
        }
    }

    @Override
    void showOwneri(int pozitie) {
        System.out.println("****************PROPRIETARI*************");
        System.out.println("****************************************");
        System.out.println(owneri.getPozitie() + " din " + owneri.getNrOwneri());
        System.out.println("****************************************");
        afisareOwneri(pozitie);
        System.out.println("****************************************");
    }

    void afisareOwneri(int pozitie) {
        if (owneri.EsteVida() == 1) {
            System.out.println("Nu aveti agende in memorie!");
            return;
        } else {
            int i;
            Nodo p;

            if (pozitie <= 3) {
                for (p = owneri.primul, i = 0; p != null && i < 5; i++, p = p.urmator) {
                    if (i + 1 == pozitie) {
                        System.out.print("o->");
                    } else {
                        System.out.print("      ");
                    }
                    if (p != null) {
                        System.out.println(p.informatie.ownerContact.getNume() + "-" + p.informatie.ownerContact.getPrenume());
                    }
                    System.out.println();
                }
            } else if (pozitie > 3 && pozitie <= owneri.Lungime() - 2) {
                for (p = owneri.primul, i = 1; i < pozitie && p != null; i++) {
                    p = p.urmator;
                }

                System.out.println("      " + p.precedent.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.precedent.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.informatie.ownerContact.getPrenume());
                System.out.println("o->" + p.informatie.ownerContact.getNume() + "-" + p.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.urmator.informatie.ownerContact.getNume() + "-" + p.urmator.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.urmator.urmator.informatie.ownerContact.getNume() + "-" + p.urmator.urmator.informatie.ownerContact.getPrenume());
            } else if (pozitie == owneri.Lungime() - 1) {
                for (p = owneri.primul, i = 1; i < pozitie && p != null; i++, p = p.urmator);
                System.out.println("      " + p.precedent.precedent.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.precedent.precedent.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.precedent.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.precedent.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.informatie.ownerContact.getPrenume());
                System.out.println("o->" + p.informatie.ownerContact.getNume() + "-" + p.urmator.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.urmator.informatie.ownerContact.getNume() + "-" + p.urmator.informatie.ownerContact.getPrenume());
            } else if (pozitie == owneri.Lungime()) {
                for (p = owneri.primul, i = 1; i < pozitie && p != null; i++, p = p.urmator);
                System.out.println("      " + p.precedent.precedent.precedent.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.precedent.precedent.precedent.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.precedent.precedent.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.precedent.precedent.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.precedent.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.precedent.informatie.ownerContact.getPrenume());
                System.out.println("      " + p.precedent.informatie.ownerContact.getNume() + "-" + p.precedent.informatie.ownerContact.getPrenume());
                System.out.println("o->" + p.informatie.ownerContact.getNume() + "-" + p.urmator.informatie.ownerContact.getPrenume());
            }
        }
    }
}
