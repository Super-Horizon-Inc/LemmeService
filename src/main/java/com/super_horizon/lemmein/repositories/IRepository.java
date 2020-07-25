package com.super_horizon.lemmein.repositories;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public interface IRepository<T> {
    List<T> findOrCreate(Map<String, String> query) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}