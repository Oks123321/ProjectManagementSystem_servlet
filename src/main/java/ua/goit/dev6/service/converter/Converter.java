package ua.goit.dev6.service.converter;

public interface Converter<E, T> {
    E from(T entity);
    T to(E entity);
}
