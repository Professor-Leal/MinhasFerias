package br.com.rafaelleal.minhasferias.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

// https://developer.android.com/training/dependency-injection/hilt-testing?hl=pt-br#instrumented-tests
// A custom runner to set up the instrumented application class for tests.
class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}

