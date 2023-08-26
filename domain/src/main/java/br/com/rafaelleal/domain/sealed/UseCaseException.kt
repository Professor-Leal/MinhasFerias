package br.com.rafaelleal.minhasferias.domain.sealed

sealed class UseCaseException(cause: Throwable) : Throwable(cause) {

    class AddressException(cause: Throwable) : UseCaseException(cause)
    class UserException(cause: Throwable) : UseCaseException(cause)
    class UnknownException(cause: Throwable) : UseCaseException(cause)

}

