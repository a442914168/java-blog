package com.bobo.delegate;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Delegate
 * @description:
 * @author: bobobo
 * @create: 2018-07-16 06:25
 **/
public class ViewController {

    private Map<String, View> views = new HashMap<String, View>();

    public ViewController() {
        views.put("Login", new LoginView());
        views.put("Home", new HomeView());
    }

    public void push(String viewName) {
        views.get(viewName).draw(viewName);
    }
}
