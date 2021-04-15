package com.ango.pokemon.core.repository

import com.ango.pokemon.core.data.source.local.LocalDataSourceContract
import com.ango.pokemon.core.data.source.remote.RemoteDataSourceContract

class RepositoryImpl(
        val localDataSource: LocalDataSourceContract,
        val remoteDataSource: RemoteDataSourceContract) : Repository {

}