/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Clasa modeleaza conceptul de contact din care se pot deriva tipuri specifice (cu atribute
 * individuale).
 *
 * @author Robert
 */
public class Contact {

    protected StringBuilder nume = new StringBuilder();
    protected StringBuilder prenume = new StringBuilder();
    protected StringBuilder nrTel = new StringBuilder();
    protected StringBuilder email = new StringBuilder();

    public Contact(Contact c) {
        this.nume = new StringBuilder(c.getNume());
        this.prenume = new StringBuilder(c.getPrenume());
        this.nrTel = new StringBuilder(c.getNrTel());
    }

    Contact(String nume, String prenume, String nrtel) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
        nrTel = new StringBuilder(nrtel);
    }

    Contact(String nume, String prenume) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
    }

    Contact(String nume) {
        this.nume = new StringBuilder(nume);
    }

    Contact() {
        nume = new StringBuilder();
        prenume = new StringBuilder();
        nrTel = new StringBuilder();
    }

    StringBuilder getNume() {
        return nume;
    }

    StringBuilder getPrenume() {
        return prenume;
    }

    StringBuilder getNrTel() {
        return nrTel;
    }

    StringBuilder getAtribut() {
        return null;
    }

    StringBuilder getEmail() {
        return email;
    }

    /*
     * Metoda afiseaza la ecran atributele contactului intr-un format predefinit.
     */
    void Afisare() {
        if (nume.length() > 0) {
            System.out.print(nume);
        }

        if (prenume.length() > 0) {
            System.out.print("-" + prenume + "\n");
        }

        if (nrTel.length() > 0) {
            System.out.println(nrTel);
        }

        if (email.length() > 0) {
            System.out.println(email);
        }
    }

    /**
     * Metoda salveaza prin intermediul unui obiect de scriere in fisier atributele contactului
     * intr-un format predefinit.
     *
     * @param fout obiectul prin intermediul caruia se scrie in fisier.
     */
    void Salveaza(FileWriter fout) {
        try {
            fout.append("n|" + nume + "|" + prenume + "|" + nrTel);
            if (email.length() > 0) {
                fout.append("|" + email);
            } else {
                fout.append("| ");
            }
            fout.append(System.getProperty("line.separator").toString());
            fout.flush();
        } catch (IOException ex) {
        }
    }

    void setNume(String numeNou) {
        nume = new StringBuilder(numeNou);
    }

    void setPrenume(String prenumeNou) {
        prenume = new StringBuilder(prenumeNou);
    }

    /**
     * Metoda seteaza numarul de telefon daca atributul reprezinta un numar.
     *
     * @param nrTelNou valoarea cu care se incearca setarea.
     */
    void setNrTel(String nrTelNou) {
        try {
            Integer.parseInt(nrTelNou);
            nrTel = new StringBuilder(nrTelNou);
        } catch (NumberFormatException e) {
            System.err.println("Parametrul trimis nu este numar de telefon.");
            e.printStackTrace();
            nrTel = new StringBuilder();
        }
    }

    /**
     * Metoda seteaza emailul daca acesta corespunde unui format anume.
     *
     * @param email valoarea cu care se incearca setarea.
     * @throws Exceptie exceptie in cazul in care atributul nu corespunde cu formatul predefinit.
     */
    void setEmail(String email) throws Exceptie {
        Pattern p = Pattern.compile("([a-zA-Z]+[a-zA-Z1-9._]*)@(([a-zA-Z]+\\.)+[a-z]+)");

        if (p.matcher(email).matches()) {
            this.email = new StringBuilder(email);
        } else {
            this.email = new StringBuilder();
            try {
                System.err.wait(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Contact.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new Exceptie("Parametrul nu corespunde formatului: exemplu@domeniu.(com, ro, yahoo, google etc.)");
        }
    }

    /**
     * Metoda nu se implementeaza in clasa Contact ci in derivatele acesteia.
     *
     * @param s
     */
    void setAtribut(String s) {
    }

    /**
     * Metoda seteaza atributele contactului cu valori citite din fisier.
     *
     * @param line numele fisierului
     */
    void Incarca(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, "|");
        if (tokenizer.hasMoreTokens()) {
            nume = new StringBuilder(tokenizer.nextToken());
        }

        if (tokenizer.hasMoreTokens()) {
            prenume = new StringBuilder(tokenizer.nextToken());
        }

        if (tokenizer.hasMoreTokens()) {
            nrTel = new StringBuilder(tokenizer.nextToken());
        }

        if (tokenizer.hasMoreTokens()) {
            email = new StringBuilder(tokenizer.nextToken());
        }
    }

    /**
     * Metoda seteaza atributul specific parametrului "caz" cu valoarea parametrlui "atribut"
     *
     * @param caz cazul atributului: nume, prenume, numar telefon sau atribut.
     * @param atribut valoarea cu care se initializeaza atributul respectiv.
     * @return true
     */
    boolean Switch(char caz, String atribut) {

        switch (caz) {
            case 'n':
                setNume(atribut);
                break;
            case 'p':
                setPrenume(atribut);
                break;
            case 't':
                setNrTel(atribut);
                break;
            case 'e':
                try {
                    setEmail(atribut);
                } catch (Exceptie ex) {
                    System.err.println("Emailul nu are formatul corect");
                    Logger.getLogger(Contact.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 'a':
                System.out.println("Contactul nu are alte atribute.\n");
                break;
        }
        return true;
    }
}
