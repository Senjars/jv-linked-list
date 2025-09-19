package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = getNode(index);
        Node<T> newNode = new Node<T>(current.getPrev(), value, current);

        if (current.getPrev() == null) {
            head = newNode;
        } else {
            current.getPrev().setNext(newNode);
        }
        current.setPrev(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Object[] elementsToAdd = list.toArray();
        for (Object obj : elementsToAdd) {
            @SuppressWarnings("unchecked")
            T value = (T) obj;
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForAccess(index);
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndexForAccess(index);
        Node<T> node = getNode(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForAccess(index);
        Node<T> nodeToRemove = getNode(index);

        T removedValue = nodeToRemove.getValue();

        if (nodeToRemove.getPrev() != null) {
            nodeToRemove.getPrev().setNext(nodeToRemove.getNext());
        } else {
            head = nodeToRemove.getNext();
        }

        if (nodeToRemove.getNext() != null) {
            nodeToRemove.getNext().setPrev(nodeToRemove.getPrev());
        } else {
            tail = nodeToRemove.getPrev();
        }

        nodeToRemove.setPrev(null);
        nodeToRemove.setNext(null);
        nodeToRemove.setValue(null);

        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.getValue(), object)) {
                if (current.getPrev() != null) {
                    current.getPrev().setNext(current.getNext());
                } else {
                    head = current.getNext();
                }

                if (current.getNext() != null) {
                    current.getNext().setPrev(current.getPrev());
                } else {
                    tail = current.getPrev();
                }

                current.setPrev(null);
                current.setNext(null);
                current.setValue(null);

                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private void checkIndexForAccess(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}

