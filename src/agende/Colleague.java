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
 *Clasa modeleaza conceptul de "coleg" avand ca atribut categoria de coleg (ex: facultate, liceu, munca, proiect etc.)
 * @author Robert
 */
public class Colleague
        extends Contact {

    private StringBuilder categorie = new StringBuilder();

    Colleague(Contact c) {
        this.nume = new StringBuilder(c.getNume());
        this.prenume = new StringBuilder(c.getPrenume());
        this.nrTel = new StringBuilder(c.getNrTel());
    }

    Colleague(Colleague c) {
        this.nume = new StringBuilder(c.getNume());
        this.prenume = new StringBuilder(c.getPrenume());
        this.nrTel = new StringBuilder(c.getNrTel());
        this.categorie = new StringBuilder(c.getAtribut());
    }

    Colleague(String nume, String prenume, String nrtel, String categorie) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
        nrTel = new StringBuilder(nrtel);
        this.categorie = new StringBuilder(categorie);
    }

    Colleague(String nume, String prenume, String nrtel) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
        nrTel = new StringBuilder(nrtel);
    }

    Colleague(String nume, String prenume) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
    }

    Colleague(String nume) {
        this.nume = new StringBuilder(nume);
    }

    Colleague() {
        nume = new StringBuilder();
        prenume = new StringBuilder();
        nrTel = new StringBuilder();
        categorie = new StringBuilder();
    }

    @Override
    void Afisare() {
        System.out.print("(c)");
        if (categorie.length() > 0) {
            System.out.println(categorie);
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
            System.out.print(email + "\n");
        }
    }

    @Override
    StringBuilder getAtribut() {
        return categorie;
    }

    @Override
    void setAtribut(String x) {
        categorie = new StringBuilder(x);
    }

    @Override
    void Salveaza(FileWriter fout) {
        try {
            fout.append("c|" + nume + "|" + prenume + "|" + nrTel);
            if (email.length() > 0) {
                fout.append("|" + email);
            } else {
                fout.append("| ");
            }

            if (categorie.length() > 0) {
                fout.append("|" + categorie);
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
            categorie = new StringBuilder(tokenizer.nextToken());
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
