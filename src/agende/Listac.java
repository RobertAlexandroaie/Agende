/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Clasa foloseste o lista( de noduri) care reprezinta agenda unui proprietar.
 *
 * @author Robert
 */
public class Listac {

    boolean modificare;
    int pozitie;
    Contact ownerContact;
    Nodc primul, ultimul;

    Listac() {
        modificare = false;
        pozitie = 1;

        ownerContact = new Contact();
        primul = null;
        ultimul = null;
    }

    Listac(Contact c) {
        pozitie = 1;
        modificare = false;

        ownerContact = c;
        primul = null;
        ultimul = null;
    }

    /**
     * Constructor care initializeaza lista de contacte cu valori citite dintr-un fisier.
     *
     * @param linie numele fisierului.
     */
    Listac(String linie) {
        modificare = false;
        pozitie = 1;

        ownerContact = new Contact();
        primul = null;
        ultimul = null;
        Incarca(linie);
    }

    /**
     * Metoda initializeaza lista cu lista vida.
     */
    void ListaVida() {
        Modificare();
        primul = ultimul = null;
    }

    /**
     * Metoda verifica daca lista este vida.
     *
     * @return 1 daca este vida, 0 daca nu este vida.
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
     *
     * @return Lungimea listei.
     */
    int Lungime() {
        if (EsteVida() == 1) {
            return 0;
        }
        if (primul == ultimul) {
            return 1;
        }

        int l = 0;
        Nodc p;
        for (p = primul; p != null; p = p.urmator) {
            l++;
        }

        return l;
    }

    /**
     * Metoda adauga in ordine lexicografica in lista un contact dat ca parametru de un tip dat ca
     * parametru
     *
     * @param c contactul care se adauga
     * @param t tipul contactului
     */
    void AdaugaContact(Contact c, char t) {
        Modificare();

        Nodc p;
        Nodc q = new Nodc(c);
        q.setTip(t);

        if (EsteVida() == 1) {
            primul = ultimul = q;
        } else {
            for (p = primul;
                    p != null && p.informatie.getNume().toString().compareTo(q.informatie.getNume().toString()) < 0;
                    p = p.urmator);

            if (p == null) {
                InsereazaDupa(ultimul, q);
                ultimul = q;
            } else {
                if (p.informatie.getNrTel().toString().compareTo(q.informatie.getNrTel().toString()) == 0) {
                    System.out.print("\nAtentie!Nu se introduc doua contacte cu acelasi numar!\n\n\n\n");
                } else if (p.informatie.getNume().toString().compareTo(q.informatie.getNume().toString()) > 0) {
                    InsereazaInFata(p, q);
                    if (p == primul) {
                        primul = q;
                    }
                } else if (p.informatie.getNume().toString().compareTo(q.informatie.getNume().toString()) == 0) {
                    if (p.informatie.getPrenume().toString().compareTo(q.informatie.getPrenume().toString()) < 0) {
                        InsereazaDupa(p, q);
                        if (p == ultimul) {
                            ultimul = q;
                        }
                    } else if (p.informatie.getPrenume().toString().compareTo(q.informatie.getPrenume().toString()) > 0) {
                        InsereazaInFata(p, q);
                        if (p == primul) {
                            primul = q;
                        }
                    } else if (p.informatie.getPrenume().toString().compareTo(q.informatie.getPrenume().toString()) == 0) {
                        if (p.informatie.getNrTel().toString().compareTo(q.informatie.getNrTel().toString()) < 0) {
                            InsereazaDupa(p, q);
                            if (p == ultimul) {
                                ultimul = q;
                            }
                        } else if (p.informatie.getNrTel().toString().compareTo(q.informatie.getNrTel().toString()) > 0) {
                            InsereazaInFata(p, q);
                            if (p == primul) {
                                primul = q;
                            }
                        } else if (p.informatie.getNrTel().toString().compareTo(q.informatie.getNrTel().toString()) == 0) {
                            System.out.print("Nu se insereaza noduri identice.");
                        }
                    }
                }
            }
        }
    }

