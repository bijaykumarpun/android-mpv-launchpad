package rs.android.launchpad.infra.di

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.android.launchpad.repository.FeatureRepository
import rs.android.launchpad.repository.ProductRepository
import rs.android.launchpad.repository.RemoteConfigRepository
import rs.android.launchpad.repository.impl.FeatureRepositoryImpl
import rs.android.launchpad.repository.impl.ProductRepositoryImpl
import rs.android.launchpad.repository.impl.RemoteConfigRepositoryImpl
import rs.android.launchpad.util.billing.BillingManager

@Module
@InstallIn(SingletonComponent::class)
abstract interface DataModule {

    @Binds
    fun bindFeatureRepository(
        featureRepositoryImpl: FeatureRepositoryImpl
    ): FeatureRepository

    @Binds
    fun bindRemoteConfigRepository(
        remoteConfigRepositoryImpl: RemoteConfigRepositoryImpl
    ): RemoteConfigRepository


    @Binds
    fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

}

@Module
@InstallIn(SingletonComponent::class)
object BillingModule {

    @Provides
    fun provideBillingManager(
        @ApplicationContext context: Context
    ): BillingManager {
        return BillingManager(context)
    }
}


@Module
@InstallIn(SingletonComponent::class)
object FirebaseRemoteConfigModule {
    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }
}
