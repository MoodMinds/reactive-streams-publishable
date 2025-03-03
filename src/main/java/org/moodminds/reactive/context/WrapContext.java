package org.moodminds.reactive.context;

import org.moodminds.elemental.AbstractAssociation;
import org.moodminds.elemental.AbstractKeyValue;
import org.moodminds.elemental.Association;
import org.moodminds.elemental.Container;
import org.moodminds.elemental.KeyValue;
import org.moodminds.elemental.OptionalIterator;
import org.moodminds.elemental.RandomMatch;
import org.moodminds.elemental.WrapKeyValue;
import reactor.util.context.Context;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Spliterator;

import static java.util.Objects.requireNonNull;

/**
 * A wrapping {@link Context} implementation of the {@link Association} context.
 */
public class WrapContext extends AbstractAssociation<Object, Object, KeyValue<?, ?>>
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
        return new KeysContainer();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Container<Object> values() {
        return new ValuesContainer();
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
    public Iterator<KeyValue<?, ?>> iterator() {
        return context.stream().<KeyValue<?, ?>>map(WrapKeyValue::wrap).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param key {@inheritDoc}
     * @param value {@inheritDoc}
     * @param present {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected Iterator<KeyValue<?, ?>> iterator(Object key, Object value, boolean present) {
        return OptionalIterator.iterator(() -> new AbstractKeyValue<Object, Object>() {
            @Override public Object getKey() { return key; }
            @Override public Object getValue() { return value; }
        }, present);
    }


    /**
     * Wrap Context keys Container implementation.
     */
    protected class KeysContainer extends AbstractKeysContainer implements RandomMatch {

        @Override public Spliterator<Object> spliterator() {
            return WrapContext.this.context.stream().parallel().map(Entry::getKey).spliterator(); }
    }

    /**
     * Wrap Context values Container implementation.
     */
    protected class ValuesContainer extends AbstractValuesContainer {

        @Override public Spliterator<Object> spliterator() {
            return WrapContext.this.context.stream().parallel().map(Entry::getValue).spliterator(); }
    }
}
