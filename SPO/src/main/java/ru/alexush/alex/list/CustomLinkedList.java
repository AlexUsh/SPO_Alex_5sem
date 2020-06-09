package ru.alexush.alex.list;

public class CustomLinkedList {
    private Node firstNode;
    private Node lastNode;
    private int size = 0;

    public void add(int value) {
        if (firstNode == null) {
            firstNode = new Node(null, null, value);
            lastNode = firstNode;
        } else {
            Node node = new Node(null, lastNode, value);
            this.lastNode.next = node;
            this.lastNode = node;
        }
        size++;
    }

    public int get(int index) {
        int element = Integer.parseInt(null);
        if (index < size) {
            Node node = firstNode;
            if (index == 0) return (firstNode.value);
            else
                while (index > 0) {
                    index--;
                    node = node.next;
                    element = node.value;
                }

        } else System.out.println("This element doesn't exist");
        return element;
    }

    public void remove(int elem) throws Exception {
        Node node = firstNode;
        while(!node.equals(lastNode) && node.value != elem ) {
            node = node.next;
        }
        try {
            if (node != firstNode) {
                node.prev.next = node.next;
            } else
                firstNode = node.next;
            if (node != lastNode) {
                node.next.prev = node.prev;
            } else
                lastNode = node.prev;
        } catch (Exception e) {
            throw new Exception("Удаляемый элемент отсутствует");
        }
    }

    public boolean contains(int o) {
        Node node = firstNode;
        for (int i = 0; i < size; i++) {
            if ((node.value == o)) {
                return true;
            } else {
                node = node.next;
            }
        }
        return false;
    }

    public String toString() {
        Node node = firstNode;
        StringBuilder s = new StringBuilder("[");
        if (node != null) {
            while (node.next != null) {
                s.append(" ").append(node.value);
                node = node.next;
            }
            s.append(" ").append(node.value);
        }
        s.append("]");
        return s.toString();
    }
}
