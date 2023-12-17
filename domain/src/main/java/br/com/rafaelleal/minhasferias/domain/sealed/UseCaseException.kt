package br.com.rafaelleal.minhasferias.domain.sealed

sealed class UseCaseException(cause: Throwable) : Throwable(cause) {
    class RegisteredEventException(cause: Throwable) : UseCaseException(cause)
    class AddressException(cause: Throwable) : UseCaseException(cause)
    class UserException(cause: Throwable) : UseCaseException(cause)
    class UnknownException(cause: Throwable) : UseCaseException(cause)
    companion object {
        fun createFromThrowable(throwable: Throwable):
                UseCaseException {
            return if (throwable is UseCaseException)
                throwable else UnknownException(throwable)
        }
    }
}

