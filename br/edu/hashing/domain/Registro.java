package br.edu.hashing.domain;

/**
 * Representa um registro de 9 dígitos. 
 * A ordem e eventuais zeros à esquerda são relevantes apenas para exibição, 
 * mas internamente usaremos um int para facilitar a função‐hash.
 */
public class Registro {
    private final String codigoStr;   // Ex.: "000001240"
    private final int codigoInt;      // O valor numérico (0 a 999_999_999)

    public Registro(String codigoStr) {
        if (codigoStr == null || codigoStr.length() != 9)
            throw new IllegalArgumentException("Código deve ter exatamente 9 dígitos");
        this.codigoStr = codigoStr;
        // Converter em inteiro (pode remover zeros à esquerda ao converter)
        this.codigoInt = Integer.parseInt(codigoStr);
    }

    public String getCodigoStr() {
        return codigoStr;
    }

    public int getCodigoInt() {
        return codigoInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registro)) return false;
        Registro outro = (Registro) o;
        return this.codigoInt == outro.codigoInt;
    }

    @Override
    public int hashCode() {
        // Não será usado pelo nosso código de hashing, mas é bom implementar:
        return Integer.hashCode(codigoInt);
    }

    @Override
    public String toString() {
        return codigoStr;
    }
}
