package Act1;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *    Java Program to Implement Gale Shapley Algorithm 
 **/

/** Class GaleShapley **/
public class GaleShapley {
    //
    private int N, engagedCount;
    private String[][] menPref;
    private String[][] womenPref;
    private String[] men;
    private String[] women;
    private String[] womenPartner;
    private int[] womenPartnerIndex;
    private boolean[] menEngaged;

    // extension del problema
    private boolean[][] p;

    /** Constructor **/
    public GaleShapley(String[] m, String[] w, String[][] mp, String[][] wp, boolean[][] p) {
        N = mp.length;
        engagedCount = 0;
        men = m;
        women = w;
        menPref = mp;
        womenPref = wp;
        menEngaged = new boolean[N];
        womenPartner = new String[N];
        womenPartnerIndex = new int[N];

        this.p = p;

        calcMatches();
    }

    /** function to calculate all matches **/
    private void calcMatches()
    {
        //preprocessing();
        //Bucle mientras no se haya emparejado a todas las personajes
        while (engagedCount < N)
        {
            
                int free;
                //Busca al primer hombre soltero al cual poder emparejar.
            for (free = 0; free < N; free++)
                if (!menEngaged[free])
                    break;
            
            //Se itera indefinidamente mientras no se hayan analizados todas las mujeres 
            //y el hombre siga soltero(es decir,no se haya emparejado en alguna iteracion anterior)
            for (int i = 0; i < N && !menEngaged[free]; i++)
            {                 
                

                //Se obtiene el indice de la mujer que se encuentra en la posicion i del arreglo de preferencias del hombre
                int index = womenIndexOf(menPref[free][i]);

                //Si la pareja formada por hombre free y mujer i es prohibida, se omite su analisis.
                System.out.println("PRIMER IF:" +free+index);
                if(!p[free][index]){

                //Usando el indice anterior, se ve si la mujer i esta emparejada actualmente.
                //Si esta soltera, se emparejan entre si.
                //Si esta emparejada, se obtiene a su pareja actual y batallan por ver a quien prefiere mas la mujer.
                //La mujer se quedara con aquel hombre que prefiera.
                if (womenPartner[index] == null)
                {
                    womenPartner[index] = men[free];
                    womenPartnerIndex[index] = free;
                    menEngaged[free] = true;
                    engagedCount++;
                }
                else
                {
                    String currentPartner = womenPartner[index];
                    if (morePreference(currentPartner, men[free],womenPartnerIndex[index],free, index))
                    {
                        womenPartner[index] = men[free];
                        womenPartnerIndex[index] = free;
                        menEngaged[free] = true;
                        menEngaged[menIndexOf(currentPartner)] = false;
                    }
                }
                
            }
            printCouples();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            }     
            
                   
        }
        printCouples();
    }

    /** function to check if women prefers new partner over old assigned partner **/
    private boolean morePreference(String curPartner, String newPartner,int curPartnerIndex,int newPartnerIndex, int index) {
        boolean result = false;
        boolean condicion = false;
        for (int i = N - 1; i >= 0; i--) {
            if (womenPref[index][i].equals(newPartner) && !condicion){
                result = true;
                condicion = estaEnUnProhibido(newPartnerIndex);
                System.out.println("1"+newPartner+": "+condicion);
            }
            if (womenPref[index][i].equals(curPartner) && !condicion){
                result = false;
                condicion = estaEnUnProhibido(curPartnerIndex);
                System.out.println("2"+curPartner+": "+condicion);
            }
        }
        return result;
    }

    private boolean estaEnUnProhibido(int hombre){
        for(int i=0;i<p[hombre].length;i++){
            if(p[hombre][i]){
                return true;
            }
        }
        return false;
    }

    /** get men index **/
    private int menIndexOf(String str) {
        for (int i = 0; i < N; i++)
            if (men[i].equals(str))
                return i;
        return -1;
    }

    /** get women index **/
    private int womenIndexOf(String str) {
        for (int i = 0; i < N; i++)
            if (women[i].equals(str))
                return i;
        return -1;
    }

    /** print couples **/
    public void printCouples() {
        System.out.println("Couples are : ");
        for (int i = 0; i < N; i++) {
            System.out.println(womenPartner[i] + " " + women[i]);
        }
    }

    /** main function **/
    public static void main(String[] args) {
        System.out.println("Gale Shapley Marriage Algorithm\n");

        /** list of men **/
        String[] m = { "M1", "M2", "M3", "M4", "M5" };

        /** list of women **/
        String[] w = { "W1", "W2", "W3", "W4", "W5" };

        /** men preference **/
        String[][] mp = { { "W5", "W2", "W3", "W4", "W1" },
                { "W2", "W5", "W1", "W3", "W4" },
                { "W4", "W3", "W2", "W1", "W5" },
                { "W1", "W2", "W3", "W4", "W5" },
                { "W5", "W2", "W3", "W4", "W1" } };
        /** women preference **/
        String[][] wp = { { "M5", "M3", "M4", "M2", "M1" },
                { "M4", "M2", "M3", "M5", "M1" },
                { "M4", "M5", "M3", "M2", "M1" },
                { "M5", "M2", "M3", "M4", "M1" },
                { "M2", "M5", "M4", "M3", "M1" } };

        /**
         * Lista de parejas prohibidas. El booleano en posicion [i,j] representa si el
         * hombre i puede estar
         * en pareja con la mujer j
         **/
        boolean[][] p = { { false, true, true, true, true },
                { false, true, true, true, true },
                { false, false, false, false, false },
                { false, false, false, false, false },
                { false, false, false, false, false } };
                /*Se podria pensar como un problema de N torres. Pareciera que este problema es posible de resolver con un algoritmo
                 polinomial,
                 No es evidencia fiable, pero en este foro se habla de eso.No profundizo porque no es parte del enunciado:
                 https://cs.stackexchange.com/questions/28413/how-hard-is-this-constrained-n-rooks-problem
                */
        
        GaleShapley gs = new GaleShapley(m, w, mp, wp, p);
    }
}
