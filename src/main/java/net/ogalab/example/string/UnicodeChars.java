/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ogalab.example.string;

/**
 *
 * @author oogasawa
 */
public class UnicodeChars {
    
    public static void main(String[] args) {
        StringBuffer b = new StringBuffer();
        b.append('a');
        b.append('b');
        b.append('c');
        b.append('\u00a5'); // Japanese Yen symbol.
        b.append('\u01FC'); // Roman AE with acute accent.
        b.append('\u0391'); // Greek Capital alpha.
        
        for (int i=0; i<b.length(); i++) {
            System.out.println("Character #" + i + " is " + b.charAt(i));
        }
    }
}