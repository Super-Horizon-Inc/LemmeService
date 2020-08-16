package com.super_horizon.lemme.repositories;

import java.util.*;

public interface IRepository<E> {
    E findOrCreate(Map<String, String> query);
}