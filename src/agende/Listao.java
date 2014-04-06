/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

import java.io.DataInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa folosita pentru reprezentarea lisei de propiretari de agenda.
 *
 * @author Robert
 */
public class Listao {

    int nrOwneri;
    int pozitie;
    Nodo primul;
    Nodo ultimul;

    Listao() {
        pozitie = 1;
        nrOwneri = 0;
        this.primul = null;
        this.ultimul = null;
    }
/**
 * Seteaza numarul de proprietari (lungimea listei).
 * 
 * @param numarOwneri valoarea cu care se seteaza numarul de proprietari.
 */
    void setNrOwneri(int numarOwneri) {
        this.nrOwneri = numarOwneri;
    }

    
    /**
     * Metoda care returneaza numarul de proprietari
     * 
     * @return numarul de proprietari
     */
    int getNrOwneri() {
        return this.nrOwneri;
    }

    /**
     * Metoda seteaza pozitia de selectie a unui proprietar din lista. Se verifica ca valoarea sa fie intre 1 si numarul maxim de proprietari.
     * 
     * @param pozitieNoua valoarea cu care se  seteaza pozitia.
     */
    void setPozitie(int pozitieNoua) {
        if (pozitieNoua <= 0) {
            pozitie = nrOwneri;
        } else {
            pozitie = pozitieNoua % (nrOwneri + 1);
        }
        
        if (pozitie == 0) {
            pozitie++;
        }
    }

    
    /**
     * 
     * @return pozitia curenta
     */
    int getPozitie() {
        return pozitie;
    }

    /**
     * Metoda initializeaza lista cu lista vida.
     */
    void ListaVida() {
        this.primul = this.ultimul = null;
    }

