package confictura.util;

import arc.func.*;

import java.util.*;

/**
 * Struct utilities, providing some stateless iterative utilities such as reduce.
 * @author GlennFolker
 */
@SuppressWarnings("unchecked")
public final class StructUtils{
    private static final Empty<?> empty = new Empty<>();

    private StructUtils(){
        throw new AssertionError();
    }

    public static <T> Empty<T> empty(){
        // SAFETY: Has no references or casts to T, so type erasure shouldn't mess everything up.
        return (Empty<T>)empty;
    }

    public static <T> Iter<T> iter(T[] array){
        return iter(array, 0, array.length);
    }

    public static <T> Iter<T> iter(T[] array, int offset, int length){
        return new Iter<>(array, offset, length);
    }

    public static <T> Chain<T> chain(Iterator<T> first, Iterator<T> second){
        return new Chain<>(first, second);
    }

    public static <T, R> R reduce(T[] array, R initial, Func2<T, R, R> reduce){
        for(var item : array) initial = reduce.get(item, initial);
        return initial;
    }

    public static <T> int reducei(T[] array, int initial, Reducei<T> reduce){
        for(var item : array) initial = reduce.get(item, initial);
        return initial;
    }

    public static <T> int sumi(T[] array, Intf<T> extract){
        return reducei(array, 0, (item, accum) -> accum + extract.get(item));
    }

    public static <T> float reducef(T[] array, float initial, Reducef<T> reduce){
        for(var item : array) initial = reduce.get(item, initial);
        return initial;
    }

    public static <T> float average(T[] array, Floatf<T> extract){
        return reducef(array, 0f, (item, accum) -> accum + extract.get(item)) / array.length;
    }

    public interface Reducei<T>{
        int get(T item, int accum);
    }

    public interface Reducef<T>{
        float get(T item, float accum);
    }

    public static class Empty<T> implements Iterable<T>, Iterator<T>{
        @Override
        public Empty<T> iterator(){
            return this;
        }

        @Override
        public boolean hasNext(){
            return false;
        }

        @Override
        public T next(){
            return null;
        }
    }

    public static class Iter<T> implements Iterable<T>, Iterator<T>{
        protected final T[] array;
        protected final int offset, length;
        protected int index = 0;

        public Iter(T[] array, int offset, int length){
            this.array = array;
            this.offset = offset;
            this.length = length;
        }

        @Override
        public Iter<T> iterator(){
            return this;
        }

        @Override
        public boolean hasNext(){
            return index < length - offset;
        }

        @Override
        public T next(){
            return hasNext() ? array[offset + index++] : null;
        }
    }

    public static class Chain<T> implements Iterable<T>, Iterator<T>{
        protected final Iterator<T> first, second;

        public Chain(Iterator<T> first, Iterator<T> second){
            this.first = first;
            this.second = second;
        }

        @Override
        public Chain<T> iterator(){
            return this;
        }

        @Override
        public boolean hasNext(){
            return first.hasNext() || second.hasNext();
        }

        @Override
        public T next(){
            return first.hasNext() ? first.next() : second.next();
        }
    }
}
