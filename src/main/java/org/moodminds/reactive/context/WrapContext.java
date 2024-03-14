package org.moodminds.reactive.context;

import org.moodminds.elemental.AbstractAssociation;
import org.moodminds.elemental.Association;
import org.moodminds.elemental.Container;
import org.moodminds.elemental.EmptyIterator;
import org.moodminds.elemental.KeyValue;
import org.moodminds.elemental.RandomMatch;
import org.moodminds.elemental.SingleIterator;
import org.moodminds.elemental.WrapKeyValue;
import org.moodminds.elemental.WrapSpliterator;
import reactor.util.context.Context;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Spliterator;

import static java.util.Objects.requireNonNull;
import static java.util.Spliterator.DISTINCT;
import static java.util.Spliterator.IMMUTABLE;
import static org.moodminds.elemental.Pair.pair;

/**
 * A wrapping {@link Context} implementation of the {@link Association} context.
 */
public class WrapContext extends AbstractAssociation<Object, Object, KeyValue<Object, Object>>
        implements RandomMatch, Serializable {

    private static final long serialVersionUID = -7953131836841982418L;

    /**
     * A wrapped {@link Context} holder field.
     */
    private final Context context;

    /**
     * Construct the object with the given {@link Context} context instance.
     *
     * @param context the given {@link Context} context instance
     */
    public WrapContext(Context context) {
        this.context = requireNonNull(context);
    }

    /**
     * {@inheritDoc}
     *
     * @param key {@inheritDoc}
     * @return the non-null value to which the specified key is associated
     * @throws ClassCastException     {@inheritDoc}
     * @throws NullPointerException   if the specified key is {@code null}
     * @throws NoSuchElementException {@inheritDoc}
     * @param <R> {@inheritDoc}
     */
    @Override
    public <R> R get(Object key) {
        return context.get(key);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains() {
        return !context.isEmpty();
    }

    /**
     * {@inheritDoc}
     *
     * @param key {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return context.hasKey(key);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Container<Object> keys() {
        return new WrapKeysContainer();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Container<Object> values() {
        return new WrapValuesContainer();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return context.size();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Iterator<KeyValue<Object, Object>> iterator() {
        return context.stream().map(WrapKeyValue::wrap).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param key {@inheritDoc}
     * @param value {@inheritDoc}
     * @param hasEntry {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected Iterator<KeyValue<Object, Object>> iterator(Object key, Object value, boolean hasEntry) {
        return hasEntry ? SingleIterator.iterator(pair(key, value)) : EmptyIterator.iterator();
    }


    /**
     * Wrap Context keys Container.
     */
    protected class WrapKeysContainer extends AbstractKeysContainer implements RandomMatch {

        @Override public Spliterator<Object> spliterator() {
            return WrapSpliterator.wrap(WrapContext.this.context.stream()
                            .map(Entry::getKey).spliterator(),
                    ch -> ch | IMMUTABLE | DISTINCT); }
    }

    /**
     * Wrap Context values Container.
     */
    protected class WrapValuesContainer extends AbstractValuesContainer {

        @Override public Spliterator<Object> spliterator() {
            return WrapSpliterator.wrap(WrapContext.this.context.stream()
                            .map(Entry::getValue).spliterator(),
                    ch -> ch | IMMUTABLE); }
    }
}
