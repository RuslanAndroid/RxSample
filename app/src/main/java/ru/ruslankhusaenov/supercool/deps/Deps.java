package ru.ruslankhusaenov.supercool.deps;





import javax.inject.Singleton;

import dagger.Component;
import ru.ruslankhusaenov.supercool.fragments.news.NewsListFragment;
import ru.ruslankhusaenov.supercool.repository.NetworkRepository;

/**
 * Created by ennur on 6/28/16.
 */
@Singleton
@Component(modules = {NetworkRepository.class,})
public interface Deps {
    void inject(NewsListFragment fragment);
}
