package org.moodminds.reactive;

import org.moodminds.function.Executable;
import org.moodminds.function.Executable1;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 * A {@link Flux} implementation of the {@link Publishable} interface.
 *
 * @param <V> the type of item values
 * @param <E> the type of potential exceptions
 */
public class FluxPublishable<V, E extends Exception> extends Flux<V> implements Publishable<V, E> {

    /**
     * A wrapped {@link Flux} holder field.
     */
    private final Flux<V> flux;

    /**
     * Construct the object with the given {@link Flux} instance.
     *
     * @param flux the given {@link Flux} instance
     * @throws NullPointerException if the {@link Flux} specified is {@code null}
     */
    protected FluxPublishable(Flux<V> flux) {
        this.flux = requireNonNull(flux);
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     */
    @Override
    public void subscribe(CoreSubscriber<? super V> subscriber) {
        flux.subscribe(subscriber);
    }

    /**
     * Subscribe a {@link Executable1} to this {@link Flux}, allowing it to consume all the
     * elements in the sequence. This subscription requests an unbounded demand ({@code Long.MAX_VALUE}).
     *
     * @param consumer the consumer to be invoked on each value (onNext signal)
     *
     * @return a new {@link Disposable} that can be used to cancel the underlying {@link org.reactivestreams.Subscription}
     */
    public Disposable subscribe(Executable1<? super V> consumer) {
        return subscribe((Consumer<? super V>) consumer);
    }

    /**
     * Subscribe to this {@link Flux} using a {@link Executable1} to consume each element in the sequence
     * and a {@link Executable1} to handle errors. This subscription requests an unbounded demand ({@code Long.MAX_VALUE}).
     *
     * @param consumer the consumer to be invoked on each next signal
     * @param errorConsumer the consumer to be invoked on error signals
     *
     * @return a new {@link Disposable} that can be used to cancel the underlying {@link org.reactivestreams.Subscription}
     */
    public Disposable subscribe(Executable1<? super V> consumer, Executable1<? super Throwable> errorConsumer) {
        return subscribe((Consumer<? super V>) consumer, errorConsumer);
    }

    /**
     * Subscribe a set of {@link Executable1} to this {@link Flux} for consuming elements, handling errors,
     * and reacting to completion by the {@link Executable}. The subscription will request unbounded demand
     * ({@code Long.MAX_VALUE}).
     *
     * @param consumer the consumer to be invoked on each value
     * @param errorConsumer the consumer to be invoked on error signals
     * @param completeConsumer the consumer to be invoked on complete signals
     *
     * @return a new {@link Disposable} that can be used to cancel the underlying {@link org.reactivestreams.Subscription}
     */
    public Disposable subscribe(Executable1<? super V> consumer, Executable1<? super Throwable> errorConsumer, Executable completeConsumer) {
        return subscribe((Consumer<? super V>) consumer, errorConsumer, completeConsumer);
    }

    /**
     * Subscribe a set of {@link Executable1} to this {@link Flux} for consuming elements, handling errors,
     * and reacting to completion by the {@link Executable}, with an associated {@link Context}. The subscription
     * will request unbounded demand ({@code Long.MAX_VALUE}).
     *
     * @param consumer the consumer to be invoked on each value
     * @param errorConsumer the consumer to be invoked on error signals
     * @param completeConsumer the consumer to be invoked on complete signals
     * @param initialContext the initial {@link Context} tied to the subscription, visible to upstream operators
     *
     * @return a new {@link Disposable} that can be used to cancel the underlying {@link org.reactivestreams.Subscription}
     */
    public Disposable subscribe(Executable1<? super V> consumer, Executable1<? super Throwable> errorConsumer, Executable completeConsumer, reactor.util.context.Context initialContext) {
        return subscribe((Consumer<? super V>) consumer, errorConsumer, completeConsumer, initialContext);
    }

    /**
     * Return a FluxPublishable by the given {@link Flux}.
     *
     * @param flux the given {@link Flux}
     * @param <V> the type of item values
     * @param <E> the type of potential exceptions
     * @return a FluxPublishable by the given {@link Flux}
     * @throws NullPointerException if the {@link Flux} specified is {@code null}
     */
    public static <V, E extends Exception> FluxPublishable<V, E> flux(Flux<V> flux) {
        return new FluxPublishable<>(flux);
    }
}
