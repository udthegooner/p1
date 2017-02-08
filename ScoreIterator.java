import java.util.NoSuchElementException;

public class ScoreIterator<E> implements ScoreIteratorADT<Score> {
	private ScoreList myLists;
	private int currItems;

	public ScoreIterator (ScoreList myLists){
		this.myLists = myLists;
		this.currItems = 0;
	}
	@Override
	public boolean hasNext() {
		return currItems< myLists.size();
	}

	@Override
	public Score next() {
		if (!hasNext()) throw new NoSuchElementException();
		Score result = myLists.get(currItems);
		currItems++;
		return result;
	}

}
