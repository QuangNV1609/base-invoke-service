package com.quangnv.msb.core.funtional;

import java.util.function.Function;

public interface Functor<E extends Functor, T> {
    T get();

    <U> Functor<E, U> map(Function<? super T, ? extends U> mapper);
}
