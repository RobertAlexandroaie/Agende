/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agende;

/**
 *Clasa reprezinta interfata de comunicare dintre utilizator si contact.
 * @author Robert
 */
public abstract class IViewS {
    
    abstract char getUserOption();

    abstract void showContact();

    abstract void getUserData(StringBuilder s);
}
