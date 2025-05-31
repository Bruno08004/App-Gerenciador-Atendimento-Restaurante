package com.exemple.controller;

import com.exemple.model.Atendimento;
import com.exemple.model.AtendimentoGrupo;
import com.exemple.model.AtendimentoIndividual;
import com.exemple.model.Garcom;
import com.exemple.model.ItemPedido;
import com.exemple.model.ObservacaoDoPedido;
import com.exemple.model.Pedido;
import com.exemple.model.Restaurante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaGerenciarPedidoController {

    private Restaurante restaurante;
    private Garcom garcomLogado;
    private Atendimento atendimentoAtual;
    private Pedido pedidoAtual; // O pedido que está sendo editado/criado

    @FXML
    private Label labelTituloAtendimento;
    @FXML
    private ListView<String> listViewCardapio;
    @FXML
    private TextField filtroCardapio;
    @FXML
    private ListView<String> listViewItensPedido;
    @FXML
    private Label labelTotalPedido;
    @FXML
    private Spinner<Integer> spinnerQuantidade;
    @FXML
    private TextArea textAreaObservacoesItem;

    private ObservableList<ItemPedido> itensCardapioObservableList;
    private ObservableList<ItemPedido> itensPedidoObservableList;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        itensCardapioObservableList = FXCollections.observableArrayList(restaurante.getCardapio());
        // Popula o ListView na inicialização, após o setRestaurante
        carregarCardapio();
    }

    public void setGarcomLogado(Garcom garcom) {
        this.garcomLogado = garcom;
    }

    public void setAtendimentoAtual(Atendimento atendimento) {
        this.atendimentoAtual = atendimento;
        this.pedidoAtual = atendimento.getPedido(); // Pega o pedido existente do atendimento
        if (atendimentoAtual instanceof AtendimentoIndividual) {
            labelTituloAtendimento.setText("Pedido para: " + ((AtendimentoIndividual) atendimentoAtual).getCliente().getNome());
        } else if (atendimentoAtual instanceof AtendimentoGrupo) {
            labelTituloAtendimento.setText("Pedido para: " + ((AtendimentoGrupo) atendimentoAtual).getGrupo().getNomeGrupo());
        }
        itensPedidoObservableList = FXCollections.observableArrayList(pedidoAtual.getItens());
        atualizarListaItensPedido(); // Popula a lista de itens do pedido
        atualizarTotalPedido();
    }

    @FXML
    public void initialize() {
        spinnerQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
        // O setCellFactory não é estritamente necessário para ListView<String> mas garante comportamento
        listViewCardapio.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
            }
        });
        listViewItensPedido.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
            }
        });

        // Listener para o filtro do cardápio
        filtroCardapio.textProperty().addListener((obs, oldVal, newVal) -> filtrarCardapio(newVal));
    }

    private void carregarCardapio() {
        listViewCardapio.getItems().clear();
        itensCardapioObservableList.forEach(item -> listViewCardapio.getItems().add(item.getNome() + " - R$" + String.format("%.2f", item.getPreco())));
    }

    private void filtrarCardapio(String filtro) {
        listViewCardapio.getItems().clear();
        itensCardapioObservableList.stream()
                .filter(item -> item.getNome().toLowerCase().contains(filtro.toLowerCase()))
                .forEach(item -> listViewCardapio.getItems().add(item.getNome() + " - R$" + String.format("%.2f", item.getPreco())));
    }

    private void atualizarListaItensPedido() {
        listViewItensPedido.getItems().clear();
        itensPedidoObservableList.forEach(item -> {
            StringBuilder itemString = new StringBuilder();
            itemString.append(item.getNome())
                    .append(" (x").append(item.getQuantidade())
                    .append(") - R$ ").append(String.format("%.2f", item.calcularSubtotal()));
            if (!item.getObservacoes().isEmpty()) {
                itemString.append(" [Obs: ");
                item.getObservacoes().forEach(obs -> itemString.append(obs.getDescricao()).append("; "));
                itemString.append("]");
            }
            listViewItensPedido.getItems().add(itemString.toString());
        });
    }

    private void atualizarTotalPedido() {
        labelTotalPedido.setText("TOTAL: R$ " + String.format("%.2f", pedidoAtual.calcularTotal()));
    }

    @FXML
    public void handleAdicionarItem(ActionEvent event) {
        String selectedItemString = listViewCardapio.getSelectionModel().getSelectedItem();
        if (selectedItemString == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um item do cardápio para adicionar.").showAndWait();
            return;
        }

        String nomeItem = selectedItemString.substring(0, selectedItemString.indexOf(" - R$"));
        ItemPedido itemSelecionado = restaurante.getCardapio().stream()
                .filter(item -> item.getNome().equals(nomeItem))
                .findFirst().orElse(null);

        if (itemSelecionado != null) {
            int quantidade = spinnerQuantidade.getValue();
            // Cria uma nova instância de ItemPedido para não modificar o objeto original do cardápio
            ItemPedido itemParaPedido = new ItemPedido(itemSelecionado.getNome(), quantidade, itemSelecionado.getPreco());
            pedidoAtual.adicionarItem(itemParaPedido); // Adiciona ao pedido
            itensPedidoObservableList.add(itemParaPedido); // Adiciona à lista observável
            atualizarListaItensPedido();
            atualizarTotalPedido();
            new Alert(Alert.AlertType.INFORMATION, itemParaPedido.getNome() + " (x" + quantidade + ") adicionado ao pedido.").showAndWait();
        }
    }

    @FXML
    public void handleAdicionarObservacaoItem(ActionEvent event) {
        String selectedItemPedidoString = listViewItensPedido.getSelectionModel().getSelectedItem();
        if (selectedItemPedidoString == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um item no pedido para adicionar observação.").showAndWait();
            return;
        }

        ItemPedido itemNoPedido = null;
        // Percorre a lista de objetos ItemPedido reais para encontrar o correspondente
        for (ItemPedido item : itensPedidoObservableList) {
            // Tenta corresponder pelo nome e quantidade para maior precisão
            String itemDisplayStringStart = item.getNome() + " (x" + item.getQuantidade() + ")";
            if (selectedItemPedidoString.startsWith(itemDisplayStringStart)) {
                itemNoPedido = item;
                break;
            }
        }

        if (itemNoPedido != null) {
            String observacaoTexto = textAreaObservacoesItem.getText().trim();
            if (!observacaoTexto.isEmpty()) {
                itemNoPedido.adicionarObservacao(new ObservacaoDoPedido(observacaoTexto));
                atualizarListaItensPedido(); // Atualiza a lista para mostrar a observação
                textAreaObservacoesItem.clear();
                new Alert(Alert.AlertType.INFORMATION, "Observação adicionada a " + itemNoPedido.getNome() + ".").showAndWait();
            } else {
                new Alert(Alert.AlertType.WARNING, "Digite a observação a ser adicionada.").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Erro ao encontrar o item selecionado no pedido. Tente novamente.").showAndWait();
        }
    }

    @FXML
    public void handleConfirmarPedido(ActionEvent event) {
        // Pedido já está sendo atualizado em tempo real à medida que itens são adicionados.
        // Este botão serve mais para um feedback final ao garçom ou para fechar a tela.
        new Alert(Alert.AlertType.INFORMATION, "Pedido atualizado e salvo!").showAndWait();
        handleVoltar(event); // Volta para a tela de atendimentos
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaAtendimento.fxml"));
            Parent root = loader.load();

            TelaAtendimentoController controller = loader.getController();
            controller.setRestaurante(restaurante);
            controller.setGarcomLogado(garcomLogado);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Atendimento - Garçom: " + garcomLogado.getNome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para a tela de atendimentos: " + e.getMessage()).showAndWait();
        }
    }
}