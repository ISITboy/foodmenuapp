package com.example.gosporttastingtask.ui.di

import android.app.Application
import androidx.room.Room
import com.example.gosporttastingtask.ui.Constants.BASE_URL
import com.example.gosporttastingtask.ui.Constants.DATABASE_NAME
import com.example.gosporttastingtask.ui.data.local.Database
import com.example.gosporttastingtask.ui.data.local.ProductsDao
import com.example.gosporttastingtask.ui.data.remote.RemoteApi
import com.example.gosporttastingtask.ui.data.repository.ProductsRepositoryImpl
import com.example.gosporttastingtask.ui.data.repository.RemoteRepositoryImpl
import com.example.gosporttastingtask.ui.domain.repository.ProductsRepository
import com.example.gosporttastingtask.ui.domain.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): Database {
        return Room.databaseBuilder(
            context = application,
            klass = Database::class.java,
            name = DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideProductDao(
        database: Database
    ): ProductsDao = database.productsDao


    @Provides
    @Singleton
    fun providesProductsRepository(
        productsDao: ProductsDao
    ): ProductsRepository = ProductsRepositoryImpl(productsDao = productsDao)


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesPostService(retrofit: Retrofit) = retrofit.create(RemoteApi::class.java)

    @Singleton
    @Provides
    fun providesRemoteRepository(
        remoteApi: RemoteApi
    ):RemoteRepository = RemoteRepositoryImpl(remoteApi= remoteApi)
}