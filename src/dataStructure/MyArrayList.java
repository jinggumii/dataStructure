package dataStructure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class MyArrayList<T> implements List<T> {

    private Object[] item = new Object[10];
    private int size = 0;

    public void newSize() {
        Object[] newItem = new Object[item.length * 2];
        for (int i = 0; i < item.length; i++) {
            newItem[i] = item[i];
        }
        item = newItem;
    }

    public void checkParameter(int arg0) {
        if (size < arg0 || arg0 < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean add(T arg0) {
        if (size >= item.length) {
            newSize();
        }
        item[size] = arg0;
        size++;
        return true;
    }

    @Override
    public void add(int arg0, T arg1) {
        if (size < arg0 || arg0 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == item.length) {
            newSize();
        }
        for (int i = size; i > arg0; i--) {
            item[i] = item[i - 1];
        }
        item[arg0] = arg1;
        size++;

    }

    @Override
    public boolean addAll(Collection<? extends T> arg0) {
        while (size + arg0.size() > item.length) {
            newSize();
        }
        Object[] newArg0 = arg0.toArray();
        for (int i = 0; i < newArg0.length; i++) {
            item[size] = newArg0[i];
            size++;
        }
        return true;
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends T> arg1) {

        while (size + arg1.size() > item.length) {
            newSize();
        }

        Object[] newArg1 = arg1.toArray();
        Object[] newItem = new Object[item.length];
        int newSize = 0;
        for (int i = 0; i < arg0; i++) {
            newItem[i] = item[i];
            newSize++;
        }

        for (int i = 0; i < newArg1.length; i++) {
            newItem[i + arg0] = newArg1[i];
            newSize++;
        }

        int j = 0;
        for (int i = arg0 + newArg1.length; i < size + newArg1.length; i++) {
            newItem[i] = item[arg0 + j];
            newSize++;
            j++;
        }
        System.arraycopy(newItem, 0, item, 0, newItem.length);
        item = newItem;
        size = newSize;

        return true;
    }

    @Override
    public void clear() {
        item = new Object[10];
        size = 0;
    }

    @Override
    public boolean contains(Object arg0) {
        return indexOf(arg0) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        Object[] newArg1 = arg0.toArray();
        for (Object o : newArg1) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int arg0) {
        if (arg0 < 0 || arg0 >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) item[arg0];
    }

    @Override
    public int indexOf(Object arg0) {
        for (int i = 0; i < size; i++) {
            if (arg0 == null) {
                if (arg0 == item[i]) {
                    return i;
                }
            } else {
                if (arg0.equals(item[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new itr();
    }

    @Override
    public int lastIndexOf(Object arg0) {
        for (int i = size - 1; i >= 0; i--) {
            if (arg0 == null) {
                if (arg0 == item[i]) {
                    return i;
                }
            } else {
                if (arg0.equals(item[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new listItr();
    }

    @Override
    public ListIterator<T> listIterator(int arg0) {
        return new listItr(arg0);
    }

    @Override
    public boolean remove(Object arg0) {
        int idx = indexOf(arg0);
        if (idx == -1) {
            return false;
        }
        if (remove(idx) != null) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int arg0) {
        if (size < arg0 || arg0 < 0)
            throw new IndexOutOfBoundsException();
        T result = (T) item[arg0];

        Object[] newItem = new Object[size - 1];
        for (int i = 0; i < arg0; i++) {
            newItem[i] = item[i];
        }
        for (int i = arg0; i < size - 1; i++) {
            newItem[i] = item[i + 1];
        }
        item = newItem;
        size--;

        return result;
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        Object[] newArg0 = arg0.toArray();
        boolean isRemove = false;
        boolean result = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < newArg0.length; j++) {
                if (newArg0[j] == null && item[i] == null) {
                    isRemove = true;
                    break;
                } else if (newArg0[j] != null && item[i] != null && item[i].equals(newArg0[j])) {
                    isRemove = true;
                    break;
                }
            }
            if (isRemove) {
                remove(i);
                isRemove = false;
                result = true;
                i--;
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        boolean result = false;
        for (int i = size - 1; i >= 0; i--) {
            if (!arg0.contains(item[i])) {
                remove(i);
                i--;
                result = true;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T set(int arg0, T arg1) {
        checkParameter(arg0);
        Object result = item[arg0];
        item[arg0] = arg1;
        return (T) result;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> subList(int arg0, int arg1) {
        checkParameter(arg0);
        checkParameter(arg1);
        if (arg1 < arg0)
            throw new IndexOutOfBoundsException();
        List<T> result = new MyArrayList<>();
        for (int i = arg0; i < arg1; i++) {
            result.add((T) item[i]);
        }
        return result;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = item[i];
        }
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        T[] arr = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (T) item[i];
        }
        return arr;
    }

    private class itr implements Iterator<T> {

        int cursor = 0;
        int lastReturn = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() { // E
            if (cursor > size) {
                throw new NoSuchElementException();
            }
            lastReturn = cursor;
            cursor++;

            return MyArrayList.this.get(lastReturn);
            // return item[lastReturn];
        }

        @Override
        public void remove() {
            if (lastReturn < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(lastReturn);
            lastReturn = -1;
            cursor--;
        }

    }

    private class listItr extends itr implements ListIterator<T> {

        int cursor = size - 1;
        int prevReturn = -1;

        public listItr() {

        }

        public listItr(int arg0) {
            this.cursor = arg0;
            this.prevReturn = arg0 - 1;
        }

        @Override
        public void add(T arg0) {
            MyArrayList.this.add(arg0);
        }

        @Override
        public boolean hasPrevious() {
            return cursor >= 0;
        }

        @Override
        public int nextIndex() {
            return cursor + 1;
        }

        @Override
        public T previous() {
            if (cursor < 0) {
                throw new NoSuchElementException();
            }
            prevReturn = cursor;
            cursor--;
            return MyArrayList.this.get(prevReturn);
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T arg0) {
            MyArrayList.this.set(cursor, arg0);
        }

    }

}
