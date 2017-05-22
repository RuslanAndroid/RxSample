package ru.ruslankhusaenov.supercool;

import android.app.Application;

import ru.ruslankhusaenov.supercool.deps.DaggerDeps;
import ru.ruslankhusaenov.supercool.deps.Deps;
import ru.ruslankhusaenov.supercool.repository.NetworkRepository;


/**
 * Created by Ruslan on 17.05.2017.
 */

public class App extends Application {
    private static Deps deps;
    @Override
    public void onCreate() {
        super.onCreate();
        deps = DaggerDeps.builder()
                .networkRepository(new NetworkRepository(getApplicationContext()))
                .build();
    }

    public static Deps getDeps() {
        return deps;
    }
}
