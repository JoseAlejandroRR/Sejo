package com.skillcorp.sejoframework.contracts.providers;

public interface IProvider {

    public Object getInstance();
    public void create(Object instance);
}
