package week2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
/**
 *
 * @author Stefan
 */
public class Subset {
    
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int size = Integer.parseInt(args[0]);
        int cnt = 0;
        while (!StdIn.isEmpty()) {
            cnt++;
            String next = StdIn.readString();
            int rnd = StdRandom.uniform(cnt);
            if (rnd >= size)
                continue;
            if (q.size() >= size)
                q.dequeue();
            q.enqueue(next);
        }
        for (String s : q) {
            System.out.println(s);
        }
    }
}
