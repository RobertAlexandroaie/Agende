/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clasa folosita pentru vizualizarea agendei unui proprietar.
 *
 * @author Robert
 */
public class ViewC extends IViewC {

    private Listac agenda;
    private ControllerC controller;

    /**
     * Constructor cu 2 parametri
     *
     * @param a folosit pentru setarea modelului  
     * @param c folosit pentru setarea controllerului
     */
    ViewC(Listac a, ControllerC c) {
        agenda = a;
        controller = c;
        controller.associateModel(agenda);
        controller.associateView(this);
    }

    @Override
    void showContacte(int pozitie) {
        System.out.print("*****************CONTACTE***************\n");
        System.out.print("****************************************\n");
        System.out.print(agenda.ownerContact.getNume() + " - " + agenda.ownerContact.getPrenume() + "\n");
        System.out.print("****************************************\n");
        System.out.print(pozitie + " din " + agenda.Lungime() + "\n");
        System.out.print("****************************************\n");
        AfisareContacte(pozitie);
        System.out.print("****************************************\n");
    }

    @Override
    char getUserOption() {

        System.out.println();
        System.out.println("w: Selectati contactul precedent.");
        System.out.println("s: Selectati contactul urmatoar.");
        System.out.println("e: Afisati optiunile contactului.");
        System.out.println("f: Filtrati dupa <criteriu>.");
        System.out.println("r: Cautati dupa <criteriu>.");
        System.out.println("n: Adaugati un contact nou.");
        System.out.println("d: Stergeti contactul.");
        System.out.println("k: Salvati agenda curenta.");
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

    void AfisareContacte(int pozitie) {
        if (agenda.EsteVida() == 1) {
            System.out.println("Nu aveti contacte in agenda!");
            return;
        } else {
            int i;
            Nodc p;

            if (pozitie <= 3) {
                for (p = agenda.primul, i = 0; p != null && i < 5; i++, p = p.urmator) {
                    if (i + 1 == pozitie) {
                        System.out.print("o->");
                    } else {
                        System.out.print("   ");
                    }
                    if (p != null) {
                        System.out.print("(" + p.getTip() + ")" + p.informatie.getAtribut() + "\n      " + p.informatie.getNume() + " " + p.informatie.getPrenume() + "\n      " + p.informatie.getNrTel() + "\n");
                    }
                    System.out.println();
                }
            } else if (pozitie > 3 && pozitie <= agenda.Lungime() - 2) {
                for (p = agenda.primul, i = 1; i < pozitie && p != null; i++) {
                    p = p.urmator;
                }
                System.out.print("   (" + p.precedent.precedent.getTip() + ")" + p.precedent.precedent.informatie.getAtribut() + "\n      " + p.precedent.precedent.informatie.getNume() + " " + p.precedent.precedent.informatie.getPrenume() + "\n      " + p.precedent.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.precedent.getTip() + ")" + p.precedent.informatie.getAtribut() + "\n      " + p.precedent.informatie.getNume() + " " + p.precedent.informatie.getPrenume() + "\n      " + p.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("o->(" + p.getTip() + ")" + p.informatie.getAtribut() + "\n      " + p.informatie.getNume() + " " + p.informatie.getPrenume() + "\n      " + p.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.urmator.getTip() + ")" + p.urmator.informatie.getAtribut() + "\n      " + p.urmator.informatie.getNume() + " " + p.urmator.informatie.getPrenume() + "\n      " + p.urmator.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.urmator.urmator.getTip() + ")" + p.urmator.urmator.informatie.getAtribut() + "\n      " + p.urmator.urmator.informatie.getNume() + " " + p.urmator.urmator.informatie.getPrenume() + "\n      " + p.urmator.urmator.informatie.getNrTel() + "\n\n");
            } else if (pozitie == agenda.Lungime() - 1) {
                for (p = agenda.primul, i = 1; i < pozitie && p != null; i++, p = p.urmator);
                System.out.print("   (" + p.precedent.precedent.precedent.getTip() + ")" + p.precedent.precedent.precedent.informatie.getAtribut() + "\n      " + p.precedent.precedent.precedent.informatie.getNume() + " " + p.precedent.precedent.precedent.informatie.getPrenume() + "\n      " + p.precedent.precedent.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.precedent.precedent.getTip() + ")" + p.precedent.precedent.informatie.getAtribut() + "\n      " + p.precedent.precedent.informatie.getNume() + " " + p.precedent.precedent.informatie.getPrenume() + "\n      " + p.precedent.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.precedent.getTip() + ")" + p.precedent.informatie.getAtribut() + "\n      " + p.precedent.informatie.getNume() + " " + p.precedent.informatie.getPrenume() + "\n      " + p.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("o->(" + p.getTip() + ")" + p.informatie.getAtribut() + "\n      " + p.informatie.getNume() + " " + p.informatie.getPrenume() + "\n      " + p.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.urmator.getTip() + ")" + p.urmator.informatie.getAtribut() + "\n      " + p.urmator.informatie.getNume() + " " + p.urmator.informatie.getPrenume() + "\n      " + p.urmator.informatie.getNrTel() + "\n\n");
            } else if (pozitie == agenda.Lungime()) {
                for (p = agenda.primul, i = 1; i < pozitie && p != null; i++, p = p.urmator);
                System.out.print("   (" + p.precedent.precedent.precedent.precedent.getTip() + ")" + p.precedent.precedent.precedent.precedent.informatie.getAtribut() + "\n      " + p.precedent.precedent.precedent.precedent.informatie.getNume() + " " + p.precedent.precedent.precedent.precedent.informatie.getPrenume() + "\n      " + p.precedent.precedent.precedent.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.precedent.precedent.precedent.getTip() + ")" + p.precedent.precedent.precedent.informatie.getAtribut() + "\n      " + p.precedent.precedent.precedent.informatie.getNume() + " " + p.precedent.precedent.precedent.informatie.getPrenume() + "\n      " + p.precedent.precedent.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.precedent.precedent.getTip() + ")" + p.precedent.precedent.informatie.getAtribut() + "\n      " + p.precedent.precedent.informatie.getNume() + " " + p.precedent.precedent.informatie.getPrenume() + "\n      " + p.precedent.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("   (" + p.precedent.getTip() + ")" + p.precedent.informatie.getAtribut() + "\n      " + p.precedent.informatie.getNume() + " " + p.precedent.informatie.getPrenume() + "\n      " + p.precedent.informatie.getNrTel() + "\n\n");
                System.out.print("o->(" + p.getTip() + ")" + p.informatie.getAtribut() + "\n      " + p.informatie.getNume() + " " + p.informatie.getPrenume() + "\n      " + p.informatie.getNrTel() + "\n\n");
            }
        }
    }
}
