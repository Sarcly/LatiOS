package latiOS.util;

import java.util.Stack;

public class SizedStack<E> extends Stack<E> {
	
	private static final long serialVersionUID = 1L;
	
	private int maxSize;

    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public E push(E object) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }
}
