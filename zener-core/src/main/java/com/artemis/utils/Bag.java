package com.artemis.utils;

import com.artemis.Entity;

/**
 * Collection type a bit like ArrayList but does not preserve the order of its
 * entities, speedwise it is very good, especially suited for games.
 */

public interface Bag<E> extends ImmutableBag<E> {


    boolean remove(E e);

    void add(E e);

    void clear();
}
