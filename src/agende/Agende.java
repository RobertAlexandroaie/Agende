/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 * Clasa principala care prezinta funcitonalitatea aplicatiei.
 * 
 * @author Robert
 */
public class Agende {

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
	// TODO code application logic here
	Listao agende = new Listao();
	Listac agendaStd = new Listac();
	agendaStd.Incarca("Numepr1!Prenumepr1.txt");

	Nodo nod = new Nodo(agendaStd, null, null);
	agende.primul = nod;
	agende.ultimul = nod;

	agende.setNrOwneri(agende.getNrOwneri() + 1);
	ControllerO ctrl = new ControllerO();
	@SuppressWarnings("unused")
	ViewO v = new ViewO(agende, ctrl);
	ctrl.start();

	agendaStd.generateReport();
	/*
	 * System.out.print(nod.informatie.getContact(1).informatie.getNume() +
	 * " " + nod.informatie.getContact(1).informatie.getPrenume() + "\n");
	 */
    }
}
