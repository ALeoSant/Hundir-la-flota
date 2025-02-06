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


    public static void main(String[] args) {
        int N=8;
        int tablero1[][] = new int[N][N];
        int tablero2[][] = new int[N][N];
        int coordenada[] = new int[2];
        int barcos[] = {4,3,3,2,2,2,1,1,1,1};

        ponAgua(tablero1,tablero2);
        mostrarTablero(tablero1);
        colocarBarcos(tablero1,coordenada,barcos,N);
        mostrarTablero(tablero1);
        colocarBarcos(tablero2,coordenada,barcos,N);
        mostrarTablero(tablero1);
        mostrarTablero(tablero2);

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
        for(int i=0;i<tablero.length;i++){
            for(int j=0;j< tablero.length;j++){
                System.out.print(tablero[i][j]+" ");
            }
            System.out.println(" ");
        }
    }


    //COLOCAR ALEATORIAMENTE LOS BARCOS
    public static void colocarBarcos(int [][] tablero, int [] coordenada, int [] barcos, int N){
        int tamaño;
        for(int i=0;i<tablero.length;i++) {
            boolean colocado = false;
            while (!colocado) {
                coordenadaAleatoria(N, coordenada);
                tamaño = barcos[i];
                if (sePuede(tablero, coordenada, tamaño, N))
                    for(int j=0;j<tamaño;j++){
                        tablero[coordenada[0]][coordenada[1]+j]=1;
                        colocado=true;
                    }

            }
        }
    }

    //SE PEUDE COLOCAR BARCO
    public static boolean sePuede(int [][] tablero, int [] coordenada, int tamaño, int N){
        //Comprueba que no se salga del tablero
        if(coordenada[0]+tamaño>=N) return false;
        if(coordenada[1]+tamaño>=N) return false;

        //Comprueba que no haya barcos cerca por columnas
        for(int i=coordenada[0]-1;i<=coordenada[0]+tamaño;i++){
            for(int j=coordenada[1]-1;j<coordenada[1]+1;j++){
                if(tablero[i][j]==1) return false;
            }
        }

        //Comprueba que no haya barcos cerca por filas
        for(int i=coordenada[0]-1;i<=coordenada[0]+1;i++){
            for(int j=coordenada[1]-1;j<coordenada[1]+tamaño;j++){
                if(tablero[i][j]==1) return false;
            }
        }
        return true;
    }

    //GENERA COORDENADA ALEATORIAS(PARA ATAQUE Y COLOCACIÓN)
    public static void coordenadaAleatoria(int N, int [] coordenada){
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




}