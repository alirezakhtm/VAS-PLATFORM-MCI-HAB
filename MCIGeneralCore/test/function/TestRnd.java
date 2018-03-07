/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import java.util.Random;

/**
 *
 * @author alirezakhtm
 */
public class TestRnd {
    public static void main(String[] args) {
        Random rnd = new Random();
        long l = Math.abs(rnd.nextLong());
        System.out.println(">>>> "+l);
    }
}
