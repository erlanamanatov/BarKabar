package com.erkprog.barkabar.ui;

public interface ILifeCycle<V> {
  void bind(V view);

  void unBind();
}
