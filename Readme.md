# 🍽️ App Gerenciador de Atendimento de Restaurante

Bem-vindo ao nosso sistema inteligente de **gerenciamento de atendimentos em restaurantes**, criado como projeto final para a disciplina de **Estrutura de Dados** no IFBA – Campus Vitória da Conquista.

Nosso objetivo foi desenvolver um sistema funcional, que simula o ambiente real de um restaurante, otimizando a organização dos atendimentos, o trabalho dos garçons e a experiência dos clientes. A aplicação também incorpora recursos como **cronômetro de atendimento**, **fila com prioridade**, **limite por turno**, **atendimento a grupos** e **persistência dos dados em arquivos `.txt`**.

---

## 🎯 Objetivo

O sistema visa **organizar e agilizar o atendimento em restaurantes**, utilizando estruturas de dados clássicas e boas práticas de programação.

- 📋 Gerencia garçons, pedidos e clientes (individuais ou em grupo)
- ⏱️ Cronometra o tempo de atendimento
- ⚙️ Utiliza **filas, listas, generics e interfaces**
- ⭐ Prioriza clientes VIP ou com necessidades especiais
- 📂 Salva dados em arquivos `.txt` para manter histórico e preferências
- 🚫 Limita o número de atendimentos por garçom a cada turno
- 🔁 Permite reorganização dinâmica das filas com base em prioridade

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **JavaFX + FXML** para interface gráfica
- **Maven** para gerenciamento do projeto
- **JUnit 5** para testes automatizados

---

## 🧠 Estruturas de Dados Implementadas

| Estrutura               | Aplicação no Sistema                                         |
|-------------------------|--------------------------------------------------------------|
| **Lista**               | Armazena pedidos, itens, históricos e preferências           |
| **Fila (FIFO)**         | Organiza ordem de atendimento dos clientes                   |
| **Fila com Prioridade** | Reorganiza a fila automaticamente para clientes VIP          |
| **Generics**            | Garante reuso e segurança na fila de atendimento             |
| **Interface `Atendivel`** | Permite tratar clientes e grupos de forma polimórfica       |

---

## 📸 Interface do Sistema

A interface gráfica foi desenvolvida para ser simples e intuitiva para os garçons:

- Tela de **Login do Garçom**
- Tela de **menu principal** com acesso rápido às funcionalidades
- Visualização da **fila geral** e da **fila individual de cada garçom**
- Cadastro de **clientes e grupos**
- Registro de **pedidos com observações personalizadas**
- Acompanhamento do **tempo de atendimento**

---

## 🆕 Funcionalidades Adicionadas

Estas são as funcionalidades implementadas a pedido do professor e com base nas necessidades reais do atendimento:

- ✅ **Limite de atendimentos por garçom**: cada garçom pode atender até 5 clientes por turno
- 🔁 **Reorganização dinâmica da fila**: clientes com prioridade (VIP) são movidos automaticamente para o início
- 🕒 **Cronômetro de atendimento**: o sistema registra o tempo gasto em cada atendimento
- 🧾 **Histórico de pedidos**: é possível consultar o que o cliente já pediu anteriormente
- 👥 **Atendimento a grupos**: o sistema trata grupos de forma unificada, respeitando preferências coletivas
- 💾 **Persistência de dados em `.txt`**: todas as informações são salvas em arquivos `.txt`, garantindo que os dados não se percam após fechar o sistema

---

## 🔁 Fluxo de Atendimento

1. O cliente (ou grupo) entra na fila geral
2. O garçom transfere o cliente para sua fila individual
3. O pedido é registrado, com cronômetro e observações
4. O atendimento é finalizado e salvo no histórico em `.txt`
5. O garçom continua até atingir o limite de 5 atendimentos por turno

---

## 🚀 Como Executar o Projeto

> Pré-requisitos: JDK 21+, Maven e JavaFX configurados

# Clone o repositório
git clone https://github.com/Bruno08004/App-Gerenciador-Atendimento-Restaurante.git

# Acesse a pasta do projeto
cd App-Gerenciador-Atendimento-Restaurante

# Compile o projeto
mvn clean install

# Execute a aplicação
mvn javafx:run

---

### 🧪 Testes

- Utilizamos JUnit 5 para validar funcionalidades críticas como:

- Login dos garçons

- Cálculo total dos pedidos

- Organização e reordenação da fila

- Finalização do atendimento e atualização do histórico

---

### 📚 Créditos
#### Projeto desenvolvido por:

Ana Luiza Freitas B. Siqueira

Bruno Campos Penha

Grazielly de Sousa Barros

### 👨‍🏫 Professor Orientador

Alexandro Santos da Silva

