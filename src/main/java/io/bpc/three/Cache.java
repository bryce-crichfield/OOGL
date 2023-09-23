package io.bpc.three;

import java.util.function.Supplier;

public class Cache<T> {
    private final Supplier<T> supplier;
    private T value = null;

    public Cache(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (value == null) {
            value = supplier.get();
        }

        return value;
    }

}
