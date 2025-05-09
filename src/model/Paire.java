package model;

/*Classe qui implemente le concept de paire ou le premier element et le deuxieme element peuvent etre de types different */
public class Paire<K,V> {
    private final K premierElement;
    private final V deuxiemeElement;

    /*constructeur */
    public Paire(K premierElement, V deuxiemeElement){
        this.premierElement = premierElement;
        this.deuxiemeElement = deuxiemeElement;
    }

    /*getters */
    public K fst() {
        return premierElement;
    }

    public V snd() {
        return deuxiemeElement;
    }

}
