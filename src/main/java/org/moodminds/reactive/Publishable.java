package org.moodminds.reactive;

import org.moodminds.elemental.Association;
import org.moodminds.elemental.Container;
import org.moodminds.elemental.KeyValue;
import org.moodminds.reactive.context.WrapContext;
import org.moodminds.function.Executable1Throwing1;
import org.moodminds.function.ExecutableThrowing1;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.moodminds.elemental.ArraySequence.sequence;
import static org.moodminds.function.Executable1Throwing1.idle;
import static reactor.util.context.Context.of;

/**
 * An extension of the {@link SubscribeSupport} interface, this class is a specialized {@link CorePublisher}
 * providing default implementations for the methods defined in {@link SubscribeSupport}.
 *
 * @param <V> the type of item values
 * @param <E> the type of potential exceptions
 */
public interface Publishable<V, E extends Exception> extends SubscribeSupport<V, E>, CorePublisher<V> {

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     */
    @Override
    default void subscribe(org.reactivestreams.Subscriber<? super V> subscriber) {
        if (subscriber instanceof CoreSubscriber)
            subscribe((CoreSubscriber<? super V>) subscriber);
        else subscribe(subscriber, new KeyValue<?, ?>[]{});
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx        {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    default void subscribe(org.reactivestreams.Subscriber<? super V> subscriber, KeyValue<?, ?>... ctx) {
        subscribe((CoreSubscriber<? super V>) subscriber(subscriber, sequence(ctx)));
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx        {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    default void subscribe(SubscribeSupport.Subscriber<? super V, ? super E> subscriber, KeyValue<?, ?>... ctx) {
        subscribe((CoreSubscriber<? super V>) subscriber(subscriber, sequence(ctx)));
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx        {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    default void subscribe(org.reactivestreams.Subscriber<? super V> subscriber, Association<?, ?, ?> ctx) {
        subscribe((CoreSubscriber<? super V>) subscriber(subscriber, ctx));
    }

    /**
     * {@inheritDoc}
     *
     * @param subscriber {@inheritDoc}
     * @param ctx        {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    default void subscribe(SubscribeSupport.Subscriber<? super V, ? super E> subscriber, Association<?, ?, ?> ctx) {
        subscribe((CoreSubscriber<? super V>) subscriber(subscriber, ctx));
    }

    /**
     * Subscribe to this Publishable with the specified item consumer and {@link KeyValue key-value array context}.
     *
     * @param itemConsumer the given item consumer
     * @param ctx          the given {@link KeyValue key-value array context}
     * @throws NullPointerException if any of the specified events consumers is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer, KeyValue<?, ?>... ctx) {
        subscribe(itemConsumer, idle(), idle(), ExecutableThrowing1.idle(), ctx);
    }

    /**
     * Subscribe to this Publishable with the specified item consumer and {@link Association context}.
     *
     * @param itemConsumer the given item consumer
     * @param ctx          the given {@link Association context}
     * @throws NullPointerException if any of the specified events consumers or {@link Association context} is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer, Association<?, ?, ?> ctx) {
        subscribe(itemConsumer, idle(), idle(), ExecutableThrowing1.idle(), ctx);
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link KeyValue key-value array context}.
     *
     * @param itemConsumer  the given item consumer
     * @param errorConsumer the given {@link Throwable} consumer
     * @param ctx           the given {@link KeyValue key-value array context}
     * @throws NullPointerException if any of the specified events consumers is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           KeyValue<?, ?>... ctx) {
        subscribe(itemConsumer, idle(), errorConsumer, ExecutableThrowing1.idle(), ctx);
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link Association context}.
     *
     * @param itemConsumer  the given item consumer
     * @param errorConsumer the given {@link Throwable} consumer
     * @param ctx           the given {@link Association context}
     * @throws NullPointerException if any of the specified events consumers or {@link Association context} is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           Association<?, ?, ?> ctx) {
        subscribe(itemConsumer, idle(), errorConsumer, ExecutableThrowing1.idle(), ctx);
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link KeyValue key-value array context}.
     *
     * @param itemConsumer  the given item consumer
     * @param faultConsumer the given {@link E} consumer
     * @param errorConsumer the given {@link Throwable} consumer
     * @param ctx           the given {@link KeyValue key-value array context}
     * @throws NullPointerException if any of the specified events consumers is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super E, ? extends RuntimeException> faultConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           KeyValue<?, ?>... ctx) {
        subscribe(itemConsumer, faultConsumer, errorConsumer, ExecutableThrowing1.idle(), ctx);
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link Association context}.
     *
     * @param itemConsumer  the given item consumer
     * @param faultConsumer the given {@link E} consumer
     * @param errorConsumer the given {@link Throwable} consumer
     * @param ctx           the given {@link Association context}
     * @throws NullPointerException      if any of the specified events consumers or {@link Association context} is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super E, ? extends RuntimeException> faultConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           Association<?, ?, ?> ctx) {
        subscribe(itemConsumer, faultConsumer, errorConsumer, ExecutableThrowing1.idle(), ctx);
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link KeyValue key-value array context}.
     *
     * @param itemConsumer     the given item consumer
     * @param errorConsumer    the given {@link Throwable} consumer
     * @param completeConsumer the given completion event executor
     * @param context          the given {@link KeyValue key-value array context}
     * @throws NullPointerException if any of the specified events consumers is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           ExecutableThrowing1<? extends RuntimeException> completeConsumer,
                           KeyValue<?, ?>... context) {
        subscribe(itemConsumer, idle(), errorConsumer, completeConsumer, context);
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link Association context}.
     *
     * @param itemConsumer     the given item consumer
     * @param errorConsumer    the given {@link Throwable} consumer
     * @param completeConsumer the given completion event executor
     * @param ctx              the given {@link Association context}
     * @throws NullPointerException if any of the specified events consumers or {@link Association context} is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           ExecutableThrowing1<? extends RuntimeException> completeConsumer,
                           Association<?, ?, ?> ctx) {
        subscribe(itemConsumer, idle(), errorConsumer, completeConsumer, ctx);
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link KeyValue key-value array context}.
     *
     * @param itemConsumer     the given item consumer
     * @param faultConsumer    the given {@link E} consumer
     * @param errorConsumer    the given {@link Throwable} consumer
     * @param completeConsumer the given completion event executor
     * @param ctx              the given {@link KeyValue key-value array context}
     * @throws NullPointerException      if any of the specified events consumers is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super E, ? extends RuntimeException> faultConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           ExecutableThrowing1<? extends RuntimeException> completeConsumer,
                           KeyValue<?, ?>... ctx) {
        requireNonNull(itemConsumer); requireNonNull(errorConsumer);
        requireNonNull(faultConsumer); requireNonNull(completeConsumer);
        subscribe((CoreSubscriber<? super V>) subscriber(itemConsumer, faultConsumer, errorConsumer, completeConsumer, sequence(ctx)));
    }

    /**
     * Subscribe to this Publishable with the specified events consumers and {@link Association context}.
     *
     * @param itemConsumer     the given item consumer
     * @param faultConsumer    the given {@link E} consumer
     * @param errorConsumer    the given {@link Throwable} consumer
     * @param completeConsumer the given completion event executor
     * @param ctx              the given {@link Association context}
     * @throws NullPointerException      if any of the specified events consumers or {@link Association context} is {@code null}
     */
    default void subscribe(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                           Executable1Throwing1<? super E, ? extends RuntimeException> faultConsumer,
                           Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                           ExecutableThrowing1<? extends RuntimeException> completeConsumer,
                           Association<?, ?, ?> ctx) {
        requireNonNull(itemConsumer); requireNonNull(errorConsumer);
        requireNonNull(faultConsumer); requireNonNull(completeConsumer);
        subscribe((CoreSubscriber<? super V>) subscriber(itemConsumer, faultConsumer, errorConsumer, completeConsumer, ctx));
    }


    /**
     * A {@link CoreSubscriber} extension of the {@link SubscribeSupport.Subscriber} interface.
     *
     * @param <V> the type of items to consume
     * @param <E> the type of exceptions to consume
     */
    interface Subscriber<V, E extends Exception> extends CoreSubscriber<V>, SubscribeSupport.Subscriber<V, E> {}


    /**
     * Return a Subscriber by the given {@link SubscribeSupport.Subscriber}
     * and {@link Container} of {@link KeyValue key-values} context.
     *
     * @param subscriber the given {@link SubscribeSupport.Subscriber}
     * @param ctx        the given {@link Container} of {@link KeyValue key-values} context
     * @param <V>        the type of items to consume
     * @param <E>        the type of exceptions to consume
     * @return a Subscriber by the given {@link SubscribeSupport.Subscriber}
     * and {@link Container} of {@link KeyValue key-values} context
     * @throws NullPointerException is the specified {@link SubscribeSupport.Subscriber}
     *                              or the {@link Container} context is {@code null}
     */
    static <V, E extends Exception> Subscriber<V, E> subscriber(SubscribeSupport.Subscriber<? super V, ? super E> subscriber, Container<? extends KeyValue<?, ?>> ctx) {
        requireNonNull(subscriber); Context context = of(ctx.stream().collect(toMap(KeyValue::getKey, KeyValue::getValue)));
        return new Subscriber<V, E>() {
            @Override public void onSubscribe(org.reactivestreams.Subscription s) { subscriber.onSubscribe(s); }
            @Override public void onNext(V v) { subscriber.onNext(v); }
            @Override public void onError(E error) { subscriber.onError(error); }
            @Override @SuppressWarnings("unchecked") public void onError(Throwable error) {
                try { subscriber.onError((E) error); }
                catch (ClassCastException e) { subscriber.onError(error); } }
            @Override public void onComplete() { subscriber.onComplete(); }
            @Override public Context currentContext() { return context; }
        };
    }

    /**
     * Return a Subscriber by the given {@link org.reactivestreams.Subscriber}
     * and {@link Container} of {@link KeyValue key-values} context.
     *
     * @param subscriber the given {@link org.reactivestreams.Subscriber}
     * @param ctx        the given {@link Container} of {@link KeyValue key-values} context
     * @param <V>        the type of items to consume
     * @param <E>        the type of exceptions to consume
     * @return a Subscriber by the given {@link org.reactivestreams.Subscriber}
     * and {@link Container} of {@link KeyValue key-values} context
     * @throws NullPointerException is the specified {@link org.reactivestreams.Subscriber}
     *                              or the {@link Container} context is {@code null}
     */
    static <V, E extends Exception> Subscriber<V, E> subscriber(org.reactivestreams.Subscriber<? super V> subscriber, Container<? extends KeyValue<?, ?>> ctx) {
        requireNonNull(subscriber); Context context = of(ctx.stream().collect(toMap(KeyValue::getKey, KeyValue::getValue)));
        return new Subscriber<V, E>() {
            @Override public void onSubscribe(org.reactivestreams.Subscription s) { subscriber.onSubscribe(s); }
            @Override public void onNext(V v) { subscriber.onNext(v); }
            @Override public void onError(E error) { subscriber.onError(error); }
            @Override public void onError(Throwable t) { subscriber.onError(t); }
            @Override public void onComplete() { subscriber.onComplete(); }
            @Override public Context currentContext() { return context; }
        };
    }

    /**
     * Return a Subscriber by the given {@link CoreSubscriber}.
     *
     * @param subscriber the given {@link CoreSubscriber}
     * @param <V>        the type of items to consume
     * @param <E>        the type of exceptions to consume
     * @return a Subscriber by the given {@link CoreSubscriber}
     * @throws NullPointerException is the specified {@link CoreSubscriber} is {@code null}
     */
    static <V, E extends Exception> Subscriber<V, E> subscriber(CoreSubscriber<V> subscriber) {
        requireNonNull(subscriber); return new Subscriber<V, E>() {
            @Override public void onSubscribe(Subscription s) { subscriber.onSubscribe(s); }
            @Override public void onNext(V v) { subscriber.onNext(v); }
            @Override public void onError(E error) { subscriber.onError(error); }
            @Override public void onError(Throwable error) { subscriber.onError(error); }
            @Override public void onComplete() { subscriber.onComplete(); }
            @Override public Context currentContext() { return subscriber.currentContext(); }
        };
    }

    /**
     * Return a Subscriber by the given events consumers and {@link Container}
     * of {@link KeyValue key-values} context.
     *
     * @param itemConsumer     the given item consumer
     * @param faultConsumer    the given {@link Exception} consumer
     * @param errorConsumer    the given {@link Throwable} consumer
     * @param completeConsumer the given completion event executor
     * @param ctx              the given {@link Container} of {@link KeyValue key-value} context
     * @param <V>              the type of items to consume
     * @param <E>              the type of exceptions to consume
     * @return a Subscriber by the given events consumers and {@link Container} of {@link KeyValue key-values} context
     * @throws NullPointerException is any of the specified events consumers is {@code null}
     */
    static <V, E extends Exception> Subscriber<V, E> subscriber(Executable1Throwing1<? super V, ? extends RuntimeException> itemConsumer,
                                                                Executable1Throwing1<? super E, ? extends RuntimeException> faultConsumer,
                                                                Executable1Throwing1<? super Throwable, ? extends RuntimeException> errorConsumer,
                                                                ExecutableThrowing1<? extends RuntimeException> completeConsumer,
                                                                Container<? extends KeyValue<?, ?>> ctx) {
        requireNonNull(itemConsumer); requireNonNull(errorConsumer);
        requireNonNull(faultConsumer); requireNonNull(completeConsumer);
        Context context = of(ctx.stream().collect(toMap(KeyValue::getKey, KeyValue::getValue)));
        return new Subscriber<V, E>() {
            @Override public void onSubscribe(Subscription subscription) { subscription.request(Long.MAX_VALUE); }
            @Override public void onNext(V item) { itemConsumer.exec(item); }
            @Override public void onError(E error) { faultConsumer.exec(error); }
            @Override @SuppressWarnings("unchecked") public void onError(Throwable error) {
                try { faultConsumer.exec((E) error); }
                catch (ClassCastException e) { errorConsumer.exec(error); } }
            @Override public void onComplete() { completeConsumer.exec(); }
            @Override public Context currentContext() { return context; }
        };
    }


    /**
     * Return an empty Publishable, executing and emitting nothing.
     *
     * @param <V> the type of items to consume
     * @param <E> the type of exceptions to consume
     * @return an empty Publishable, executing and emitting nothing
     */
    static <V, E extends Exception> Publishable<V, E> publishable() {
        return subscriber -> {
            subscriber.onSubscribe(new Subscription() {
                @Override public void request(long n) {}
                @Override public void cancel() {}
            });
            subscriber.onComplete(); };
    }

    /**
     * Return a Publishable by the given {@link SubscribeSupport}.
     *
     * @param subscribeSupport the given {@link SubscribeSupport}
     * @param <V>              the type of items to consume
     * @param <E>              the type of exceptions to consume
     * @return a Publishable by the given {@link SubscribeSupport}
     * @throws NullPointerException is the specified {@link SubscribeSupport} is {@code null}
     */
    static <V, E extends Exception> Publishable<V, E> publishable(SubscribeSupport<? extends V, ? extends E> subscribeSupport) {
        return subscriber -> subscribeSupport.subscribe(subscriber(subscriber), new WrapContext(subscriber.currentContext()));
    }
}
