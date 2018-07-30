package com.erkprog.barkabar.ui;

public interface BasePresenter<V> extends ILifeCycle<V> {

  boolean isAttached();
}
