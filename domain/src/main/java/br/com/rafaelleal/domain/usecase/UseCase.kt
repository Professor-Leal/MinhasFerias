package br.com.rafaelleal.domain.usecase

import br.com.rafaelleal.minhasferias.domain.sealed.Result
import br.com.rafaelleal.minhasferias.domain.sealed.UseCaseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class UseCase<I : UseCase.Request, O : UseCase.Response>(
    private val configuration: Configuration
) {
    fun execute(request: I) = process(request)
        .map {
            Result.Success(it) as Result<O>
        }
        .flowOn(configuration.dispatcher)
        .catch {
            emit(
                Result.Error(
                    UseCaseException.createFromThrowable(it)
                )
            )
        }
    internal abstract fun process(request: I): Flow<O>
    class Configuration(val dispatcher: CoroutineDispatcher)
    interface Request
    interface Response
}