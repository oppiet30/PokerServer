package com.hyphenated.card.holder;

import com.hyphenated.card.Card;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardIteratorTest {
    @Test
    public void preservesOrderAndSkipsEmptyHolders() {
        Card first = Card.values()[0];
        Card second = Card.values()[1];
        Iterator<Card> iterator = new CardIterator(new CardHolder(),
                new CardHolder(first), new CardHolder(), new CardHolder(second));
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertSame(first, iterator.next());
        assertSame(second, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyIteratorIsExhausted() {
        new CardIterator().next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cardHolderIteratorRemainsUnmodifiable() {
        Iterator<Card> iterator = new CardHolder(Card.values()[0]).iterator();
        iterator.next();
        iterator.remove();
    }
}
