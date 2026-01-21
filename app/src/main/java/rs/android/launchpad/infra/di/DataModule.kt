package rs.android.launchpad.infra.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.android.launchpad.repository.FeatureRepository
import rs.android.launchpad.repository.impl.FeatureRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract interface DataModule {

    @Binds
    fun bindFeatureRepository(
        featureRepositoryImpl: FeatureRepositoryImpl
    ): FeatureRepository

}
