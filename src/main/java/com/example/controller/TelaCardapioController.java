package com.example.controller;

import com.example.model.ItemPedido;
import com.example.model.Restaurante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TelaCardapioController {

    @FXML
    private ListView<String> listaItens;
    @FXML
    private TextField filtro;

    private ObservableList<ItemPedido> itensCardapio;
    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (this.restaurante != null) {
            carregarItensCardapio();
        }
    }

    @FXML
    public void initialize() {
        itensCardapio = FXCollections.observableArrayList();
        filtro.textProperty().addListener((observable, oldValue, newValue) -> filtrarItens(newValue));
    }

    private void carregarItensCardapio() {
        if (restaurante != null) {
            itensCardapio.clear();
            itensCardapio.addAll(restaurante.getCardapio());
            atualizarListaExibida();
        }
    }

    private void atualizarListaExibida() {
        listaItens.getItems().clear();
        itensCardapio.stream()
                .map(item -> item.getNome() + " - R$" + String.format("%.2f", item.getPreco()))
                .forEach(itemString -> listaItens.getItems().add(itemString));
    }

    private void filtrarItens(String textoFiltro) {
        if (textoFiltro == null || textoFiltro.isEmpty()) {
            atualizarListaExibida();
        } else {
            listaItens.getItems().clear();
            itensCardapio.stream()
                    .filter(item -> item.getNome().toLowerCase().contains(textoFiltro.toLowerCase()))
                    .map(item -> item.getNome() + " - R$" + String.format("%.2f", item.getPreco()))
                    .forEach(itemString -> listaItens.getItems().add(itemString));
        }
    }

    @FXML
    public void handleAdicionarItem(ActionEvent event) {
        Dialog<ItemPedido> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Item ao Cardápio");
        dialog.setHeaderText("Novo Item");

        ButtonType adicionarButtonType = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(adicionarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Item");
        TextField precoField = new TextField();
        precoField.setPromptText("Preço");
        Spinner<Integer> quantidadeSpinner = new Spinner<>(1, 999, 1);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(new Label("Preço:"), 0, 1);
        grid.add(precoField, 1, 1);
        grid.add(new Label("Quantidade (inicial):"), 0, 2);
        grid.add(quantidadeSpinner, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == adicionarButtonType) {
                try {
                    String nome = nomeField.getText().trim();
                    double preco = Double.parseDouble(precoField.getText().trim());
                    int quantidade = quantidadeSpinner.getValue();
                    if (!nome.isEmpty() && preco >= 0 && quantidade > 0) {
                        return new ItemPedido(nome, quantidade, preco);
                    }
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, "Preço inválido. Use um número.").showAndWait();
                }
            }
            return null;
        });

        Optional<ItemPedido> result = dialog.showAndWait();
        result.ifPresent(item -> {
            if (restaurante != null) {
                restaurante.adicionarAoCardapio(item);
                carregarItensCardapio();
                new Alert(Alert.AlertType.INFORMATION, "Item '" + item.getNome() + "' adicionado ao cardápio.").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
            }
        });
    }

    @FXML
    public void handleRemoverItem(ActionEvent event) {
        String selectedItemString = listaItens.getSelectionModel().getSelectedItem();
        if (selectedItemString != null) {
            String nomeItem = selectedItemString.substring(0, selectedItemString.indexOf(" - R$"));

            ItemPedido itemParaRemover = itensCardapio.stream()
                    .filter(item -> item.getNome().equals(nomeItem))
                    .findFirst()
                    .orElse(null);

            if (itemParaRemover != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja remover '" + itemParaRemover.getNome() + "' do cardápio?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Implementação da remoção no restaurante
                    // Você precisaria de um método removerItemDoCardapio no Restaurante
                    // restaurante.removerItemDoCardapio(itemParaRemover);
                    itensCardapio.remove(itemParaRemover);
                    atualizarListaExibida();
                    new Alert(Alert.AlertType.INFORMATION, "Item removido do cardápio.").showAndWait();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Selecione um item para remover.").showAndWait();
        }
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuPrincipal.fxml"));
            Parent root = loader.load();

            TelaPrincipalController menuController = loader.getController();
            menuController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para o menu principal: " + e.getMessage()).showAndWait();
        }
    }
}