/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 * Clasa pentru inmagazinarea unei agende intr-un format folosit la constructia unei liste.
 *
 * @author Robert
 */
public class Nodo {

    Listac informatie;
    Nodo urmator;
    Nodo precedent;

    Nodo() {
        informatie = new Listac();
        urmator = null;
        precedent = null;
    }

    Nodo(Listac info, Nodo urm, Nodo prec) {
        informatie = info;
        urmator = urm;
        precedent = prec;
    }

    Nodo(Listac info) {
        informatie = info;
        urmator = null;
        precedent = null;
    }
}
