package iterator;// BinaryTreeIterator.java
import model.BinaryTreeMyModel;

import java.util.Iterator;
import java.util.NoSuchElementException;

class BinaryTreeIterator implements Iterator<Integer> {
    private BinaryTreeMyModel tree;
    private int currentIndex;

    public BinaryTreeIterator(BinaryTreeMyModel tree) {
        this.tree = tree;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        while (currentIndex < tree.getTree().size() && tree.getTree().get(currentIndex) == 0) {
            currentIndex++;
        }
        return currentIndex < tree.getTree().size();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return tree.getTree().get(currentIndex++);
    }
}