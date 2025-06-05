package com.exemple.util;

/**
 * Enumeração que representa os tipos de clientes do restaurante.
 * <p>
 * Pode ser utilizada para diferenciar o atendimento de clientes prioritários e comuns.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>Não lança exceções diretamente, mas métodos que utilizam esta enumeração podem lançar {@link NullPointerException}
 *   se um valor nulo for passado onde um {@code TipoCliente} é esperado.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Definir o tipo de um cliente ao instanciá-lo.</li>
 *   <li>Utilizar o tipo para lógica de prioridade em filas ou atendimentos.</li>
 * </ol>
 *
 * @author Seu Nome
 * @version 1.0
 */
public enum TipoCliente {
    PRIORITARIO, COMUM
}