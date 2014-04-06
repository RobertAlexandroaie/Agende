/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 *Clasa actioneaza asupra modelului (un contact), avand diferite actiuni pentru editarea atributelor modelului. 
 * @author Robert
 */
public class ControllerS {
    
    private Nodc nod;
    private IViewS view;

    // setarea canalelor de comunicatie
    void associateModel(Nodc n) {
        nod = n;
    }

    void associateView(IViewS v) {
        view = v;
    }

    // bucla de executie
    boolean startSettings() {
        char c = ' ';

        StringBuilder s = new StringBuilder();
        boolean m = false;

        while (c != 'q') {
            System.out.flush();
            view.showContact();

            while (!((c == 'q') || (c == 't') || (c == 'p') || (c == 'n') || (c == 'a')||(c == 'e'))) {
                c = view.getUserOption();
            }


            if (c == 'q') {
                return m;
            }
            
            s.delete(0, s.length());
            view.getUserData(s);
            
            m = nod.informatie.Switch(c, s.toString());
            c=' ';
        }
        return m;
    }
}
