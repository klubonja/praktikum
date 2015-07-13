package staticClasses;

/**
 * A circular buffer. This is practically a fixed size FIFO queue. Which means you can enter a maximum amount of overall elements and can get the oldest.
 */
public class CircularBuffer<T> {
    private final T[] buffer;
    private int headIndex = 0;
    private int tailIndex = 0;
    private int numberOfElements = 0;

    public CircularBuffer(final int size) {
        buffer = newArray(size);
    }

    @SuppressWarnings("unchecked")
    // generics and arrays do not mix. we should be safe here.
    private T[] newArray(final int length) {
        return (T[]) new Object[length];
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public boolean isFull() {
        return numberOfElements == buffer.length;
    }

    /** Returns the current number of elements inside the buffer */
    public int getSize() {
        return numberOfElements;
    }

    /**
     * Inserts the given element.
     * @throws IllegalStateException if buffer has not enough free space
     */
    public void add(final T newElement) {
        if (isFull())
            throw new IllegalStateException("buffer full");

        buffer[headIndex] = newElement;
        headIndex = (headIndex + 1) % buffer.length;
        ++numberOfElements;
    }

    /**
     * Inserts the given elements.
     * @throws IllegalStateException if buffer has not enough free space
     */
    public void addAll(final T[] newElements) {
        if (newElements.length + numberOfElements > buffer.length)
            throw new IllegalStateException("insufficient space to insert array");

        // or: call add() method for all elements. Which should be the favored way.
        final int elementsToFillAtHead = Math.min(newElements.length, buffer.length - headIndex);
        copyNumberOfElementsFromArraySourceStartToBuffer(elementsToFillAtHead, newElements, 0);
        final int rest = newElements.length - elementsToFillAtHead;
        copyNumberOfElementsFromArraySourceStartToBuffer(rest, newElements, elementsToFillAtHead);
        numberOfElements += newElements.length;
    }

    private void copyNumberOfElementsFromArraySourceStartToBuffer(final int numberOfElements, final T[] arraySource, final int sourceStart) {
        System.arraycopy(arraySource, sourceStart, buffer, headIndex, numberOfElements);
        headIndex = (headIndex + numberOfElements) % buffer.length;
    }

    /** 
     * Get and remove the oldest element
     * @throws  IllegalStateException if buffer is empty
     */
    public T removeOldest() {
        if (isEmpty())
            throw new IllegalStateException("buffer empty");

        final T oldest = buffer[tailIndex];
        tailIndex = (tailIndex + 1) % buffer.length;
        --numberOfElements;
        return oldest;
    }
}