    /**
     * Metoda initializeaza un contact data ca parametru de un tip dat ca parametru
     *
     * @param contactDeAdaugat contactul care se initializeaza
     * @param tip tipul contactului
     */
    void contactNou(Contact contactDeAdaugat, char tip) {
        Scanner sc = new Scanner(System.in);
        StringBuilder valoareCamp = new StringBuilder();

        valoareCamp.delete(0, valoareCamp.length());
        System.out.print("Nume: ");
        valoareCamp.insert(0, sc.nextLine());
        contactDeAdaugat.setNume(valoareCamp.toString());

        System.out.print("Prenume: ");
        valoareCamp.delete(0, valoareCamp.length());
        valoareCamp.insert(0, sc.nextLine());
        contactDeAdaugat.setPrenume(valoareCamp.toString());

        System.out.print("Telefon: ");
        valoareCamp.delete(0, valoareCamp.length());
        valoareCamp.insert(0, sc.nextLine());
        contactDeAdaugat.setNrTel(valoareCamp.toString());

        if (tip != 'n') {
            System.out.print("Atribut: ");
            valoareCamp.delete(0, valoareCamp.length());
            valoareCamp.insert(0, sc.nextLine());
            contactDeAdaugat.setAtribut(valoareCamp.toString());
            AdaugaContact(contactDeAdaugat, tip);
        }
    }

    /**
     * Metoda adauga un contact nou in lista in functie de tipul dorit de utilizator.
     */
    void AdaugaContact() {
        char r = ' ';
        Scanner sc = new Scanner(System.in);

        System.out.print("Ce tip de contact adaugati? (a=Acquaintance/c=Colleague/n=Contact/f=Friend)\n");
        while (!(r == 'a' || r == 'c' || r == 'n' || r == 'f')) {
            System.out.print("Raspuns: ");
            String line = new String(sc.nextLine());
            r = line.charAt(0);
        }

        if (r == 'a') {
            Acquaintance a = new Acquaintance();
            contactNou(a, r);
        } else if (r == 'c') {
            Colleague a = new Colleague();
            contactNou(a, r);
        } else if (r == 'f') {
            Friend a = new Friend();
            contactNou(a, r);
        } else if (r == 'n') {
            Contact a = new Contact();
            contactNou(a, r);
        }
    }

    /**
     * Metoda sterge contactul de la pozitia deja definita.
     */
    void StergeContact() {
        Modificare();
        if (EsteVida() == 1) {
            System.out.print("Stiva este vida. Niciun contact de sters.\n");
        } else {
            Nodc nod_de_scos;
            int i;

            for (nod_de_scos = primul, i = 0; i < pozitie - 1 && nod_de_scos != null; i++, nod_de_scos = nod_de_scos.urmator);

            if (nod_de_scos == primul) {
                primul = nod_de_scos.urmator;
                if (primul != null) {
                    primul.precedent = null;
                }
            } else if (nod_de_scos == ultimul) {
                ultimul = nod_de_scos.precedent;
                if (ultimul != null) {
                    ultimul.urmator = null;
                }
            } else {
                nod_de_scos.urmator.precedent = nod_de_scos.precedent;
                nod_de_scos.precedent.urmator = nod_de_scos.urmator;
            }
        }
    }

    /**
     * Metoda seteaza campul modificare cu true pentru a semnala ca in lista s-a facut o modificare.
     */
    void Modificare() {
        modificare = true;
    }

    /**
     * Metoda returneaza starea listei (daca este sau nu modificata)
     *
     * @return modificare
     */
    boolean getModificare() {
        return modificare;
    }

    /**
     * Metoda seteaza atributul modificare cu valoarea daca ca parametru.
     *
     * @param m valoarea cu care se initializeaza campul modificare.
     */
    void setModificare(boolean m) {
        modificare = m;
    }

    /**
     * Metoda salveaza lista de contacte intr-un fisier specific numelui si prenumelui ownerului.
     */
    void Salveaza() {
        String fisier = new String(ownerContact.getNume() + "!" + ownerContact.getPrenume() + ".txt");

        Nodc p;
        FileWriter fout = null;
        try {
            fout = new FileWriter(fisier.toString());
        } catch (IOException ex) {
        }

        if (fout != null) {
            ownerContact.Salveaza(fout);

            for (p = primul; p != null; p = p.urmator) {
                p.informatie.Salveaza(fout);
            }
        }

        try {
            fout.close();
        } catch (IOException ex) {
        }
        setModificare(false);
    }

