/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ogalab.util.java;

/**
 *
 * @author oogasawa
 */
public class GetProperty {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(System.getProperty("java.library.path"));
        }
        else if (args.length > 0) {
            for (int i=0; i<args.length; i++) {
                System.out.println(args[i]);
                System.out.println(System.getProperty(args[i]));
                System.out.println("");
            }
        }
    }
    
}
