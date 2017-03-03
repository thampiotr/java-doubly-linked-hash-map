package piotrrr.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedMapTest {

    @Test
    public void basicPutAndGet() throws Exception {
        int testSize = 100;
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertFalse(map.get("1").isPresent());
        Assert.assertEquals(0, map.size());
        for (int i = 0; i < testSize; i++) {
            if (i % 2 == 0) {
                map.putBack(String.valueOf(i), i * i);
            } else {
                map.putFront(String.valueOf(i), i * i);
            }
        }
        Assert.assertFalse(map.get("123").isPresent());
        for (int i = 0; i < testSize; i++) {
            Assert.assertEquals(Integer.valueOf(i * i), map.get(String.valueOf(i)).orElse(-1));
        }
        Assert.assertEquals(testSize, map.size());
    }

    @Test
    public void iteratesForwardWithPutBack() throws Exception {
        int testSize = 100;
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertFalse(map.get("1").isPresent());
        for (int i = 0; i < testSize; i++) {
            map.putBack(String.valueOf(i), i * i);
        }
        Iterator<Integer> it = map.valuesIterator();
        for (int i = 0; i < testSize; i++) {
            Assert.assertTrue(it.hasNext());
            Assert.assertEquals(Integer.valueOf(i * i), it.next());
        }
    }

    @Test
    public void iteratesForwardPutFront() throws Exception {
        int testSize = 100;
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertFalse(map.get("1").isPresent());
        for (int i = 0; i < testSize; i++) {
            map.putFront(String.valueOf(i), i * i);
        }
        Iterator<Integer> it = map.valuesIterator();
        for (int i = 0; i < testSize; i++) {
            Assert.assertTrue(it.hasNext());
            int expected = testSize - i - 1;
            expected = expected * expected;
            Assert.assertEquals(Integer.valueOf(expected), it.next());
        }
    }

    @Test
    public void iteratesReversePutBack() throws Exception {
        int testSize = 100;
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertFalse(map.get("1").isPresent());
        for (int i = 0; i < testSize; i++) {
            map.putBack(String.valueOf(i), i * i);
        }
        Iterator<Integer> it = map.reverseValuesIterator();
        for (int i = 0; i < testSize; i++) {
            Assert.assertTrue(it.hasNext());
            int expected = testSize - i - 1;
            expected = expected * expected;
            Assert.assertEquals(Integer.valueOf(expected), it.next());
        }
    }

    @Test
    public void iteratesReversePutFront() throws Exception {
        int testSize = 100;
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertFalse(map.get("1").isPresent());
        for (int i = 0; i < testSize; i++) {
            map.putFront(String.valueOf(i), i * i);
        }
        Iterator<Integer> it = map.reverseValuesIterator();
        for (int i = 0; i < testSize; i++) {
            Assert.assertTrue(it.hasNext());
            Assert.assertEquals(Integer.valueOf(i * i), it.next());
        }
    }

    @Test
    public void removal() throws Exception {
        int testSize = 100;
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertEquals(0, map.size());
        for (int i = 0; i < testSize; i++) {
            map.putBack(String.valueOf(i), i * i);
        }
        Assert.assertEquals(testSize, map.size());
        for (int i = 0; i < testSize; i += 2) {
            map.remove(String.valueOf(i));
        }
        Assert.assertEquals(testSize / 2, map.size());
        for (int i = 0; i < testSize; i += 2) {
            Assert.assertFalse(map.get(String.valueOf(i)).isPresent());
        }
        for (int i = 1; i < testSize; i += 2) {
            Assert.assertTrue(map.get(String.valueOf(i)).isPresent());
        }
        Iterator<Integer> it = map.valuesIterator();
        for (int i = 1; i < testSize; i += 2) {
            Assert.assertEquals(Integer.valueOf(i * i), it.next());
        }
    }

    @Test
    public void emptyMap() throws Exception {
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertFalse(map.remove("abc").isPresent());
        Assert.assertFalse(map.get("sth").isPresent());
        Assert.assertFalse(map.valuesIterator().hasNext());
        Assert.assertFalse(map.reverseValuesIterator().hasNext());
    }

    @Test
    public void addAndRemoveOne() throws Exception {
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        Assert.assertFalse(map.get("sth").isPresent());
        Assert.assertFalse(map.valuesIterator().hasNext());
        Assert.assertFalse(map.reverseValuesIterator().hasNext());
        Assert.assertEquals(0, map.size());

        map.putBack("sth", 123);
        Assert.assertEquals(Integer.valueOf(123), map.get("sth").orElseThrow(NoSuchElementException::new));
        Assert.assertEquals(1, map.size());

        Iterator<Integer> fwdIt = map.valuesIterator();
        Assert.assertTrue(fwdIt.hasNext());
        Assert.assertEquals(Integer.valueOf(123), fwdIt.next());
        Assert.assertFalse(fwdIt.hasNext());

        Iterator<Integer> revIt = map.reverseValuesIterator();
        Assert.assertTrue(revIt.hasNext());
        Assert.assertEquals(Integer.valueOf(123), revIt.next());
        Assert.assertFalse(revIt.hasNext());

        map.remove("sth");
        Assert.assertEquals(0, map.size());
        Assert.assertFalse(map.get("sth").isPresent());
        Assert.assertFalse(map.valuesIterator().hasNext());
        Assert.assertFalse(map.reverseValuesIterator().hasNext());
    }

    @Test
    public void overwriting() throws Exception {
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();

        map.putBack("sth", 1);
        map.putBack("sth", 2);
        map.putBack("sth", 1);
        map.putBack("sth", 123);
        Assert.assertEquals(Integer.valueOf(123), map.get("sth").orElseThrow(NoSuchElementException::new));
        Assert.assertEquals(1, map.size());

        Iterator<Integer> fwdIt = map.valuesIterator();
        Assert.assertTrue(fwdIt.hasNext());
        Assert.assertEquals(Integer.valueOf(123), fwdIt.next());
        Assert.assertFalse(fwdIt.hasNext());

        Iterator<Integer> revIt = map.reverseValuesIterator();
        Assert.assertTrue(revIt.hasNext());
        Assert.assertEquals(Integer.valueOf(123), revIt.next());
        Assert.assertFalse(revIt.hasNext());

        map.remove("sth");
        Assert.assertEquals(0, map.size());
        Assert.assertFalse(map.get("sth").isPresent());
        Assert.assertFalse(map.valuesIterator().hasNext());
        Assert.assertFalse(map.reverseValuesIterator().hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsExceptionWhenIteratorInvalid() throws Exception {
        DoublyLinkedMap<String, Integer> map = new DoublyLinkedMap<>();
        map.reverseValuesIterator().next();
    }
}