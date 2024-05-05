package br.com.rafaelleal.minhasferias.app.test.steps

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rafaelleal.minhasferias.app.MainActivity
import br.com.rafaelleal.minhasferias.app.mocks.MockDb
import br.com.rafaelleal.minhasferias.app.test.ActivityScenarioHolder
import br.com.rafaelleal.minhasferias.app.test.ComposeRuleHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.pt.Dado
import io.cucumber.java.pt.E
import io.cucumber.java.pt.Então
import io.cucumber.junit.WithJunitRule
import org.junit.Rule
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@WithJunitRule(useAsTestClassInDescription = true)
@HiltAndroidTest
class RegisterEventsSteps(
    val composeRuleHolder: ComposeRuleHolder,
    val scenarioHolder: ActivityScenarioHolder
) : SemanticsNodeInteractionsProvider by composeRuleHolder.composeRule {

    @Rule(order = 0)
    @JvmField
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        MockDb.initializeDatabase()
    }

    @After
    fun tearDown() {
        MockDb.closeDatabase()
    }


    val timeOutToShowScreenView = 5000L
    // Variáveis auxiliares
    val monthInt = LocalDate.now().monthValue
    val yearInt = LocalDate.now().year
    val selectedDateString = formatDate(LocalDate.of(yearInt, monthInt, 17))
    val selectedTimeString = formatTime(LocalTime.of(4, 52))

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(date: LocalDate): String {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalTime): String {
        return DateTimeFormatter.ofPattern("hh:mm").format(time)
    }

    @Dado("^que o usuário abriu a tela de Registrar Eventos")
    fun initializeApp(){
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        scenarioHolder.launch(MainActivity.create(instrumentation.targetContext))
    }

    @E("clicou no FAB")
    fun clickOnregsitrEventAddFab(){
        composeRuleHolder.composeRule.waitUntil(
            timeoutMillis = timeOutToShowScreenView
        ) {
            composeRuleHolder.composeRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }
        composeRuleHolder.composeRule
            .onNodeWithTag("fab_add_new_event", useUnmergedTree = true)
            .performClick()
    }

    @Então("^deve navegar para a tela de adição de evento")
    fun navigateToAddRegisteredEventScreen(){
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeRuleHolder.composeRule.onAllNodesWithTag("Nome do Evento")
                .fetchSemanticsNodes().size == 1
        }
    }

    @E("o botão de salvar não deve estar visível")
    fun oBotaoDeSalvarNaoDeveEstarVisivel() {
        composeRuleHolder.composeRule.onNodeWithTag("SaveRegisteredEventButton").assertDoesNotExist()
    }

    @E("digitar o nome do evento: {}")
    fun digitarONomeDoEvento(arg0: String) {
        composeRuleHolder.composeRule.onNodeWithTag("Nome do Evento").performTextInput(
            arg0
        )
    }

    @E("digitar o nome do endereço: {}")
    fun digitarOEndereco(arg0: String) {
        composeRuleHolder.composeRule.onNodeWithTag("Endereço").performTextInput(arg0)
    }

    @E("clicar no dia do endereço para abrir o diálogo")
    fun clicarNoDiaDoEnderecoParaAbrirODialogo() {
        composeRuleHolder.composeRule.onNodeWithTag("Dia do Evento").performClick()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeRuleHolder.composeRule.onAllNodesWithText("Cancel")
                .fetchSemanticsNodes().size == 1
        }
    }

    @E("escolher o dia {} no calendário e clicar em OK")
    fun escolherODiaNoCalendarioEClicarEmOK(arg0: String) {
        composeRuleHolder.composeRule.onNodeWithText(arg0).performClick()
        composeRuleHolder.composeRule.onNodeWithText("OK").performClick()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeRuleHolder.composeRule.onAllNodesWithText(selectedDateString)
                .fetchSemanticsNodes().size == 1
        }
    }

    @Então("deve aparecer a data selecionada")
    fun deveAparecerADataSelecionadaNoFormatoDdMmYyyy() {
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeRuleHolder.composeRule.onAllNodesWithText(selectedDateString)
                .fetchSemanticsNodes().size == 1
        }
    }
    
    @E("clicar na hora do evento")
    fun clicarNaHoraDoEvento() {
        composeRuleHolder.composeRule.onNodeWithTag("Hora do Evento").performClick()
    }

    @Então("deve aparecer o diálogo para a escolha do horário")
    fun deveAparecerODialogoParaAEscolhaDoHorario() {
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeRuleHolder.composeRule.onAllNodesWithText("Cancel")
                .fetchSemanticsNodes().size == 1
        }
    }

//    @E("clicar nos dígitos <hora{int}>, <hora{int}> e <hora{int}> emsequência depois em OK")
    @E("clicar nos dígitos {}, {} e {} em sequência depois em OK")
    fun clicarNosDigitosEEmsequenciaDepoisEmOK(arg0: String, arg1: String, arg2: String) {
        composeRuleHolder.composeRule.onNodeWithText(arg0).performClick()
        composeRuleHolder.composeRule.onNodeWithText(arg1).performClick()
        composeRuleHolder.composeRule.onNodeWithText(arg2).performClick()
        composeRuleHolder.composeRule.onNodeWithText("OK").performClick()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeRuleHolder.composeRule.onAllNodesWithText(selectedTimeString)
                .fetchSemanticsNodes().size == 1
        }
    }

    @Então("deve aparecer a caixa de evento preenchida com {} e {} e o botão de salvar")
    fun deveAparecerACaixaDeEventoPreenchidaComEEOBotaoDeSalvar(arg0: String, arg1: String) {
        composeRuleHolder.composeRule.onAllNodesWithText(arg0).assertCountEquals(2)
        composeRuleHolder.composeRule.onAllNodesWithText(arg1).assertCountEquals(2)
        composeRuleHolder.composeRule.onAllNodesWithText(selectedDateString).assertCountEquals(1)
        composeRuleHolder.composeRule.onAllNodesWithText(selectedTimeString).assertCountEquals(1)
        composeRuleHolder.composeRule.onAllNodesWithText("$selectedDateString - $selectedTimeString")
            .assertCountEquals(1)
    }

    @E("clicar no botão de salvar")
    fun clicarNoBotaoDeSalvar() {
        composeRuleHolder.composeRule.onNodeWithTag("SaveRegisteredEventButton").performClick()
    }

    @Então("deve voltar para a tela inicial com o evento salvo mostrando o nome {} na lista")
    fun deveVoltarParaATelaInicialComOEventoSalvoMostrandoONomeNaLista(arg0: String) {
        // Volta para a tela inicial e verifica se o item foi criado
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = timeOutToShowScreenView) {
            composeRuleHolder.composeRule.onAllNodesWithTag("fab_add_new_event")
                .fetchSemanticsNodes().size == 1
        }
        composeRuleHolder.composeRule.onNodeWithText(arg0, useUnmergedTree = true)
            .assertIsDisplayed()
    }

}
