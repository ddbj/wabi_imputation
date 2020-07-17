/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ogalab.example.os;

/**
 *
 * @author oogasawa
 */
public class Environment {
    
    public static void main(String[] args) {
        
        // getting environment variables.
        System.out.println(System.getenv());
        System.out.println(System.getenv("PATH"));
        
        System.out.println(System.getProperties());
//        System.out.println(System.getenv("PATH"));
        
        
        System.out.println(System.getProperty("java.specification.version"));
        System.out.println(System.getProperty("os.name"));
 
        
    }
    
}
