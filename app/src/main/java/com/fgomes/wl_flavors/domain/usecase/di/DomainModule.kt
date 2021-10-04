package com.fgomes.wl_flavors.domain.usecase.di

import com.fgomes.wl_flavors.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindCreateProductUseCase(useCase: CreateProductUseCaseImpl) : CreateProductUseCase

    @Binds
    fun bindGetProductsUseCase(useCaseImpl: GetProductsUseCaseImpl) : GetProductsUseCase

    @Binds
    fun bindUploadProductImageUseCase(useCase: UploadProductImageUseCaseImpl) : UploadProductImageUseCase


}