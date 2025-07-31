package com.quangnv.msb.core.funtional;

import java.util.function.Function;

public interface Monad<E extends Monad, T> extends Functor<E, T> {
    <U> Monad<E, U> flatMap(Function<? super T, Monad<E, ? extends U>> mapper);
}
