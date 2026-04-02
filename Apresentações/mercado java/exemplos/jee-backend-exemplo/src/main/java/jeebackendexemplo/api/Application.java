package jeebackendexemplo.api;

import jeebackendexemplo.api.controller.ClienteController;
import jeebackendexemplo.api.controller.TestController;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Application extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(TestController.class);
        classes.add(ClienteController.class);
        return classes;
    }
}
