import java.util.Scanner;

public class Main {

    //CONSTANTES DE COLORES
    //public static final String ANSI_RESET = "\u001B[0m";
    //public static final String ANSI_BLACK = "\u001B[30m";
    //public static final String ANSI_RED = "\u001B[31m";
    //public static final String ANSI_GREEN = "\u001B[32m";
    //public static final String ANSI_YELLOW = "\u001B[33m";
    //public static final String ANSI_BLUE = "\u001B[34m";
    //public static final String ANSI_PURPLE = "\u001B[35m";
    //public static final String ANSI_CYAN = "\u001B[36m";

    //COMO USAR LAS CONSTANTES
    //System.out.print(ANSI_RED + "HOLA" + ANSI_RESET);
    static int N=8;

    public static void main(String[] args) {
        int tablero1[][] = new int[N][N];
        int tablero2[][] = new int[N][N];
        int coordenada[] = new int[2];
        int barcos[] = {4,3,3,2,2,2,1,1,1,1};
        // ARRAY DE BARCOS PARA PRUEBAS XD
        // int barcos[] = {1};
        boolean perfe=false;
        while (!perfe) {
            ponAgua(tablero1, tablero2);
            boolean perfe1 = colocarBarcos(tablero1, coordenada, barcos);
            //mostrarTablero(tablero1);
            boolean perfe2 = colocarBarcos(tablero2, coordenada, barcos);
            //mostrarTablero(tablero2);
            if(perfe1 && perfe2) perfe=true;
        }
        imprimeTableros(tablero1,tablero2);
        jugar(tablero1,tablero2,coordenada);
    }

    //FUNCIONES PROPIAS

    //INICIALIZA LOS TABLEROS CON AGUA
    public static void ponAgua(int [][] tableroUsuario, int [][] tableroCPU){
        for(int i=0;i<tableroUsuario.length;i++){
            for(int j=0;j<tableroUsuario.length;j++){
                tableroUsuario[i][j]=0;
                tableroCPU[i][j]=0;
            }
        }
    }

