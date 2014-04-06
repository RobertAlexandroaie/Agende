/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 *Clasa reprezinta interfata de comunicare dintre utilizator si lista de proprietari.
 * @author Robert
 */
public abstract class IViewO {

    abstract char getUserOption();

    abstract void showOwneri(int pozitie);
}
