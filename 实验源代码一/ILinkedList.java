public interface ILinkedList<T> {
    
    T getFirst();
    
    T getLast();
    
    T removeFirst();
    
    T removeLast();
    
    void addFirst(T t);

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    boolean insert(T t);

    void insert(T element, int index);

    boolean containsAll(ILinkedList<T> c);

    boolean insertAll(ILinkedList<T> c);

    boolean insertAll(ILinkedList<T> c, int index);

    void clear();

    T find(int index);

    T set(T element, int index);

    T remove(int index);

    int indexOf(Object o);

    int lastIndexOf(Object o);

    ILinkedList<T> subList(int fromIndex, int toIndex);
    
    T findFirst(T key) throws ListItemNotFoundException;
    
    ILinkedList<T> findAll(T key);
    
    void removeFirst(T key);
    
    void removeAll(T key);

}
