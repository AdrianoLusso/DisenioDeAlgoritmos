package Parte1.Act3;

import java.util.LinkedList;

public class Trie {

    static final int ALPHABET_SIZE = 26;

    static class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        // verdadero si el nodo representa el fin de la palabra
        boolean isEndOfWord;

        //Lista de sinonimos relacionada a la palabra cuyo ultimo nodo sea este(es decir, que este nodo este en true).
        LinkedList sinonims = new LinkedList<String>();

        TrieNode() {
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    };

    static TrieNode root;

    //Si no existe la palabra en el arbol, la inserta
    //Si ya existe(osea, es subpalabra de otra palabra ya insertada), solo marca el nodo como final.
    static void insert(String key) {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();

            pCrawl = pCrawl.children[index];
        }

        // marca el ultimo nodo encontrado como el fin de la palabra
        pCrawl.isEndOfWord = true;
    }
    /*
     Calculo de eficiencia:
     Como tenemos un bucle definido de n repeticiones, tal que n=longitud de la 
     palabra que quiere ingresarse en el diccionario, sablemos que es de O(n)
     */

    static boolean insertSinonim(String key,String sinm){
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        //Voy buscando la palabra a la que quiero agregar sinonimo
        for (level = 0; level < length; level++) {
            index = key.charAt(level) - 'a';

            //Si llego al fin del camino sin que el nodo final tenga el flag de final de palabra, la palabra a la que
            //quiero agregar el sinonimo no existe
            if (pCrawl.children[index] == null)
                return false;

            pCrawl = pCrawl.children[index];
        }

        pCrawl.sinonims.add(sinm);
        //Se logro agregar el sinonimo a la palabra
        return true;
    }
    /*
     Calculo de eficiencia:
     Al igual que con el insertar palabra, el insertar sinonimo tiene O(n). En este caso, n es igual a
     la longitud de la palabra a la que se le va a agregar el sinonimo, pudiendo truncarse el recorrido
     en situaciones donde la palabra a la que se le piensa agregar el sinonimo no existe en el diccionario. 
     */

    static boolean search(String key) {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            index = key.charAt(level) - 'a';

            if (pCrawl.children[index] == null)
                return false;

            pCrawl = pCrawl.children[index];
        }

        return (pCrawl.isEndOfWord);
    }
    /*
     Calculo de eficiencia:
     Para encontrar si una palabra esta en el diccionario, debe recorrerse(en el peor caso posible) n nodos correspondientes
     a los n caracteres de la palabra. Por lo tanto, el orden es O(n)
     */

    static LinkedList searchSynonims(String key) {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            index = key.charAt(level) - 'a';

            //Si la palabra a buscar no existe, sus sinonimos tampoco(lista nula).
            if (pCrawl.children[index] == null)
                return null;

            pCrawl = pCrawl.children[index];
        }

        return pCrawl.sinonims;
    }
    /*
     Calculo de eficiencia:
     Al igual la busqueda de palabras en el diccionario, la busqueda sinonimos requiere(primero que nada) recorrer n nodos
     que representan los n caracteres de la palabra cuyos sinonimos queres ver. Por eso, O(n)
     */


    public static void main(String args[]) {
        String keys[] = { "enrique","encendido","zorro","automovil","autmatico","marmota" };

        String output[] = { "No esta en el arbol", "Esta en el arbol" };

        root = new TrieNode();

        // Poner palabras en el arbol
        int i;
        for (i = 0; i < keys.length; i++)
            insert(keys[i]);

        insertSinonim("encendido", "prendido");
        insertSinonim("encendido", "ardiente");

        // Buscar diferentes palabras
        if (search("enriquee") == true)
            System.out.println("enriquee --- " + output[1]);
        else
            System.out.println("enriquee --- " + output[0]);

        if (search("enrique") == true)
            System.out.println("enrique --- " + output[1]);
        else
            System.out.println("enrique --- " + output[0]);

        if (search("encendido") == true)
            System.out.println("encendido --- " + output[1]);
        else
            System.out.println("encendido --- " + output[0]);

        if (search("encendiote") == true)
            System.out.println("encendiote --- " + output[1]);
        else
            System.out.println("encendiote --- " + output[0]);

        System.out.println("Sinonimos de encendido: "+searchSynonims("encendido"));
        System.out.println("Sinonimos de encendidote: "+searchSynonims("encendiote"));


    }

}