package org.moodminds.reactive.context;

import org.moodminds.elemental.AbstractAssociation.AbstractImmutableAssociation;
import org.moodminds.elemental.Association;
import org.moodminds.elemental.KeyValue;
import org.moodminds.elemental.RandomMatch;
import org.moodminds.elemental.WrapMap.WrapEntry;
import reactor.util.context.Context;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

/**
 * A wrapping {@link Context} implementation of the {@link Association} context.
 */
public class WrapContext extends AbstractImmutableAssociation<Object, Object>
        implements RandomMatch, Serializable {

    private static final long serialVersionUID = -7953131836841982418L;

    /**
     * A wrapped {@link Context} holder field.
     */
    private final Context wrapped;

    /**
     * Construct the object with the given {@link Context} context instance.
     *
     * @param context the given {@link Context} context instance
     */
    public WrapContext(Context context) {
        this.wrapped = requireNonNull(context);
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
        return wrapped.get(key);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains() {
        return !wrapped.isEmpty();
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
        return wrapped.hasKey(key);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return wrapped.size();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Iterator<KeyValue<Object, Object>> iterator() {
        return wrapped.stream().<KeyValue<Object, Object>>map(WrapEntry::wrap).iterator();
    }
}
