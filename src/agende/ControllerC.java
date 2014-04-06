/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

import java.util.Scanner;

/**
 *Clasa modifica modelul (lista de contacte) permitand diferite actiuni de navigare in lista, de salvare, de incarcare etc.
 * @author Robert
 */
public class ControllerC {

    private Listac agenda;
    private IViewC view;

    void associateModel(Listac a) {
        agenda = a;
    }

    void associateView(IViewC v) {
        view = v;
    }

    void startContacte() {
        char c = ' ';
        char r = ' ';

        while (c != 'q') {
            System.out.flush();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            view.showContacte(agenda.getPozitie());

            while (!((c == 'q') || (c == 's') || (c == 'w') || (c == 'e') || (c == 'd') || (c == 'n') || (c == 'f') || (c == 'r') || (c == 'k'))) {
                c = view.getUserOption();
            }

            if (c == 'q') {
                if (agenda.getModificare()) {
                    System.out.print("Doriti sa salvati? (d/n)\n");
                    System.out.print("Raspuns: ");
                    try {
                        Scanner sc = new Scanner(System.in);
                        String line = new String(sc.nextLine());

                        r = line.charAt(0);
                    } catch (StringIndexOutOfBoundsException e) {
                        r = 'n';
                    }

                    if (r == 'd') {
                        agenda.Salveaza();
                    }
                }
                return;
            }

            switch (c) {
                case 'w':
                    agenda.setPozitie(agenda.getPozitie() - 1);
                    break;
                case 's':
                    agenda.setPozitie(agenda.getPozitie() + 1);
                    break;
                case 'k':
                    agenda.Salveaza();
                    break;
                case 'f':
                    Filtreaza();
                    agenda.setModificare(false);
                    break;
                case 'r':
                    Cauta();
                    agenda.setModificare(false);
                    break;
                case 'd':
                    agenda.StergeContact();
                    break;
                case 'n':
                    agenda.AdaugaContact();
                    break;
                case 'e':
                    SaltMVCSettings();
                    break;
            }
            c = ' ';
        }
    }

    void Filtreaza() {
        ControllerC ctr = new ControllerC();
        ViewC viewc;
        viewc = new ViewC(agenda.Filtrare(), ctr);
        ctr.startContacte();
    }

    void Cauta() {
        ControllerC ctrl = new ControllerC();
        ViewC viewc;
        viewc = new ViewC(agenda.Cautare(), ctrl);
        ctrl.startContacte();
    }

    void SaltMVCSettings() {
        ControllerS ctrl = new ControllerS();
        ViewS views;
        views = new ViewS(this.agenda.getContact(agenda.getPozitie()), ctrl);
        agenda.setModificare(ctrl.startSettings());
    }
}
