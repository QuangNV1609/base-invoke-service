package com.quangnv.msb.core.funtional;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Value<E extends Value, T> {
    boolean isEmpty();

    T get();

    Value<E, T> or(Supplier<? extends Value<E, ? extends T>> supplier);

    Value<E, T> orUse(Value<E, ? extends T> alternative);

    Value<E, T> orLift(Supplier<? extends E> alternative);


    default T orElse(T alternative) {
        return isEmpty() ? alternative : get();
    }

    default T orElse(Supplier<T> alternative) {
        return orElse(alternative.get());
    }


    default T orElse(Runnable runnable) {
        if (isEmpty()) {
            runnable.run();
            return orElseNull();
        }
        return get();
    }

    default T orElseNull() {
        return orElse(() -> null);
    }

    default <X extends Exception> T orElseThrow(X exception) throws X {
        if (isEmpty()) {
            throw exception;
        }
        return get();
    }


    default <X extends Exception> T orElseSupplyException(Supplier<X> exception) throws X {
        Objects.requireNonNull(exception);
        return orElseThrow(exception.get());
    }


    default Value<E, T> peek(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        if (!isEmpty()) {
            consumer.accept(this.get());
        }
        return this;
    }

    interface Type {
        static <O extends Value<E, T>, E extends Value, T> O narrow(Value<E, ? extends T> higher) {
            return (O) higher;
        }
    }
}