    /**
     * Metoda incarca lista de contacte dintr-un fisier dat ca parametru.
     *
     * @param numeFisier numele fisierlui.
     */
    void Incarca(String numeFisier) {
        StringBuilder numeFisierNou = new StringBuilder(numeFisier);
        boolean conditie = true;

        Scanner sc = null;
        while (conditie) {
            try {
                sc = new Scanner(new File(numeFisierNou.toString()));
                conditie = false;
            } catch (FileNotFoundException ex) {
                Scanner in = new Scanner(System.in);
                numeFisierNou.delete(0, numeFisierNou.length());
                numeFisierNou.insert(0, in.nextLine());
                conditie = true;
            }
        }

        char tip;

        StringBuilder linie = new StringBuilder();
        linie.delete(0, linie.length());
        linie.insert(0, sc.nextLine());

        ownerContact.Incarca(linie.substring(2));

        while (sc.hasNextLine()) {
            linie.delete(0, linie.length());
            linie.insert(0, sc.nextLine());

            Contact c = new Contact();
            Acquaintance a = new Acquaintance();
            Colleague cl = new Colleague();
            Friend f = new Friend();

            tip = linie.charAt(0);

            if (tip == 'n') {
                c.Incarca(linie.substring(2));
                AdaugaContact(c, tip);
            }

            if (tip == 'a') {
                a.Incarca(linie.substring(2));
                AdaugaContact(a, tip);
            }

            if (tip == 'c') {
                cl.Incarca(linie.substring(2));
                AdaugaContact(cl, tip);
            }

            if (tip == 'f') {
                f.Incarca(linie.substring(2));
                AdaugaContact(f, tip);
            }
        }
    }

    /**
     * Metoda returneza valoare pozitiei curente.
     *
     * @return pozitie
     */
    int getPozitie() {
        return pozitie;
    }

    /**
     * Metoda seteaza atributul pozitie cu valoarea trimisa ca parametru. Se seteaza doar valori
     * intre 1 si lungimea listei inclusiv.
     *
     * @param pozitieNoua valoarea pozitiei cu care se seteaza atributul.
     */
    void setPozitie(int pozitieNoua) {
        if (pozitieNoua <= 0) {
            pozitie = Lungime();
        } else {
            pozitie = pozitieNoua % (Lungime() + 1);
        }

        if (pozitie == 0) {
            pozitie++;
        }
    }

    /**
     * Metoda returneaza un contact de la pozitia curenta.
     *
     * @param poz valoarea pozitiei de la care se cere contactul.
     * @return Contactul de la pozitia data ca parametru.
     */
    Nodc getContact(int poz) {
        Nodc p;
        int i;

        for (i = 1, p = primul; i < getPozitie() && p != null; i++, p = p.urmator);

        return p;
    }

    /**
     * Metoda filtreaza lista in functie de criteriul dorit de utilizator (Tip contact, Retea,
     * Atribut).
     *
     * @return O lista noua de contacte ce contine numai acele contacte filtrate.
     */
    Listac Filtrare() {
        StringBuilder criteriu = new StringBuilder();
        Scanner sc = new Scanner(System.in);

        char t = ' ';
        while (criteriu.toString().compareTo("Tip") != 0
                && criteriu.toString().compareTo("Retea") != 0
                && criteriu.toString().compareTo("Data") != 0) {
            System.out.print("\nCriteriul de filtrare: (Tip/Retea/Data)\n");
            criteriu.delete(0, criteriu.length());
            criteriu.insert(0, sc.nextLine());

            criteriu.insert(0, criteriu.toString().toUpperCase());
            criteriu.insert(1, criteriu.substring(1).toString().toLowerCase());
        }

        Listac f = new Listac();
        f.ownerContact = this.ownerContact;
        Nodc p;

        if (criteriu.toString().compareTo("Tip") == 0) {
            System.out.println("Tipul de contact: (a/c/f/n)");
            String line = new String(sc.nextLine());
            t = line.charAt(0);

            for (p = primul; p != null; p = p.urmator) {
                if (p.getTip() == t) {
                    f.AdaugaContact(p.informatie, t);
                }
            }
            return f;
        } else if (criteriu.toString().compareTo("Retea") == 0) {
            for (p = primul; p != null; p = p.urmator) {
                if (p.informatie.getNrTel().charAt(2) == ownerContact.getNrTel().charAt(2) && p.informatie.getNrTel().charAt(3) == ownerContact.getNrTel().charAt(3)) {
                    f.AdaugaContact(p.informatie, t);
                }
            }
            return f;
        } else {
            System.out.println("Data: ");
            StringBuilder data = new StringBuilder(sc.nextLine());

            for (p = primul; p != null; p = p.urmator) {
                if (p.getTip() == 'f') {
                    if (p.informatie.getAtribut().toString().compareTo(data.toString()) == 0) {
                        f.AdaugaContact(p.informatie, t);
                    }
                }
            }
            return f;
        }
    }