    /**
     * Metoda testeaza daca lista este vida.
     * @return 1 daca este vida, 0 daca este nevida.
     */
    int EsteVida() {
        if (primul == null && ultimul == null) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Metoda calculeaza lungimea listei.
     * @return lungimea listei
     */
    int Lungime() {
        if (this.EsteVida() == 1) {
            return 0;
        }
        if (primul == ultimul) {
            return 1;
        }

        int lungime = 0;
        Nodo p;
        for (p = primul; p != null; p = p.urmator) {
            lungime++;
        }
        return lungime;
    }

    
    /**
     * Metoda adauga lexicografic dupa nume,prenume,numar telefon,  un proprietar cu agenda  in functie de preferinta utilizatorului:din fisier sau din tastatura.
     */
    void AdaugaOwner() {
        char raspuns = ' ';
        Listac owner = new Listac();
        Scanner sc = new Scanner(System.in);
        System.out.println("\nOwner din fisier sau din tastatura?(f/t)\n");
        StringBuilder buffer = new StringBuilder();

        while (!(raspuns == 'f' || raspuns == 't')) {
            System.out.println("Raspuns: ");
            String line = new String(sc.nextLine());
            raspuns = line.charAt(0);
        }

        if (raspuns == 'f') {
            System.out.println("Fisierul este: ");
            buffer = new StringBuilder(sc.nextLine());
            owner.Incarca(buffer.toString());
        } else {
            System.out.println("Nume: ");
            buffer = new StringBuilder(sc.nextLine());
            owner.ownerContact.setNume(buffer.toString());


            System.out.println("Prenume: ");
            buffer = new StringBuilder(sc.nextLine());
            owner.ownerContact.setPrenume(buffer.toString());


            System.out.println("Numarul de telefonl: ");
            buffer = new StringBuilder(sc.nextLine());
            owner.ownerContact.setNrTel(buffer.toString());
        }

        nrOwneri++;
        Nodo p = new Nodo();
        Nodo q = new Nodo();

        q.precedent = null;
        q.urmator = null;
        q.informatie = owner;

        if (EsteVida() == 1) {
            primul = ultimul = q;
        } else {
            for (p = this.primul;
                    (p != null) && (p.informatie.ownerContact.getNume().toString().compareTo(q.informatie.ownerContact.getNume().toString()) < 0);
                    p = p.urmator);

            if (p == null) {
                InsereazaDupaPr(ultimul, q);
                ultimul = q;
            } else {
                if (p.informatie.ownerContact.getNume().toString().compareTo(q.informatie.ownerContact.getNume().toString()) > 0) {
                    InsereazaInaintePr(p, q);
                    if (p == primul) {
                        primul = q;
                    }
                } else if (p.informatie.ownerContact.getNume().toString().compareTo(q.informatie.ownerContact.getNume().toString()) == 0) {
                    if (p.informatie.ownerContact.getPrenume().toString().compareTo(q.informatie.ownerContact.getPrenume().toString()) < 0) {
                        InsereazaDupaPr(p, q);
                        if (p == ultimul) {
                            ultimul = q;
                        }
                    } else if (p.informatie.ownerContact.getPrenume().toString().compareTo(q.informatie.ownerContact.getPrenume().toString()) > 0) {
                        InsereazaInaintePr(p, q);
                        if (p == primul) {
                            primul = q;
                        }
                    } else if (p.informatie.ownerContact.getPrenume().toString().compareTo(q.informatie.ownerContact.getPrenume().toString()) == 0) {
                        if (p.informatie.ownerContact.getNrTel().toString().compareTo(q.informatie.ownerContact.getNrTel().toString()) < 0) {
                            InsereazaDupaPr(p, q);
                            if (p == ultimul) {
                                ultimul = q;
                            }
                        } else if (p.informatie.ownerContact.getNrTel().toString().compareTo(q.informatie.ownerContact.getNrTel().toString()) > 0) {
                            InsereazaInaintePr(p, q);
                            if (p == primul) {
                                primul = q;
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * Metoda primeste ca parametru o lista de contacte cu un owner (agenda) pe care o adauga lexicografic dupa numele, prenumele si numarul de telefon a ownerului.
     */
    void AdaugaOwner(Listac agendaNoua) {
        nrOwneri++;
        Nodo p = new Nodo();
        Nodo q = new Nodo();

        q.precedent = null;
        q.urmator = null;
        q.informatie = agendaNoua;

        if (EsteVida() == 1) {
            primul = ultimul = q;
        } else {
            for (p = this.primul;
                    (p != null) && (p.informatie.ownerContact.getNume().toString().compareTo(q.informatie.ownerContact.getNume().toString()) < 0);
                    p = p.urmator);

            if (p == null) {
                InsereazaDupaPr(ultimul, q);
                ultimul = q;
            } else {
                if (p.informatie.ownerContact.getNume().toString().compareTo(q.informatie.ownerContact.getNume().toString()) > 0) {
                    InsereazaInaintePr(p, q);
                    if (p == primul) {
                        primul = q;
                    }
                } else if (p.informatie.ownerContact.getNume().toString().compareTo(q.informatie.ownerContact.getNume().toString()) == 0) {
                    if (p.informatie.ownerContact.getPrenume().toString().compareTo(q.informatie.ownerContact.getPrenume().toString()) < 0) {
                        InsereazaDupaPr(p, q);
                        if (p == ultimul) {
                            ultimul = q;
                        }
                    } else if (p.informatie.ownerContact.getPrenume().toString().compareTo(q.informatie.ownerContact.getPrenume().toString()) > 0) {
                        InsereazaInaintePr(p, q);
                        if (p == primul) {
                            primul = q;
                        }
                    } else if (p.informatie.ownerContact.getPrenume().toString().compareTo(q.informatie.ownerContact.getPrenume().toString()) == 0) {
                        if (p.informatie.ownerContact.getNrTel().toString().compareTo(q.informatie.ownerContact.getNrTel().toString()) < 0) {
                            InsereazaDupaPr(p, q);
                            if (p == ultimul) {
                                ultimul = q;
                            }
                        } else if (p.informatie.ownerContact.getNrTel().toString().compareTo(q.informatie.ownerContact.getNrTel().toString()) > 0) {
                            InsereazaInaintePr(p, q);
                            if (p == primul) {
                                primul = q;
                            }
                        }
                    }
                }
            }
        }
    }

    
    /**
     * Metoda sterge agenda din lista in functie de pozitia deja setata.
     */
    void StergeOwner() {
        Nodo nodDeScos;
        int i;
        for (nodDeScos = primul, i = 0; i + 1 < pozitie && nodDeScos != null; i++, nodDeScos = nodDeScos.urmator);
        if (nrOwneri > 0) {
            nrOwneri--;
        }

        if (this.EsteVida() == 1) {
            System.out.println("Lista este vida. Niciun proprietar de sters.\n");
        } else {
            if (nodDeScos == primul) {
                primul = nodDeScos.urmator;
                if (primul != null) {
                    primul.precedent = null;
                }
            } else if (nodDeScos == ultimul) {
                ultimul = nodDeScos.precedent;
                if (ultimul != null) {
                    ultimul.urmator = null;
                }
            } else {
                nodDeScos.urmator.precedent = nodDeScos.precedent;
                nodDeScos.precedent.urmator = nodDeScos.urmator;
            }
        }
    }

    /**
     * Metoda returneaza agenda de la pozitia primita ca parametru
     * @param pozitie pozitia de la care se cere agenda
     * @return O agenda din lista de owneri corespunzatoare pozitiei indicate.
     */
    Listac getOwner(int pozitie) {
        Nodo p = new Nodo();
        int i;
        for (i = 0, p = primul; (i < this.getPozitie() - 1) && (p != null); i++, p = p.urmator);
        return p.informatie;
    }

    /**
     * Metoda salveaza lista de agende intr-un fisier standard si fiecare agenda in parte in fisierul corespunzator.
     * @return 
     */
    boolean Salveaza() {
        FileWriter fout = null;

        try {
            fout = new FileWriter("owners.txt");
        } catch (IOException ex) {
            System.err.println("Fisierul nu este disponibil.");
            return false;
        }

        if (fout == null) {
            return false;
        } else {
            try {
                Nodo n;
                for (n = primul; n != null; n = n.urmator) {
                    fout.append(n.informatie.ownerContact.getNume() + "|" + n.informatie.ownerContact.getPrenume() + "\n");
                    n.informatie.Salveaza();
                }
            } catch (IOException e) {
                return false;
            }
        }
        try {
            fout.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    
    /**
     * Metoda incarca dintr-un fisier standard lista de agende si fiecare agenda in parte din fisierul corespunzator.
     * @return 
     */
    boolean Incarca() {
        FileReader fin = null;
        try {
            fin = new FileReader("owners.txt");
        } catch (IOException ex) {
            return false;
        }

        if (fin == null) {
            return false;
        }

        String linie;
        Scanner sc = new Scanner(fin);
        while (sc.hasNext()) {
            linie = new String(sc.nextLine());
            StringTokenizer tokenizer = new StringTokenizer(linie, "|");
            int curPos = 0;

            String nume = new String(tokenizer.nextToken());
            String prenume = new String(tokenizer.nextToken());

            String fisier = new String(nume + "|" + prenume);

            Listac agendaNoua = new Listac(fisier);
            this.AdaugaOwner(agendaNoua);
        }
        return true;
    }

    /**
     * Metoda care insereaza un nod in lista de agende inaintea unui alt nod.
     * 
     * @param nodDeLegatura nodul inaintea caruia se insereaza.
     * @param nodDeInserat nodul care se insereaza.
     */
    private void InsereazaInaintePr(Nodo nodDeLegatura, Nodo nodDeInserat) {
        if (nodDeLegatura != null && nodDeInserat != null) {
            if (nodDeLegatura.precedent == null) {
                nodDeInserat.urmator = nodDeLegatura;
                nodDeLegatura.precedent = nodDeLegatura;
            } else {
                nodDeInserat.precedent = nodDeLegatura.precedent;
                nodDeLegatura.precedent.urmator = nodDeInserat;
                nodDeInserat.urmator = nodDeLegatura;
                nodDeLegatura.precedent = nodDeInserat;
            }
        }
    }

    
    /**
     * Metoda care insereaza un nod in lista de agende dupa un alt nod.
     * 
     * @param nodDeLegatura nodul dupa care se insereaza.
     * @param nodDeInserat nodul care se insereaza.
     */
    private void InsereazaDupaPr(Nodo nodDeLegatura, Nodo nodDeInserat) {
        if (nodDeLegatura != null && nodDeInserat != null) {
            if (nodDeLegatura.urmator == null) {
                nodDeLegatura.urmator = nodDeInserat;
                nodDeInserat.precedent = nodDeLegatura;
            } else {
                nodDeInserat.urmator = nodDeLegatura.urmator;
                nodDeLegatura.urmator.precedent = nodDeInserat;
                nodDeInserat.precedent = nodDeLegatura;
                nodDeLegatura.urmator = nodDeInserat;
            }
        }
    }
}
