package com.quangnv.msb.core.funtional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Maybe<T> extends Monad<Maybe<?>, T>, Value<Maybe<?>, T> {

    boolean isJust();

    static <E> Maybe<E> of(E value) {
        return value == null ? nothing() : new Just<>(value);
    }

    static <E> Maybe<E> of(Callable<E> callable) {
        try {
            E value = callable.call();
            return of(value);
        } catch (Exception e) {
            return nothing();
        }

    }

    static <E> Maybe<E> nothing() {
        return Nothing.cast(Nothing.EMPTY);
    }

    /**
     * Map from Maybe<T> to Maybe<U> by mapper function
     *
     * @param mapper mapper function accept T and return U
     * @param <U>
     * @return Maybe<U> if current value is present,
     * Nothing if current value is empty
     */
    default <U> Maybe<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);

        return isJust() ?
                Maybe.of(mapper.apply(get())) : nothing();
    }


    /**
     * It similar with map , but accept throwing function
     * and return nothing when exception occurs
     *
     * @param mapper
     * @param <U>
     * @return Maybe<U> if current value is present,
     * Nothing if current value is empty or an exception occurs.
     */
    default <U> Maybe<U> mapThrowingFunc(ThrowingFunction<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return isJust() ?
                Maybe.of(() -> mapper.apply(get())) : nothing();
    }

    /**
     * Convert Maybe<T> to Maybe<U> if value is empty
     *
     * @param supplier
     * @param <U>
     * @return Maybe<U> if value is empty,
     * Maybe<T> if value is present
     */
    default <U> Maybe<?> orAnother(Supplier<? extends U> supplier) {
        Objects.requireNonNull(supplier);
        return isJust() ? this : Maybe.of(supplier.get());
    }

    /**
     * Cast value type T to K
     *
     * @param thisClass class you want to cast from T
     * @param <K>       class to cast
     * @return Maybe<K> if value is an instance of type K,
     * Nothing if value is not an instance of type K
     */
    default <K> Maybe<K> castValueTo(Class<K> thisClass) {
        Objects.requireNonNull(thisClass);
        if (isJust() && thisClass.isInstance(get())) {
            return Maybe.of((K) get());
        }
        return nothing();
    }


    /**
     * Flatten map Maybe<T> to Maybe<U> by function that accept type T and return Maybe<U>
     *
     * @param mapper
     * @param <U>
     * @return Maybe<U>
     */
    default <U> Maybe<U> flatMap(Function<? super T, Monad<Maybe<?>, ? extends U>> mapper) {
        return isJust() ?
                of(mapper.apply(get()).get()) : nothing();
    }

    /**
     * If value is present use this value, otherwise use alternative value
     *
     * @param alternative
     * @return If value is present use this value, otherwise use alternative value
     */
    @Override
    default Maybe<T> or(Supplier<? extends Value<Maybe<?>, ? extends T>> alternative) {
        Objects.requireNonNull(alternative);
        return orUse(alternative.get());
    }

    /**
     * If value is present use this value, otherwise use alternative value
     *
     * @param alternative
     * @return If value is present use this value, otherwise use alternative value
     */
    @Override
    default Maybe<T> orUse(Value<Maybe<?>, ? extends T> alternative) {
        Objects.requireNonNull(alternative);
        return isJust() ? this : Value.Type.<Maybe<T>, Maybe<?>, T>narrow(alternative);
    }

    @Override
    default Maybe<T> peek(Consumer<? super T> consumer) {
        if (isJust()) {
            consumer.accept(this.get());
            return this;
        }
        return nothing();
    }

    /**
     * If value is present use this value, otherwise use alternative value
     *
     * @param alternative
     * @return If value is present use this value, otherwise use alternative value
     */
    @Override
    default Maybe<T> orLift(Supplier<? extends Maybe<?>> alternative) {
        Objects.requireNonNull(alternative);
        return orUse((Maybe<T>) alternative.get());
    }

    /**
     * If value does not satisfy condition then return nothing
     *
     * @param predicate
     * @return this when value satisfy the condition, else return nothing
     */
    default Maybe<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (isJust()) {
            return predicate.test(get()) ? this : Nothing.cast(this);
        }
        return nothing();
    }

    class Nothing<T> implements Maybe<T> {

        private static final Nothing<?> EMPTY = new Nothing<>();

        private Nothing() {

        }

        private static <O, E> Nothing<O> cast(Maybe<E> maybe) {
            return (Nothing<O>) maybe;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public T get() {
            throw new NoSuchElementException("No such element");
        }

        @Override
        public boolean isJust() {
            return false;
        }
    }

    class Just<T> implements Maybe<T> {
        private final T value;

        private Just(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean isJust() {
            return true;
        }

        @Override
        public boolean isEmpty() {
            return value == null;
        }
    }
}
