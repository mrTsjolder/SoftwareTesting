import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Team12 (Mayer, Pfoser, Hoedt)
 *
 */
public class RingBufferTest {

    /**
     * Capacity for the buffer that is used in the tests.
     * In order to avoid trivial buffers, it should not be less than 2.
     */
	private static final int SIZE = 10;
    /**
     * Default element to add to the buffer that can be used in the tests.
     * The tests were designed assuming that this element is not {@code null}.
     */
    private static final String ELEMENT = "item";

    private RingBuffer<String> emptyBuffer;

    /** Provides a new empty buffer for each test. */
    @Before
    public void setUp() {
        emptyBuffer = new RingBuffer<>(SIZE);
    }

    /** Test normal initialisation of RingBuffer. */
	@Test
	public void testRingBufferInitialization() {
		RingBuffer<String> buffer = new RingBuffer<>(SIZE);

		assertEquals(0, buffer.size());
		assertTrue(buffer.isEmpty());
	}

    /**
     * Tests initialisation of empty RingBuffer.
     * The assumption is that a (useless) buffer is expected upon return.
     */
    @Test
    public void testRingBufferInitializationZeroCapacity() {
        RingBuffer<String> buffer = new RingBuffer<>(0);

        assertEquals(0, buffer.size());
        assertTrue(buffer.isEmpty());
    }

    /**
     * Tests initialisation of empty RingBuffer.
     * The assumption is that a (possibly useless) buffer is expected upon return.
     */
	@Test
	public void testRingBufferInitializationNegativeCapacity() {
        RingBuffer<String> buffer = new RingBuffer<>(-SIZE);

        assertEquals(0, buffer.size());
        assertTrue(buffer.isEmpty());
    }

    /** Tests enqueue operation of the buffer. */
    @Test
    public void testEnqueue() throws RingBufferException {
        for(int i = 1; i <= SIZE; i++) {
            emptyBuffer.enqueue(ELEMENT);
            assertEquals(i, emptyBuffer.size());
        }

        assertFalse(emptyBuffer.isEmpty());
    }

    /**
     * Tests enqueue operation of {@code null} element.
     * The assumption is that {@code null} elements should not cause any trouble.
     */
    @Test
    public void testEnqueueNull() throws RingBufferException {
        emptyBuffer.enqueue(null);

        assertEquals(1, emptyBuffer.size());
        assertFalse(emptyBuffer.isEmpty());
    }

    /** Tests dequeue operation of the buffer. */
    @Test
    public void testDequeue() throws RingBufferException {
        // fill empty buffer
        for(int i = 0; i < SIZE; i++) {
            emptyBuffer.enqueue(ELEMENT + i);
        }

        // make sure the enqueues did their job
        assertEquals(SIZE, emptyBuffer.size());
        assertFalse(emptyBuffer.isEmpty());

        for(int i = 0; i < SIZE / 2; i++) {
            assertEquals(ELEMENT + i, emptyBuffer.dequeue());
        }
    }

    /** Tests if underflow is correctly detected. */
	@Test(expected = RingBufferException.class)
	public void testRingBufferUnderflow() throws RingBufferException {
		emptyBuffer.dequeue(); // should throw RingBufferException
	}

    /** Tests if overflow is correctly detected. */
    @Test(expected = RingBufferException.class)
    public void testRingBufferOverflow() throws RingBufferException {
        // fill buffer
        for (int i = 0; i < SIZE; ++i) {
            try {
                emptyBuffer.enqueue(ELEMENT);
            } catch (RingBufferException e) {
                // exception only expected when emptyBuffer is already full
                fail("Unexpected Buffer overflow");
            }
        }

        emptyBuffer.enqueue(ELEMENT);
    }

    /** Tests if the iterator over the buffer works as expected. */
    @Test
    public void testRingBufferIterator() throws RingBufferException {
        // fill buffer
        for(int i = 0; i < SIZE; i++) {
            emptyBuffer.enqueue(ELEMENT + i);
        }

        // explicit use of iterator
        Iterator<String> it = emptyBuffer.iterator();
        for(int i = 0; i < SIZE; i++) {
            assertTrue(it.hasNext());
            assertEquals(ELEMENT + i, it.next());
        }

        // implicit use of iterator
        int i = 0;
        for(String test : emptyBuffer) {
            assertEquals(ELEMENT + i++, test);
        }
    }

    /** Tests if improper use of the iterator behaves as expected */
    @Test(expected = NoSuchElementException.class)
    public void testRingBufferIteratorImproperUse() throws RingBufferException {
        emptyBuffer.iterator().next();
    }
}
