package week2;


import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;


    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[2];
    }
    private void resize(int n) {
        Item[] result = (Item[]) new Object[n];
        System.arraycopy(items, 0, result, 0, size);
        items = result;
    }
    @Override
    public Iterator<Item> iterator() {
        return new MyIterator();
    }
    private class MyIterator implements Iterator<Item> {
        private Item[] iterItems;
        private int pos;
        private MyIterator() {
            pos = 0;
            iterItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                randInsert(iterItems, i, items[i]);
            }
        }
        @Override
        public boolean hasNext() {
            return pos < iterItems.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return iterItems[pos++];
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    private void randInsert(Item[] array, int limit, Item it) {
        int rnd = StdRandom.uniform(limit + 1);
        if (rnd == limit)
            array[limit] = it;
        else {
            array[limit] = array[rnd];
            array[rnd] = it;
            }
    }
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (size == items.length) {
            resize(2*size);
        }
        randInsert(items, size++, item);

    }
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int rnd = StdRandom.uniform(size);
        Item res =  items[rnd];
        if (rnd != --size) {
            items[rnd] = items[size];
        }
        items[size] = null;
        if (size < items.length / 4)
            resize(items.length / 2);
        return res;
    }
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int rnd = StdRandom.uniform(size);
        return items[rnd];
    }
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(String.valueOf(i));
        }
        while (!rq.isEmpty()) {
            System.out.println(rq.dequeue());
        }
        for (String s : rq) {
            System.out.println(s);
        }
    }
    
    
}
