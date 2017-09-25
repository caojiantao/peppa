package com.cjt.core.impl;

import com.cjt.api.HelloHessian;
import org.springframework.stereotype.Service;

@Service("helloHessian")
public class HelloHessianImpl implements HelloHessian {

  @Override
  public void say(String msg) {
    System.out.println(msg);
  }
}
