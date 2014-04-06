/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 * Clasa folosita pentru inmagazinarea unui cotact intr-un format folosit la crearea unei liste.
 *
 * @author Robert
 */
public class Nodc {

    char tip;
    Contact informatie;
    Nodc urmator, precedent;

    Nodc() {
        informatie = new Contact();
        urmator = new Nodc();
        precedent = new Nodc();
    }

    Nodc(Contact info, Nodc urm, Nodc prec) {
        informatie = info;
        urmator = urm;
        precedent = prec;
    }

    Nodc(Contact info) {
        informatie = info;
    }

    char getTip() {
        return tip;
    }

    void setTip(char c) {
        tip = c;
    }
}
