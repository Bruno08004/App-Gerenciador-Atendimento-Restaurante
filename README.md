# 🍽️ RestauranteApp – Sistema de Gerenciamento de Atendimentos

**RestauranteApp** é um aplicativo Android desenvolvido para automatizar e organizar o fluxo de atendimento em restaurantes, baseado em parâmetros de negócio bem definidos e estruturados. O projeto foi elaborado como atividade integradora das disciplinas de **Estrutura de Dados** e **Linguagem de Programação 2**, aplicando conceitos sólidos de programação orientada a objetos, uso de estruturas de dados personalizadas e controle de fluxo por enumeradores.

## 🎯 Objetivo

Simular o funcionamento de um restaurante com múltiplos garçons, turnos e filas de atendimento, permitindo o gerenciamento eficiente de clientes individuais e em grupo, pedidos personalizados e acompanhamento do status de atendimento.

---

## 📐 Diagrama de Classes

O sistema foi modelado com base no seguinte diagrama de classes:

![Image](https://github.com/user-attachments/assets/d712447d-58f4-41d7-b6fb-5c08733a18d3)

---

## 🔄 Diagrama de Sequência

O fluxo a seguir representa a sequência típica de atendimento a um grupo de clientes:

![Image](https://github.com/user-attachments/assets/5847a4c2-ad61-4281-a147-ac5e2cc78071)

> O diagrama representa desde a chegada do grupo até a finalização do atendimento, incluindo a busca por garçom, registro do pedido, acompanhamento do status e encerramento.

---

## 🧭 Modelo de Aplicação

Diagrama que ilustra o fluxo de funcionamento do restaurante:

![Image](https://github.com/user-attachments/assets/801c5d52-f35a-461d-95bb-2ca64b8ccbb2)

## 🛠️ Funcionalidades

- Gestão de **clientes** (individuais e grupos) com preferências de pedido.
- Registro e controle de **pedidos** com itens e observações específicas.
- Distribuição de **atendimentos** entre garçons por tipo de cliente ou grupo.
- Controle de **fila de atendimento** com base em prioridade.
- Enumeração de **status** do atendimento: `AGUARDANDO_PEDIDO`, `COMENDO`, `AGUARDANDO_CONTA`, `FINALIZADO`.
- Definição de **turnos** de trabalho: `MANHA`, `TARDE`, `NOITE`.
- Estrutura de dados personalizada:
  - `FilaDeAtendimento<T>`: gerenciamento de fila com prioridade.
  - `Lista<T>`: estrutura de lista genérica.

---

## 🧱 Estrutura Principal do Sistema

### Classes Centrais

- `Restaurante`: núcleo operacional, gerencia garçons e distribui atendimentos.
- `Garcom`: funcionário responsável pelos atendimentos, com turnos e filas separadas.
- `GrupoClientes` / `Cliente`: representação dos clientes com histórico de pedidos.
- `Pedido` / `ItemPedido`: estrutura de pedido com itens e observações.
- `Atendimento`, `AtendimentoGrupo`, `AtendimentoIndividual`: controle do tempo e status dos atendimentos.

### Enumeradores

- `Status`: status do atendimento.
- `TipoCliente`: `NORMAL`, `VIP`, `PRIORITARIO`.
- `Turno`: `MANHA`, `TARDE`, `NOITE`.

---

## 🔧 Tecnologias Utilizadas

- **Java** (com foco em Programação Orientada a Objetos)
- **Android Studio**
- **Estruturas de Dados Personalizadas** (implementadas manualmente)
- **UML** para modelagem das classes

---

## 👨‍🏫 Contexto Educacional

Este projeto foi desenvolvido como parte das disciplinas:
- **Estrutura de Dados**: uso prático de listas, filas e pilhas.
- **Linguagem de Programação 2**: aplicação de POO, enums e tratamento de exceções.

---

## 📎 Autores

- Bruno Campos Penha  
- João Gabriel Magalhães
- João Vitor Moreira 
- Vinicius D Oliveira
- Robert Alves 
- Ana Luiza Freitas
- Grazielly de Sousa
- Ivana Gomes

---

## 📄 Licença

Este projeto é acadêmico e não possui fins comerciais.

