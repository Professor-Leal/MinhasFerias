#language: pt
Funcionalidade: Registrar Eventos

  Esquema do Cenário: Preencher um evento e salvar
    Dado que o usuário abriu a tela de Registrar Eventos
    E clicou no FAB
    Então deve navegar para a tela de adição de evento
    E o botão de salvar não deve estar visível
    E digitar o nome do evento: <nome_do_evento>
    E digitar o nome do endereço: <nome_do_endereco>
    E clicar no dia do endereço para abrir o diálogo
    E escolher o dia <dia_calendario> no calendário e clicar em OK
    Então deve aparecer a data selecionada
    E clicar na hora do evento
    Então deve aparecer o diálogo para a escolha do horário
    E clicar nos dígitos <hora01>, <hora02> e <hora03> em sequência depois em OK
    Então deve aparecer a caixa de evento preenchida com <nome_do_evento> e <nome_do_endereco> e o botão de salvar
    E clicar no botão de salvar
    Então deve voltar para a tela inicial com o evento salvo mostrando o nome <nome_do_evento> na lista

    Exemplos:
      | nome_do_evento      | nome_do_endereco | dia_calendario | hora01 | hora02 | hora03 |
      | Meu primeiro evento | Rua de cima      | 17             | 4      | 5      | 2      |
      | Meu primeiro evento | Rua de cima      | 50             | 4      | 5      | 2      |
