package com.hb.nf.di.module.sub;


import com.hb.lib.data.IDataManager;
import com.hb.nf.data.DataManager;
import com.hb.nf.data.repository.SystemRepository;
import com.hb.nf.data.store.system.SystemLocalStorage;
import com.hb.nf.data.store.system.SystemRepositoryImpl;
import com.hb.nf.data.store.system.SystemStore;
import com.hb.nf.di.scope.CustomScope;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by buihai on 8/8/17.
 */
@Module
public class SystemModule {


    @Provides
    @CustomScope
    SystemStore.LocalStorage provideLocalStorage(IDataManager dm) {
        return new SystemLocalStorage((DataManager) dm);
    }

    @Provides
    @CustomScope
    SystemStore.RequestService provideRequestService(Retrofit retrofit) {
        return retrofit.create(SystemStore.RequestService.class);
    }

    @Provides
    @CustomScope
    SystemRepository provideRepository(SystemStore.LocalStorage storage,
                                       SystemStore.RequestService service) {
        return new SystemRepositoryImpl(storage, service);
    }
}
