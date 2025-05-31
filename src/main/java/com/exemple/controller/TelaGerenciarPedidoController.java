package com.exemple.controller;

import com.exemple.model.Atendimento;
import com.exemple.model.AtendimentoGrupo;
import com.exemple.model.AtendimentoIndividual;
import com.exemple.model.Cliente;
import com.exemple.model.GrupoClientes;
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
    private Pedido pedidoAtual;

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
    @FXML
    private TextArea textAreaObservacoesCliente;
    @FXML
    private Label labelPedidoId;
    @FXML
    private Label labelStatusPedido;


    private ObservableList<ItemPedido> itensCardapioObservableList;
    private ObservableList<ItemPedido> itensPedidoObservableList;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        itensCardapioObservableList = FXCollections.observableArrayList(restaurante.getCardapio());
        carregarCardapio();
    }

    public void setGarcomLogado(Garcom garcom) {
        this.garcomLogado = garcom;
    }

    public void setAtendimentoAtual(Atendimento atendimento) {
        this.atendimentoAtual = atendimento;
        this.pedidoAtual = atendimento.getPedido();
        if (atendimentoAtual instanceof AtendimentoIndividual) {
            Cliente cliente = ((AtendimentoIndividual) atendimentoAtual).getCliente();
            labelTituloAtendimento.setText("Pedido para: " + cliente.getNome());
            textAreaObservacoesCliente.setText(cliente.getObservacoesGerais());
        } else if (atendimentoAtual instanceof AtendimentoGrupo) {
            GrupoClientes grupo = ((AtendimentoGrupo) atendimentoAtual).getGrupo();
            labelTituloAtendimento.setText("Pedido para: " + grupo.getNomeGrupo());
            textAreaObservacoesCliente.setText(grupo.getObservacoesGerais());
        }

        labelPedidoId.setText("ID do Pedido: " + pedidoAtual.getId());
        labelStatusPedido.setText("Status do Pedido: " + atendimentoAtual.getStatus().toString());

        // Carrega os itens do pedido existentes, se houver
        // É importante que itensPedidoObservableList seja inicializado AQUI
        // e não no initialize(), pois o pedidoAtual pode ainda não estar setado lá.
        itensPedidoObservableList = FXCollections.observableArrayList(pedidoAtual.getItens());
        listViewItensPedido.setItems(formatarItensPedido(itensPedidoObservableList)); // Configura o ListView com os itens formatados
        atualizarTotalPedido();
    }

    @FXML
    public void initialize() {
        spinnerQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));

        // Não é necessário setCellFactory para ListView<String> se você já está adicionando Strings.
        // O setCellFactory seria útil se você estivesse usando ListView<ItemPedido> e quisesse customizar como ItemPedido é exibido.

        filtroCardapio.textProperty().addListener((obs, oldVal, newVal) -> filtrarCardapio(newVal));
        textAreaObservacoesCliente.setEditable(false);
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

    // NOVO MÉTODO para formatar a lista de itens para exibição
    private ObservableList<String> formatarItensPedido(ObservableList<ItemPedido> itens) {
        ObservableList<String> formattedList = FXCollections.observableArrayList();
        itens.forEach(item -> {
            StringBuilder itemString = new StringBuilder();
            itemString.append(item.getNome())
                    .append(" (x").append(item.getQuantidade())
                    .append(") - R$ ").append(String.format("%.2f", item.calcularSubtotal()));
            if (!item.getObservacoes().isEmpty()) {
                itemString.append(" [Obs: ");
                item.getObservacoes().forEach(obs -> itemString.append(obs.getDescricao()).append("; "));
                itemString.append("]");
            }
            formattedList.add(itemString.toString());
        });
        return formattedList;
    }

    // Método que agora só se preocupa em ATUALIZAR O LISTVIEW
    private void atualizarListaItensPedido() {
        listViewItensPedido.setItems(formatarItensPedido(itensPedidoObservableList));
    }


    private void atualizarTotalPedido() {
        double total = pedidoAtual.calcularTotal();
        labelTotalPedido.setText("TOTAL: R$ " + String.format("%.2f", total));
        System.out.println("Valor total do pedido atualizado para: R$" + String.format("%.2f", total)); // Ponto de depuração
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

            // Adiciona ao pedido
            pedidoAtual.adicionarItem(itemParaPedido);

            // Adiciona à lista observável (Isso deve notificar o ListView automaticamente)
            itensPedidoObservableList.add(itemParaPedido);

            // Atualiza a exibição do ListView (chama formatarItensPedido novamente para reconstruir a lista de Strings)
            atualizarListaItensPedido();

            // Atualiza o label do total
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
        new Alert(Alert.AlertType.INFORMATION, "Pedido atualizado e salvo!").showAndWait();
        handleVoltar(event);
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