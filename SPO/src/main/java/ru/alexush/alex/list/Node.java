package ru.alexush.alex.list;

public class Node {
    Node next;
    Node prev;
    int value;

    Node(Node next, Node prev, int value) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }
}