    //MUESTRA EL TABLERO AL USUARIO
    public static void mostrarTablero(int [][] tablero){
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                System.out.print(tablero[i][j]+" ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        System.out.println(" ");
    }

    //MUESTRA AMBOS TABLEROS A LA VEZ
    public static void imprimeTableros(int [][] tablero1, int [][] tablero2){
        System.out.println("   TU TABLERO          TU RIVAL");
        for(int i=0; i<N;i++){
            if(i<N) for (int j = 0; j < N; j++) System.out.print(tablero1[i][j] + " ");
            else for(int j=0;j<N;j++) System.out.print(" ");
            System.out.print(" |  ");
            if(i<N) for(int j=0;j<N;j++) System.out.print(tablero2[i][j]+" ");
            System.out.println();
        }
    }


    //COLOCAR ALEATORIAMENTE LOS BARCOS
    public static boolean colocarBarcos(int [][] tablero, int [] coordenada, int [] barcos){
        int tamaño, intentos=0;
        for(int i=0;i<barcos.length;i++) {
            boolean colocado = false;
            while (!colocado && intentos<100) {
                coordenadaAleatoria(coordenada);
                tamaño = barcos[i];
                intentos++;
                if (sePuedeDer(tablero, coordenada, tamaño)) {
                    colocado=colocarDer(tamaño,tablero,coordenada);
                }else if (sePuedeAbaj(tablero, coordenada, tamaño)){
                    colocado=colocarAbaj(tamaño,tablero,coordenada);
                }
            }
        }
        return true;
    }

    //LA POSICION A COMRPOBAR ESTA EN EL RANGO DEL TABLERO
    public static boolean estaEnRango(int i, int j){
        if(i<0||i>N-1) return false;
        if(j<0||j>N-1) return false;
        return true;
    }


    //PUEDO COLOCAR EL BARCO DE FORMA HORIZONTAL HACIA DERECHA
    public static boolean sePuedeDer(int [][] tablero, int [] coordenada, int tamaño){
        if(coordenada[1]+tamaño-1>=8) return false;
        else for(int i = coordenada[0]-1; i<=coordenada[0]+1;i++){
            for(int j = coordenada[1]-1;j<=coordenada[1]+tamaño;j++)
                if(estaEnRango(i,j)){
                    if(tablero[i][j]!=0) return false;
                }
        }
        return true;
    }

    //COLOCA EL BARCO HACIA LA DERECHA
    public static boolean colocarDer(int tamaño, int [][] tablero, int [] coordenada){
        for (int j = 0; j < tamaño; j++) {
            tablero[coordenada[0]][coordenada[1]+j] = tamaño;
        }
        return true;
    }

    //COMPRUEBA SI SE PUEDE COLOCAR EL BARCO HACIA ABAJO
    public static boolean sePuedeAbaj(int [][] tablero, int [] coordenada, int tamaño){
        if(coordenada[0]+tamaño-1>=8) return false;
        else for(int i = coordenada[0]-1; i<=coordenada[0]+tamaño;i++){
            for(int j = coordenada[1]-1;j<=coordenada[1]+1;j++)
                if(estaEnRango(i,j)){
                    if(tablero[i][j]!=0) return false;
                }
        }
        return true;
    }

    //COLOCA EL BARCO HACIA ABAJO
    public static boolean colocarAbaj(int tamaño, int [][] tablero, int [] coordenada){
        for (int j = 0; j < tamaño; j++) {
            tablero[coordenada[0]+j][coordenada[1]] = tamaño;
        }
        return true;
    }

    public static void jugar(int [][]tablero1, int [][]tablero2,int []coordenada){
        boolean correcto=false, gana=false, turno=true, acierto=true;
        while(!gana) {
            if(turno) {
                turno=juegaJugador(coordenada, tablero2);
                gana = hasGanado(tablero2);
                if(gana) System.out.println("HAS GANADOOOO!!!!!! ENHORABUENA");
            }
            else{
                turno=juegaMaquina(coordenada,tablero1);
                gana=hasGanado(tablero1);
                if(gana) System.out.println("OHHH HAS PERDIDOO!!!! TE GANO UNA MÁQUINA PRINGADO ;)");

            }

        }
    }


    //PIDE LAS COORDENADAS AL JUGADOR PARA ACERTAR BARCOS
    public static void pedirBarco(int []coordenada){
        Scanner teclado= new Scanner(System.in);
        System.out.println("Escribe la coordenada x de donde quieres disparar [0-7]:");
        coordenada[0] = teclado.nextInt();
        System.out.println("Escribe la coordenada y de donde quieres disparar [0-7]:");
        coordenada[1] = teclado.nextInt();
    }

    //SI EL DISPARO ES AGUA O TOCA UN BARCO
    public static boolean tocaBarco(int [][] tablero, int [] coordenada){
        if(tablero[coordenada[0]][coordenada[1]]==0){
            System.out.println("AGUA");
            return false;
        }else{
            System.out.println("TOCADO");
            tablero[coordenada[0]][coordenada[1]]=0;
            return true;
        }
    }

    //TURNO DE JUEGO DEL JUGADOR
    public static boolean juegaJugador(int []coordenada, int [][]tablero){
        boolean acierto=true, correcto=false,gana=false;
        while(acierto && !gana) {
            System.out.println("TURNO DEL JUGADOR:");
            pedirBarco(coordenada);
            correcto=estaEnRango(coordenada[0],coordenada[1]);
            if(!correcto){
                System.out.println("Esa coordenada no está dentro del tablero.");
            }else{
                acierto=tocaBarco(tablero,coordenada);
                Esperar(3);
                //LimpiarPantalla();
                //limpiando();
                if(acierto){
                    gana=hasGanado(tablero);
                }
            }
        }
        return false;
    }

    //TURNO DE JUEGO DE LA MAQUINA
    public static boolean juegaMaquina(int []coordenada, int [][]tablero){
        boolean acierto=true, gana=false;
        while(acierto&&!gana) {
            Esperar(1);
            System.out.println("TURNO DE LA MÁQUINA:");
            coordenadaAleatoria(coordenada);
            Esperar(1);
            System.out.println("LA MÁQUINA ELIGE LA POSICIÓN: ("+coordenada[0]+","+coordenada[1]+")");
            Esperar(1);
            acierto=tocaBarco(tablero,coordenada);
            if(acierto){
                gana=hasGanado(tablero);
            }
            Esperar(1);
        }
        return true;
    }

    //COMPRUEBA SI EL JUGADOR HA GANADO
    public static boolean hasGanado(int [][]tablero){
        for(int i=0;i<tablero.length;i++){
            for(int j=0;j<tablero.length;j++){
                if(tablero[i][j]!=0){
                    return false;
                }
            }
        }
        return true;
    }

    //GENERA COORDENADA ALEATORIAS(PARA ATAQUE Y COLOCACIÓN)
    public static void coordenadaAleatoria(int [] coordenada){
        coordenada[0]=(int)(Math.random()*N);
        coordenada[1]=(int)(Math.random()*N);
    }



    //FUNCIONES DE UTILIDAD
    //BORRA LA PANTALLA
    public static void LimpiarPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // No hacer nada
        }
    }

    //SIMULA TIEMPO DE ESPERA
    public static void Esperar(int nsegundos) {
        try {
            Thread.sleep(nsegundos * 1000L);
        } catch (Exception e) {
            // No hacer nada
        }
    }

    public static void limpiando(){
        for(int i=0;i<50;i++)
            System.out.println();
    }





}