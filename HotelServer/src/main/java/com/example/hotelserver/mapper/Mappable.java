package com.example.hotelserver.mapper;

public interface Mappable<E, D>{
    E toEntity(D dto);
    D toDto(E entity);
//    List<D> toDto(List<E> entity);
//    List<E> toEntity(List<D> dtos);
}
