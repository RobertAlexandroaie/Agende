/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 *Clasa modifica modelul (lista de agende) permitand diferite actiuni de navigare in lista, de salvare, de incarcare etc.
 * @author Robert
 */
public class ControllerO {

    private Listao owneri;
    private IViewO view;

    void associateModel(Listao o) {
        owneri = o;
    }

    void associateView(IViewO v) {
        view = v;
    }

    void saltMVCContact() {
        ControllerC ctr = new ControllerC();
        ViewC viewc;
        viewc = new ViewC(this.owneri.getOwner(this.owneri.getPozitie()), ctr);
        ctr.startContacte();
    }

    void start() {
        char c = ' ';
        while (c != 'q') {
            System.out.flush();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            view.showOwneri(owneri.getPozitie());

            while (!((c == 'q') || (c == 's') || (c == 'w') || (c == 'e') || (c == 'n') || (c == 'd'))) {
                c = view.getUserOption();
            }

            if (c == 'q') {
                return;
            }

            switch (c) {
                case 'w':
                    owneri.setPozitie(owneri.getPozitie() - 1);
                    break;
                case 's':
                    owneri.setPozitie(owneri.getPozitie() + 1);
                    break;
                case 'n':
                    owneri.AdaugaOwner();
                    break;
                case 'd':
                    owneri.StergeOwner();
                    break;
                case 'e':
                    saltMVCContact();
                    break;
                case 'k':
                    owneri.Salveaza();
                    break;
                case 'l':
                    owneri.Incarca();
                    break;
            }
            c = ' ';
        }
    }
}
