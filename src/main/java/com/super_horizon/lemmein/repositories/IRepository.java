package com.super_horizon.lemmein.repositories;

import java.util.*;
import java.lang.reflect.InvocationTargetException;


public interface IRepository<T> {
    List<T> findOrCreate(Map<String, String> query) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}