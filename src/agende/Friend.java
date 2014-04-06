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
 *Clasa modeleaza un cotact de tip Friend (are ca atribut specific o data reprezentand o zi de nastere).
 * @author Robert
 */
public class Friend extends Contact {

    private StringBuilder data = new StringBuilder();

    Friend(Contact c) {
        nume = new StringBuilder(c.getNume());
        prenume = new StringBuilder(c.getPrenume());
        nrTel = new StringBuilder(c.getNrTel());
    }

    Friend(Friend f) {
        nume = new StringBuilder(f.getNume());
        prenume = new StringBuilder(f.getPrenume());
        nrTel = new StringBuilder(f.getNrTel());
        data = new StringBuilder(f.getAtribut());
    }

    Friend(String nume, String prenume, String nrtel, String data) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
        nrTel = new StringBuilder(nrtel);
        this.data = new StringBuilder(data);
    }

    Friend(String nume, String prenume, String nrtel) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
        nrTel = new StringBuilder(nrtel);
    }

    Friend(String nume, String prenume) {
        this.nume = new StringBuilder(nume);
        this.prenume = new StringBuilder(prenume);
    }

    Friend(String nume) {
        this.nume = new StringBuilder(nume);
    }

    Friend() {
        nume = new StringBuilder();
        prenume = new StringBuilder();
        nrTel = new StringBuilder();
        data = new StringBuilder();
    }

    boolean esteBisect(int an) {
        if (((an % 4 == 0) && (an % 100 != 0)) || (an % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    boolean esteDataValida(String data) {
        StringTokenizer tokenizer = new StringTokenizer(data, "/.- ");
        String zi = null;
        String luna = null;
        String an = null;

        if (tokenizer.hasMoreTokens()) {
            zi = new String(tokenizer.nextToken());
        } else {
            return false;
        }

        if (tokenizer.hasMoreTokens()) {
            luna = new String(tokenizer.nextToken());
        } else {
            return false;
        }

        if (tokenizer.hasMoreTokens()) {
            an = new String(tokenizer.nextToken());
        } else {
            return false;
        }

        int z = Integer.parseInt(zi);
        int l = Integer.parseInt(luna);
        int a = Integer.parseInt(an);

        if ((z >= 31)
                || ((z > 29) && (l == 2) && esteBisect(a))
                || (z > 31 && ((l <= 7 && l % 2 == 1) || (l > 7 && l % 2 == 0)))
                || ((z > 30) && (((l <= 7) && (l % 2 == 0)) || ((l > 7) && (l % 2 == 1))))
                || (l > 12)) {
            return false;
        }
        return true;
    }

    void setData(String data) throws Exceptie {
        if (esteDataValida(data)) {
            this.data = new StringBuilder(data);
        } else {
            throw new Exceptie("Data nu este valida!");
        }
    }

    @Override
    void Afisare() {
        System.out.print("(f)");
        if (data.length() > 0) {
            System.out.println(data);
        }

        if (nume.length() > 1) {
            System.out.print(nume + " ");
        }
        if (prenume.length() > 1) {
            System.out.print("-" + prenume + "\n");
        }
        if (nrTel.length() > 1) {
            System.out.println(nrTel);
        }

        if (email.length() > 0) {
            System.out.println(email);
        }
    }

    @Override
    StringBuilder getAtribut() {
        return data;
    }

    @Override
    void setAtribut(String data) {
        try {
            setData(data);
        } catch (Exceptie ex) {
            System.err.println("Data nu este valida");
            ex.printStackTrace();
        }
    }

    @Override
    void Salveaza(FileWriter fout) {
        try {
            fout.append("f|" + nume + "|" + prenume + "|" + nrTel);
            if (email.length() > 0) {
                fout.append("|" + email);
            } else {
                fout.append("| ");
            }

            if (data.length() > 0) {
                fout.append("|" + data);
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
            data = new StringBuilder(tokenizer.nextToken());
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
