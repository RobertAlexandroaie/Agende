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

/**
 *Clasa modeleaza conceptul de "cunostinta" avand ca atribut specific profesia in care cunostinta exceleaza.
 * @author Robert
 */
public class Acquaintance
        extends Contact {

    StringBuilder profesie = new StringBuilder();

    Acquaintance(Contact c) {
        this.nume = new StringBuilder(c.getNume());
        this.prenume = new StringBuilder(c.getPrenume());
        this.nrTel = new StringBuilder(c.getNrTel());
        this.profesie = new StringBuilder(c.getAtribut());
    }

    Acquaintance(Acquaintance a) {
        this.nume = new StringBuilder(a.getNume());
        this.prenume = new StringBuilder(a.getPrenume());
        this.nrTel = new StringBuilder(a.getNrTel());
        this.profesie = new StringBuilder(a.getAtribut());
    }

    Acquaintance(String nume, String prenume, String nrtel, String profesie) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
        nrTel = new StringBuilder(nrtel);
        this.profesie = new StringBuilder(profesie);
    }

    Acquaintance(String nume, String prenume, String nrtel) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
        nrTel = new StringBuilder(nrtel);
    }

    Acquaintance(String nume, String prenume) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
    }

    Acquaintance(String nume) {
        this.nume = new StringBuilder(nume);
    }

    Acquaintance() {
        nume = new StringBuilder();
        prenume = new StringBuilder();
        nrTel = new StringBuilder();
        profesie = new StringBuilder();
    }

    @Override
    void Afisare() {

        System.out.print("(c)");
        if (profesie.length() > 0) {
            System.out.println(profesie);
        }

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

    @Override
    StringBuilder getAtribut() {
        return profesie;
    }

    @Override
    void setAtribut(String profesie) {
        this.profesie = new StringBuilder(profesie);
    }

    @Override
    void Salveaza(FileWriter fout) {
        try {
            fout.append("a|" + nume + "|" + prenume + "|" + nrTel);
            if (email.length() > 0) {
                fout.append("|" + email);
            } else {
                fout.append("|");
            }

            if (profesie.length() > 0) {
                fout.append("|" + profesie);
            } else {
                fout.append("| ");
            }
            fout.append(System.getProperty("line.separator").toString());
            fout.flush();
        } catch (IOException ex) {
        }
    }

    @Override
    void Incarca(String linie) {
        StringTokenizer tokenizer = new StringTokenizer(linie, "|");

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

        if (tokenizer.hasMoreTokens()) {
            profesie = new StringBuilder(tokenizer.nextToken());
        }
    }

    @Override
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
                    System.err.println("Email-ul nu are formatul dorit.");
                    Logger.getLogger(Friend.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 'a':
                setAtribut(atribut);
                break;
        }
        return true;
    }
}
