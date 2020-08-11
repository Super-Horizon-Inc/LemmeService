package com.super_horizon.lemme.repositories;

import java.util.*;


public interface IRepository<T> {
    List<T> findOrCreate(Map<String, String> query);
}