    /**
     * Metoda cauta in lista de contactele care au ca atribute numele / prenumele / numarul de
     * telefon introdus de utilizator.
     *
     * @return Lista de contacte care respecta criteriul de cautare.
     */
    Listac Cautare() {
        StringBuilder criteriu = new StringBuilder();
        Scanner sc = new Scanner(System.in);

        while (criteriu.length() == 0) {
            System.out.println("Introduceti un criteriu de cautare (nume sau prenume sau ambele-separate prin spatiu- sau numar de telefon).\n");
            criteriu = new StringBuilder(sc.nextLine());
        }

        Listac l = new Listac();
        l.ownerContact = this.ownerContact;

        Nodc p;
        for (p = primul; p != null; p = p.urmator) {
            Nodc q = new Nodc();
            q.informatie = p.informatie;
            q.precedent = null;
            q.urmator = null;
            q.setTip(p.getTip());

            StringBuilder nume_c = new StringBuilder(q.informatie.getNume().toString());
            nume_c.append(" ");
            nume_c.append(q.informatie.getPrenume().toString());
            if (q.informatie.getNume().toString().contains(criteriu.toString())
                    || q.informatie.getPrenume().toString().contains(criteriu)
                    || nume_c.toString().contains(criteriu.toString())
                    || q.informatie.getNrTel().toString().contains(criteriu.toString())) {
                if (l.EsteVida() == 1) {
                    l.ultimul = l.primul = q;
                } else {
                    l.ultimul.urmator = q;
                    q.precedent = l.ultimul;
                    l.ultimul = q;
                }
            }
        }
        return l;
    }

    /**
     * Metoda insereaza inaintea unui nod un alt nod.
     *
     * @param nod_de_legatura nodul inaintea carua se insereaza.
     * @param nod_de_inserat nodul care se insereaza.
     */
    private void InsereazaInFata(Nodc nod_de_legatura, Nodc nod_de_inserat) {
        Modificare();
        if (nod_de_legatura != null && nod_de_inserat != null) {
            if (nod_de_legatura.precedent == null) {
                nod_de_inserat.urmator = nod_de_legatura;
                nod_de_legatura.precedent = nod_de_inserat;
            } else {
                nod_de_inserat.precedent = nod_de_legatura.precedent;
                nod_de_legatura.precedent.urmator = nod_de_inserat;
                nod_de_inserat.urmator = nod_de_legatura;
                nod_de_legatura.precedent = nod_de_inserat;
            }
        }
    }

    /**
     * metoda insereaza dupa un nod un alt nod.
     *
     * @param nod_de_legatura nodul dupa care se insereaza.
     * @param nod_de_inserat nodul care se insereaza.
     */
    private void InsereazaDupa(Nodc nod_de_legatura, Nodc nod_de_inserat) {
        Modificare();
        if (nod_de_legatura != null && nod_de_inserat != null) {
            if (nod_de_legatura.urmator == null) {
                nod_de_legatura.urmator = nod_de_inserat;
                nod_de_inserat.precedent = nod_de_legatura;
            } else {
                nod_de_inserat.urmator = nod_de_legatura.urmator;
                nod_de_legatura.urmator.precedent = nod_de_inserat;
                nod_de_inserat.precedent = nod_de_legatura;
                nod_de_legatura.urmator = nod_de_inserat;
            }
        }
    }

    /**
     * Metoda stabileste zodia in care se incadreaza o data.
     *
     * @param data data a carui zodie dorim sa o determinam.
     * @return O instantiere a unui Enum care reprezitna o zodie.
     */
    Zodia getZodia(String data) {
        StringTokenizer tokenizer = new StringTokenizer(data.toString(), "/-.");
        String zi = null;
        String luna = null;
        int z = 0;
        int l = 0;
        Zodia zodie = Zodia.berbec;

        if (tokenizer.hasMoreTokens()) {
            zi = new String(tokenizer.nextToken());
            try {
                z = Integer.parseInt(zi);
            } catch (NumberFormatException ex) {
                System.err.println("Nu se poate prelua ziua din data introdusa.");
            }
        }

        if (tokenizer.hasMoreTokens()) {
            luna = new String(tokenizer.nextToken());
            try {
                l = Integer.parseInt(luna);
            } catch (NumberFormatException ex) {
                System.err.println("Nu se poate prelua ziua din data introdusa.");
            }
        }

        if ((l == 3 && z >= 21) || (l == 4 && z <= 20)) {
            zodie = Zodia.berbec;
        } else if ((l == 4 && z >= 21) || (l == 5 && z <= 21)) {
            zodie = Zodia.taur;
        } else if ((l == 5 && z >= 21) || (l == 6 && z <= 20)) {
            zodie = Zodia.gemeni;
        } else if ((l == 6 && z >= 21) || (l == 7 && z <= 22)) {
            zodie = Zodia.rac;
        } else if ((l == 7 && z >= 23) || (l == 8 && z <= 22)) {
            zodie = Zodia.leu;
        } else if ((l == 8 && z >= 23) || (l == 9 && z <= 22)) {
            zodie = Zodia.fecioara;
        } else if ((l == 9 && z >= 23) || (l == 10 && z <= 22)) {
            zodie = Zodia.balanta;
        } else if ((l == 10 && z >= 23) || (l == 11 && z <= 21)) {
            zodie = Zodia.scorpion;
        } else if ((l == 11 && z >= 22) || (l == 12 && z <= 21)) {
            zodie = Zodia.sagetator;
        } else if ((l == 12 && z >= 22) || (l == 1 && z <= 19)) {
            zodie = Zodia.capricorn;
        } else if ((l == 1 && z >= 20) || (l == 2 && z <= 18)) {
            zodie = Zodia.varsator;
        } else if ((l == 2 && z >= 19) || (l == 3 && z <= 20)) {
            zodie = Zodia.pesti;
        }

        return zodie;
    }

