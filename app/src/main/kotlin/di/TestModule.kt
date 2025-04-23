package di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TestModule  {

    @Provides
    fun provideTestClass() : Test = Test()

    @Provides
    fun provideValidClass() : Valid = Valid()
}