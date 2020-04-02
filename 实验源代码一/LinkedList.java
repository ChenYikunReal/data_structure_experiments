import java.io.Serializable;
import java.util.NoSuchElementException;

public class LinkedList<T> implements ILinkedList<T>, Serializable {
    
    /**
     * 默认序列化UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 链表长度，默认为0
     */
    private transient int size = 0;
    
    /**
     * 头结点指针
     */
    private transient LinkedNode<T> first;
    
    /**
     * 尾结点指针
     */
    private transient LinkedNode<T> last;

    @Override
    public T getFirst() {
        final LinkedNode<T> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.data;
    }

    @Override
    public T getLast() {
        final LinkedNode<T> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.data;
    }

    @Override
    public T removeFirst() {
        final LinkedNode<T> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }

    @Override
    public T removeLast() {
        final LinkedNode<T> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }

    @Override
    public void addFirst(T t) {
        linkFirst(t);
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public boolean insert(T t) {
        linkLast(t);
        return true;
    }

    @Override
    public void insert(T element, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, node(index));
        }
    }

    @Override
    public boolean containsAll(ILinkedList<T> c) {
        for (int i = 0; i < c.size(); i++) {
            if (!contains(c.find(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean insertAll(ILinkedList<T> c) {
        for (int i = 0; i < c.size(); i++) {
            insert(c.find(i));
        }
        return true;
    }

    @Override
    public boolean insertAll(ILinkedList<T> c, int index) {
        for (int i = 0; i < c.size(); i++) {
            insert(c.find(i), index++);
        }
        return true;
    }

    @Override
    public void clear() {
        for (LinkedNode<T> x = first; x != null; ) {
            LinkedNode<T> next = x.next;
            x.data = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public T find(int index) {
        checkElementIndex(index);
        return node(index).data;
    }

    @Override
    public T set(T element, int index) {
        checkElementIndex(index);
        LinkedNode<T> x = node(index);
        T oldVal = x.data;
        x.data = element;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (LinkedNode<T> x = first; x != null; x = x.next) {
                if (x.data == null)
                    return index;
                index++;
            }
        } else {
            for (LinkedNode<T> x = first; x != null; x = x.next) {
                if (o.equals(x.data))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size;
        if (o == null) {
            for (LinkedNode<T> x = last; x != null; x = x.prev) {
                index--;
                if (x.data == null)
                    return index;
            }
        } else {
            for (LinkedNode<T> x = last; x != null; x = x.prev) {
                index--;
                if (o.equals(x.data))
                    return index;
            }
        }
        return -1;
    }

    @Override
    public ILinkedList<T> subList(int fromIndex, int toIndex) {
        checkSubPositionIndex(fromIndex, toIndex);
        ILinkedList<T> result = new LinkedList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            result.insert(find(i));
        }
        return result;
    }

    @Override
    public T findFirst(T key) throws ListItemNotFoundException {
        if (key == null) {
            throw new ListItemNotFoundException("查找失败");
        }
        LinkedNode<T> f = first;
        while (f != null) {
            if (key.equals(f.data)) {
                return f.data;
            }
            f = f.next;
        }
        throw new ListItemNotFoundException();
    }

    @Override
    public ILinkedList<T> findAll(T key) {
        if (key == null) {
            throw new ListItemNotFoundException("查找失败");
        }
        LinkedNode<T> f = first;
        ILinkedList<T> result = new LinkedList<>();
        while (f != null) {
            if (key.equals(f.data)) {
                result.insert(f.data);
            }
            f = f.next;
        }
        return result;
    }

    @Override
    public void removeFirst(T key) {
       if (key == null) {
           throw new ListItemRemoveException("删除失败");
       }
       LinkedNode<T> f = first;
       removeNext(key, f);
    }

    @Override
    public void removeAll(T key) {
        if (key == null) {
            throw new ListItemRemoveException("删除失败");
        }
        LinkedNode<T> f = first;
        LinkedNode<T> next = removeNext(key, f);
        while (next != null) {
            next = removeNext(key, f);
        }
    }
    
    private static class LinkedNode<T> {
        
        /**
         * 数据元素
         */
        T data;
        
        /**
         * 唯一的后继结点
         */
        LinkedNode<T> next;
        
        /**
         * 唯一的前驱结点
         */
        LinkedNode<T> prev;

        /**
         * 唯一构造器
         * @param data
         * @param next
         * @param prev
         */
        LinkedNode(LinkedNode<T> prev, T data, LinkedNode<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
        
    }
    
    private boolean isSubPositionIndex(int fromIndex, int toIndex) {
        return fromIndex <= toIndex && isElementIndex(fromIndex) && isElementIndex(toIndex);
    }
    
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
    
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
    
    private void checkSubPositionIndex(int fromIndex, int toIndex) {
        if (!isSubPositionIndex(fromIndex, toIndex))
            throw new IndexOutOfBoundsException("找不到元素");
    }
    
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException("找不到元素");
    }
    
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new ListItemNotFoundException("找不到元素");
    }
    
    private void linkFirst(T t) {
        final LinkedNode<T> f = first;
        final LinkedNode<T> newNode = new LinkedNode<>(null, t, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    /**
     * Links e as last element.
     */
    void linkLast(T t) {
        final LinkedNode<T> l = last;
        final LinkedNode<T> newNode = new LinkedNode<>(l, t, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }
    
    private T unlinkFirst(LinkedNode<T> f) {
        // assert f == first && f != null;
        final T element = f.data;
        final LinkedNode<T> next = f.next;
        f.data = null;
        //可以更容易被垃圾回收
        f.next = null;
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        return element;
    }
    
    private T unlinkLast(LinkedNode<T> l) {
        // assert l == last && l != null;
        final T element = l.data;
        final LinkedNode<T> prev = l.prev;
        l.data = null;
        //可以更容易被垃圾回收
        l.prev = null;
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        return element;
    }
    
    private T unlink(LinkedNode<T> x) {
        // assert x != null;
        final T element = x.data;
        final LinkedNode<T> next = x.next;
        final LinkedNode<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.data = null;
        size--;
        return element;
    }
    
    private void linkBefore(T e, LinkedNode<T> succ) {
        //断言结点非空
        final LinkedNode<T> pred = succ.prev;
        final LinkedNode<T> newNode = new LinkedNode<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        } 
        size++;
    }
    
    private LinkedNode<T> node(int index) {
        //断言isElementIndex(index);
        if (index < (size >> 1)) {
            LinkedNode<T> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            LinkedNode<T> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
    
    private LinkedNode<T> removeNext(T key, LinkedNode<T> f) {
        while (f != null) {
            if (key.equals(f.data)) {
                unlink(f);
                return f.next;
            }
            f = f.next;
        }
        return null;
     }
    
}