    /**
     * Metoda genereaza un raport in format ".xls" care contine lista zodiilor si contactele din
     * care acestea fac parte.
     */
    void generateReport() {
        String numeFisier = new String(ownerContact.getNume().toString() + ownerContact.getPrenume().toString() + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("FIRST SHEET");

        int contorLinii = 0;
        Nodc nodDeParcurgere;

        for (Zodia contorZodii : Zodia.values()) {
            HSSFRow headerZodie = firstSheet.createRow((short) contorLinii);
            firstSheet.addMergedRegion(new CellRangeAddress(contorLinii, contorLinii, 0, 2));
            HSSFCell cellZodie = headerZodie.createCell(0);
            HSSFCellStyle stilCelula = workbook.createCellStyle();

            stilCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            stilCelula.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
            stilCelula.setAlignment(CellStyle.ALIGN_CENTER);
            stilCelula.setBorderBottom(CellStyle.BORDER_MEDIUM);
            cellZodie.setCellStyle(stilCelula);
            contorLinii++;

            switch (contorZodii) {
                case berbec:
                    cellZodie.setCellValue(new HSSFRichTextString("BERBEC"));
                    break;
                case taur:
                    cellZodie.setCellValue(new HSSFRichTextString("TAUR"));
                    break;
                case gemeni:
                    cellZodie.setCellValue(new HSSFRichTextString("GEMENI"));
                    break;
                case rac:
                    cellZodie.setCellValue(new HSSFRichTextString("RAC"));
                    break;
                case leu:
                    cellZodie.setCellValue(new HSSFRichTextString("LEU"));
                    break;
                case fecioara:
                    cellZodie.setCellValue(new HSSFRichTextString("FECIOARA"));
                    break;
                case balanta:
                    cellZodie.setCellValue(new HSSFRichTextString("BALANTA"));
                    break;
                case scorpion:
                    cellZodie.setCellValue(new HSSFRichTextString("SCORPION"));
                    break;
                case sagetator:
                    cellZodie.setCellValue(new HSSFRichTextString("SAGETATOR"));
                    break;
                case capricorn:
                    cellZodie.setCellValue(new HSSFRichTextString("CAPRICORN"));
                    break;
                case varsator:
                    cellZodie.setCellValue(new HSSFRichTextString("VARSATOR"));
                    break;
                case pesti:
                    cellZodie.setCellValue(new HSSFRichTextString("PESTI"));
                    break;
            }

            for (nodDeParcurgere = primul;
                    nodDeParcurgere != null;
                    nodDeParcurgere = nodDeParcurgere.urmator) {
                if (nodDeParcurgere.getTip() == 'f') {
                    if (getZodia(nodDeParcurgere.informatie.getAtribut().toString()) == contorZodii) {
                        HSSFRow linieContact = firstSheet.createRow((short) (contorLinii));
                        HSSFCell celulaNume = linieContact.createCell(0);
                        HSSFCell celulaPrenume = linieContact.createCell(1);
                        HSSFCell celulaData = linieContact.createCell(2);

                        celulaNume.setCellValue(new HSSFRichTextString(nodDeParcurgere.informatie.getNume().toString()));
                        celulaPrenume.setCellValue(new HSSFRichTextString(nodDeParcurgere.informatie.getPrenume().toString()));
                        celulaData.setCellValue(new HSSFRichTextString(nodDeParcurgere.informatie.getAtribut().toString()));
                        contorLinii++;
                    }
                }
            }
            contorLinii++;
        }
        firstSheet.autoSizeColumn(0, true);
        firstSheet.autoSizeColumn(1, true);
        firstSheet.autoSizeColumn(2, true);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(numeFisier));
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
