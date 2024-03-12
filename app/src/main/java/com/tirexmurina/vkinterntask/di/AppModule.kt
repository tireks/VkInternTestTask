package com.tirexmurina.vkinterntask.di

import androidx.recyclerview.widget.RecyclerView
import com.tirexmurina.vkinterntask.utils.RecyclerViewLoadMoreScroll
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideScrollListener (
    ) : RecyclerView.OnScrollListener {

        return RecyclerViewLoadMoreScroll()
    }

}