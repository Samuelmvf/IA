package jogo7por7;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    //board é a variável que será meu tabuleiro 7 por 7 de Objetos Fields(campos)
    private Field[][] board;

    public Board(int number_of_holes) {//Recebo a quantidade de buracos que terá no desafio como parâmetro e instâncio a variável board
        //de tamanho 7 por 7.
        this.board = new Field[7][7];
        this.initialBoard(number_of_holes);//Chamo a função initialBoard passando o numero de buracos pro desafio.
    }

    public void initialBoard(int number_of_holes) {

        //Estrutura de repetição para percorrer toda a matriz de fields adicionando um novo field em cada posição da matriz;
        //Após essa estrutura de repetição será gerada uma matriz cheia de fields entretanto todos os atributos de cada field está setado como false nesse momento.
        for (int lines = 0; lines < 7; lines++) {
            for (int columns = 0; columns < 7; columns++) {
                Field f = new Field();
                board[lines][columns] = f;
            }
        }

        Random generator = new Random();
        //Variável para administrar a quantidade de buracos criados.
        int numberOfHolesCreated = 0;
        //Variáveis para armazenar o valor randômica tanto para linha e para coluna.
        int numberLine;
        int numberColumn;

        //Criando numero de buracos
        //Enquanto o numero de buracos criados for menor que o numero de buracos que o desafio deve ter faça:
        while (numberOfHolesCreated < number_of_holes) {

            //Gerando numeros aléatorios para linha e coluna
            numberLine = generator.nextInt(7);
            numberColumn = generator.nextInt(7);
            //Setando o atributo Hole dentro do objeto field da posição randomica como true;
            this.board[numberLine][numberColumn].setHole(true);

            //Zerando o valor do contador para que não ocorra problema com a incrementação dentro do for
            numberOfHolesCreated = 0;

            //Estrutura de repetição para contar a quantidade de buracos já criados.
            for (int lines = 0; lines < 7; lines++) {
                for (int columns = 0; columns < 7; columns++) {
                    if (this.board[lines][columns].isHole()) {
                        numberOfHolesCreated++;
                    }
                }
            }

        }
        //Criando objetivo no tabuleiro

        //Gerando numeros aleatórios para linha e para coluna
        numberLine = generator.nextInt(7);
        numberColumn = generator.nextInt(7);

        //Tratando os numeros aleatórios de forma que peguem um field(campo) que não é buraco para ser o objetivo
        while (this.board[numberLine][numberColumn].isHole()) {
            numberLine = generator.nextInt(7);
            numberColumn = generator.nextInt(7);
        }
        //Setando field(campo) randomico como Objetivo
        this.board[numberLine][numberColumn].setGoal(true);

        //Criando posicionamento do jogador no tabuleiro
        //Gerando numeros aleatórios para linha e para coluna
        numberLine = generator.nextInt(7);
        numberColumn = generator.nextInt(7);

        //Tratando os numeros aleatórios de forma que peguem um field(campo) que não é buraco e nem o objetivo para o jogador começar
        while (this.board[numberLine][numberColumn].isHole() || this.board[numberLine][numberColumn].isGoal()) {
            numberLine = generator.nextInt(7);
            numberColumn = generator.nextInt(7);
        }
        //Setando field(campo) randomico como field inicial do Jogador
        this.board[numberLine][numberColumn].setPlayer(true);

        //Chamando função para Visualizar Tabuleiro
        this.showGame(999);
    }

    public void showGame(int MovimentsNumber) {

        //Valor ficticio para montar design
        if (MovimentsNumber == 999) {
            System.out.println("------------------ Tabuleiro Inicial ------------------");
        } else// Mostra numero de movimentações do jogador
        {
            System.out.println("------------------ Movimentação numero " + MovimentsNumber + " ------------------");
        }
        //Declarada uma matriz chamada Game de String apenas para representar a Matriz de objeto.
        String[][] game = new String[7][7];
        //Montando Layout
        for (int lines = 0; lines < 7; lines++) {
            for (int columns = 0; columns < 7; columns++) {
                if (this.board[lines][columns].isHole()) {
                    game[lines][columns] = "◘ ";
                } else if (this.board[lines][columns].isGoal() && this.board[lines][columns].isPlayer()) {
                    game[lines][columns] = "⌂☺ ";
                } else if (this.board[lines][columns].isGoal()) {
                    game[lines][columns] = "⌂ ";
                } else if (this.board[lines][columns].isPlayer()) {
                    game[lines][columns] = "☺ ";
                } else if (this.board[lines][columns].isRepeated()) {
                    game[lines][columns] = " ∙ ";
                } else {
                    game[lines][columns] = "█ ";
                }
            }
        }
        //Imprimindo Layout
        for (int lines = 0; lines < 7; lines++) {
            System.out.println("\n");
            for (int columns = 0; columns < 7; columns++) {
                System.out.print(game[lines][columns]);
            }
        }
        System.out.println("\n");
        System.out.println("------------------------------------------------------------");

    }

    public void playGame() {
        //Variável para realizar o papel de contador do numero de movimentos
        int MovimentsNumber = 0;
        //Variável para armazenar a localização do campo objetivo quando encontrado
        Position finalField = null;
        //Variável para armazenar a localização do campo que foi sorteado para ser o proximo movimento
        Position randomField = new Position(0, 0);
        //Variável responsável por guardar a localização do Player;
        Position whereisPlayer = hereisPlayer();//Função hereisPlayer() retorna a localização do player;

        //Enquanto o campo que player está não for o campo objetivo faça:
        while (!this.board[whereisPlayer.getLine()][whereisPlayer.getColumn()].isGoal()) {

            //fieldsReached chama a função sucessora que retorna um ArrayList com as posições dos campos alcançados a partir do ponto atual
            ArrayList<Position> fieldsReached = SucessorFunction(whereisPlayer);

            //Estrutura de repeticao para pecorrer os possiveis estados alcancados pela funcao sucessora caso encontre o estado final salva o estado final
            //Na variavel finalField
            for (int i = 0; i < fieldsReached.size(); i++) {
                if (this.board[fieldsReached.get(i).getLine()][fieldsReached.get(i).getColumn()].isGoal()) {
                    finalField = new Position(fieldsReached.get(i).getLine(), fieldsReached.get(i).getColumn());
                    break;
                }
            }
            //Caso não encontrou o estado final na função sucessora
            if (finalField == null) {
                //No field atual seta a presença do player como false
                this.board[whereisPlayer.getLine()][whereisPlayer.getColumn()].setPlayer(false);
                //No field atual seta o atributo repeated como true simbolizando que já passou no campo.
                this.board[whereisPlayer.getLine()][whereisPlayer.getColumn()].setRepeated(true);

                Random rand = new Random();
                //Variavel para selecionar randomicamente o proximo campo a visitar
                int selectRandomField;
                //ArrayList que armazenará quais dos estados alcançados através do campo atual do jogador
                //ele não visitou ainda
                ArrayList<Position> noRepeated = findFieldsNoRepeated(fieldsReached); //funcao findFieldsNoRepeated retorna arraylist com estados dentre os retornados pela função sucessora que ainda não foram visitados

                //Foi definida a prioridade de expansão para estados que ainda não foram visitados antes dos estados ja visitiados
                //Sendo assim caso a lista de fields(campos) não visitados esteja vazia então sera feito o if
                if (noRepeated.isEmpty()) {
                    //Gera numero randomico entre 0 até o tamanho da lista - 1
                    selectRandomField = rand.nextInt(fieldsReached.size());
                    //seta a linha e a coluna do objeto randomField utilizando o objeto com indice semelhante ao do numero randomico sorteado que está contido no arraylist fieldReacheds
                    randomField.setLine(fieldsReached.get(selectRandomField).getLine());
                    randomField.setColumn(fieldsReached.get(selectRandomField).getColumn());
                    //Seta dentro do campo sorteado, o atributo player como true avisando então que o player está nesse campo
                    this.board[randomField.getLine()][randomField.getColumn()].setPlayer(true);

                } else {//Caso a lista de fields(campos) não visitados tenha algo
                    //Gera numero randomico entre 0 até o tamanho da lista - 1
                    selectRandomField = rand.nextInt(noRepeated.size());
                    //seta a linha e a coluna do objeto randomField utilizando o objeto com indice semelhante ao do numero randomico sorteado que está contido no arraylist noRepeated
                    randomField.setLine(noRepeated.get(selectRandomField).getLine());
                    randomField.setColumn(noRepeated.get(selectRandomField).getColumn());
                    //Seta dentro do campo sorteado, o atributo player como true avisando então que o player está nesse campo
                    this.board[randomField.getLine()][randomField.getColumn()].setPlayer(true);
                }
                //soma movimentação ao contador de movimentos
                MovimentsNumber++;

            }// Caso encontrou o estado final na função sucessora no inicio do while
            else {
                //No field atual seta a presença do player como false
                this.board[whereisPlayer.getLine()][whereisPlayer.getColumn()].setPlayer(false);
                //No field atual seta o atributo repeated como true simbolizando que já passou no campo.
                this.board[whereisPlayer.getLine()][whereisPlayer.getColumn()].setRepeated(true);
                //Seta no field(campo) que possui as posições contidas no finalField como true o atributo player
                //informando que o player chegou ao estado final
                this.board[finalField.getLine()][finalField.getColumn()].setPlayer(true);
                //soma movimentação ao contador de movimentos
                MovimentsNumber++;
                System.out.println("--------------------------- FIM ----------------------------");
            }
            //Atualização localização do player
            whereisPlayer = hereisPlayer();
            //Mostra a interface
            this.showGame(MovimentsNumber);
        }

    }

    public ArrayList<Position> SucessorFunction(Position now) {

        //ArrayList para armazenar as posições dos estados alcançados a partir do estado do player
        ArrayList<Position> fieldsReached = new ArrayList<>();

        
        //Salvando o numero das posições em contorno ao centro/player
        Position a = new Position(((int) now.getLine()) - 1, ((int) now.getColumn()) - 1);
        fieldsReached.add(a);
        Position b = new Position(((int) now.getLine()) - 1, ((int) now.getColumn()));
        fieldsReached.add(b);
        Position c = new Position(((int) now.getLine()) - 1, ((int) now.getColumn()) + 1);
        fieldsReached.add(c);

        Position d = new Position(((int) now.getLine()), ((int) now.getColumn()) - 1);
        fieldsReached.add(d);
        Position e = new Position(((int) now.getLine()), ((int) now.getColumn()) + 1);
        fieldsReached.add(e);

        Position f = new Position(((int) now.getLine()) + 1, ((int) now.getColumn()) - 1);
        fieldsReached.add(f);
        Position g = new Position(((int) now.getLine()) + 1, ((int) now.getColumn()));
        fieldsReached.add(g);
        Position h = new Position(((int) now.getLine()) + 1, ((int) now.getColumn()) + 1);
        fieldsReached.add(h);

        //Removendo objetos que contem indices fora da matriz 7x7
        for (int i = fieldsReached.size() - 1; i >= 0; i--) {

            if (fieldsReached.get(i).getLine() < 0 || fieldsReached.get(i).getLine() > 6 || fieldsReached.get(i).getColumn() < 0 || fieldsReached.get(i).getColumn() > 6) {
                fieldsReached.remove(fieldsReached.get(i));
            }
        }
        //Removendo buracos
        for (int i = fieldsReached.size() - 1; i >= 0; i--) {

            if (this.board[fieldsReached.get(i).getLine()][fieldsReached.get(i).getColumn()].isHole()) {
                fieldsReached.remove(fieldsReached.get(i));
            }
        }
        //retornando lista de estados alcançados já tratados
        return fieldsReached;
    }

    //Função que percorre o tabuleiro todo em busca do player e retorna a posição em que ele se encontra
    public Position hereisPlayer() {
        Position p = null;

        for (int lines = 0; lines < 7; lines++) {
            for (int columns = 0; columns < 7; columns++) {
                if (this.board[lines][columns].isPlayer()) {
                    p = new Position(lines, columns);
                    return p;
                }

            }
        }
        return p;
    }

    public Field[][] getBoard() {
        return board;
    }

    public void setBoard(Field[][] board) {
        this.board = board;
    }
    //Função que recebe os fields(campos) alcançados através função sucessora e retorna um arraylist com os fields dentre estes que ainda não foram visitados
    public ArrayList<Position> findFieldsNoRepeated(ArrayList<Position> fieldsReached) {
        ArrayList<Position> noRepeated = new ArrayList<>();
        Position p;
        for (int i = 0; i < fieldsReached.size(); i++) {
            if (!this.board[fieldsReached.get(i).getLine()][fieldsReached.get(i).getColumn()].isRepeated()) {
                p = new Position(fieldsReached.get(i).getLine(), fieldsReached.get(i).getColumn());
                noRepeated.add(p);
            }
        }
        return noRepeated;
    }

}
