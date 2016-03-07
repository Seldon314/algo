package week2;


import java.util.Iterator;

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
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;
    
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }




    private class MyIterator implements Iterator<Item> {
        private Node current;
        MyIterator() {
            current = first;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item result = current.item;
            current = current.next;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node last;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node n = new Node();
        n.item = item;
        n.next = first;
        n.last = null;
        if (first != null)
            first.last = n;
        else
            last = n;
        first = n;
        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node n = new Node();
        n.item = item;
        n.next = null;
        n.last = last;
        if (last != null)
            last.next = n;
        else
            first = n;
        last = n;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item result = first.item;
        first.item = null;
        first = first.next;
        if (first == null)
            last = null;
        else
            first.last = null;
        size--;
        return result;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        if (last.last == first)
            first.next = null;
        Item result = last.item;
        last.item = null;
        last = last.last;
        if (last == null)
            first = null;
        else
            last.next = null;
        size--;
        return result;
    }

    @Override
    public Iterator<Item> iterator() {
        return new MyIterator();
    }
    
    public static void main(String[] args) {
        Deque<String> myD = new Deque<>();
        myD.addFirst("First");
        myD.addLast("Second");
        myD.addFirst("NewFirst");
        System.out.println(myD.removeLast());
        System.out.println(myD.removeLast());
        System.out.println(myD.removeLast());
    }
}
