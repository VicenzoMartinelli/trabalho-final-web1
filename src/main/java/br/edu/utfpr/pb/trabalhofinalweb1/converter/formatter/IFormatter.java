package br.edu.utfpr.pb.trabalhofinalweb1.converter.formatter;


/**
 * Formatter é responsável por transfomar cadeias sem formatação em cadeias
 * formatadas e vice-versa.
 *
 * @author Leonardo Bessa
 */
public interface IFormatter {

    /**
     * <p>
     * Formata uma cadeia.
     * </p>
     *
     * @param value cadeia sem formatado
     * @return cadeia formatada
     * @throws IllegalArgumentException caso argumento seja nulo ou não represente propriamente um
     *                                  valor sem formato.
     */
    String format(String value) throws IllegalArgumentException;

    /**
     * <p>
     * Remove a formatação de uma cadeia.
     * </p>
     *
     * @param value cadeia formatada
     * @return cadeia sem formato
     * @throws IllegalArgumentException caso argumento seja nulo ou não represente propriamente um
     *                                  valor formatado.
     */
    String unformat(String value) throws IllegalArgumentException;

    /**
     * <p>
     * Verifica se uma cadeia está no formato com o qual o formatador trabalha.
     * </p>
     *
     * @param value cadeia a ser verificada
     * @return true, se estiver de acordo com o formato
     */
    boolean isFormatted(String value);

    /**
     * <p>
     * Verifica se uma cadeia pode ser formatada por esse formatador.
     * </p>
     *
     * @param value cadeia a ser verificada
     * @return true, se este formatador pode formatar a cadeia dada.
     */
    boolean canBeFormatted(String value);
}
