/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 *Clasa reprezinta interfata de comunicare dintre utilizator si lista de contacte.
 * @author Robert
 */
public abstract class IViewC {

    abstract char getUserOption();
    abstract void showContacte(int pozitie);
}
