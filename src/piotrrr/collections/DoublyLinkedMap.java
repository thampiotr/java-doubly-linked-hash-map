package piotrrr.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This is a basic implementation of a doubly-linked hash map, which allows for iteration (over values) in both directions.
 * Only basic operations, such as get, putBack and putFront are supported. This implementation is NOT thread-safe.
 * Modifying this map while iterating over it will yield undefined behaviour.
 *
 * @param <K> key type
 * @param <V> value typ
 */
public class DoublyLinkedMap<K, V> {
    private final HashMap<K, DoublyLinkedList.Node<V>> map = new HashMap<>();
    private final DoublyLinkedList<V> list = new DoublyLinkedList<>();

    public Optional<V> get(K key) {
        DoublyLinkedList.Node<V> node = map.get(key);
        return node == null ? Optional.empty() : Optional.of(node.value);
    }

    public Optional<V> putBack(K key, V value) {
        Optional<V> previous = remove(key);
        DoublyLinkedList.Node<V> node = list.pushBack(value);
        map.put(key, node);
        return previous;
    }

    public Optional<V> putFront(K key, V value) {
        Optional<V> previous = remove(key);
        DoublyLinkedList.Node<V> node = list.pushFront(value);
        map.put(key, node);
        return previous;
    }

    public Optional<V> remove(K key) {
        DoublyLinkedList.Node<V> node = map.get(key);
        if (node == null) {
            return Optional.empty();
        } else {
            list.remove(node);
            map.remove(key);
            return Optional.of(node.value);
        }
    }

    public Iterator<V> reverseValuesIterator() {
        return list.reverseValuesIterator();
    }

    public Iterator<V> valuesIterator() {
        return list.valuesIterator();
    }

    public int size() {
        return map.size();
    }

    private static class DoublyLinkedList<V> {
        private Node<V> head = null;
        private Node<V> tail = null;

        private Node<V> pushBack(V value) {
            Node<V> toAdd = new Node<>(value);
            toAdd.value = value;
            if (head == null) { // in fact, both head and tail are null
                head = toAdd;
                tail = toAdd;
            } else {
                tail.next = toAdd;
                toAdd.prev = tail;
                tail = toAdd;
            }
            return toAdd;
        }

        private Node<V> pushFront(V value) {
            Node<V> toAdd = new Node<>(value);
            if (head == null) { // in fact, both head and tail are null
                head = toAdd;
                tail = toAdd;
            } else {
                head.prev = toAdd;
                toAdd.next = head;
                head = toAdd;
            }
            return toAdd;
        }

        /**
         * @param node - a Node to remove. Cannot be null.
         */
        private void remove(Node<V> node) {
            if (node == head) {
                head = node.next;
            }
            if (node == tail) {
                tail = node.prev;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (node.prev != null) {
                node.prev.next = node.next;
            }
        }

        private Iterator<V> reverseValuesIterator() {
            return new ListIterator<>(this.tail, false);
        }

        private Iterator<V> valuesIterator() {
            return new ListIterator<>(this.head, true);
        }

        private static class ListIterator<V> implements Iterator<V> {
            private final boolean isForward;
            private Node<V> position;

            private ListIterator(Node<V> initialNode, boolean isForward) {
                this.position = initialNode;
                this.isForward = isForward;
            }

            @Override
            public boolean hasNext() {
                return position != null;
            }

            @Override
            public V next() {
                if (position == null) {
                    throw new NoSuchElementException("The list is empty.");
                }
                V value = position.value;
                if (isForward) {
                    position = position.next;
                } else {
                    position = position.prev;
                }
                return value;
            }
        }

        private static class Node<V> {
            private V value;
            private Node<V> next = null;
            private Node<V> prev = null;

            private Node(V value) {
                this.value = value;
            }
        }
    }
}
