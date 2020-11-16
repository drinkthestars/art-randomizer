package com.goofy.goober

import com.goofy.goober.api.ApiClient
import com.goofy.goober.api.usecase.GetArtObject
import com.goofy.goober.interactor.ArtInteractor
import com.goofy.goober.viewmodel.ArtViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonModule = module {

    factory { ApiClient() }

    factory { GetArtObject(apiClient = get()) }

    factory { ArtInteractor(getArtObject = get()) }

    viewModel { ArtViewModel() }

}
