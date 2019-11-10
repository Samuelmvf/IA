package jogo7por7;

public class Field {

    private boolean Goal; //Variavel Booleana para saber se é o estado final/ estado objetivo a ser alcançado.
    private boolean Repeated; //Variável Booleana para saber se o Jogador ja passou nesse campo.
    private boolean Hole; //Variável Booleana para saber se esse campo é um buraco.
    private boolean Player; //Variável Booleana para saber se o jogador está nesse campo atualmente..

    public Field() {//Setando construtor como tudo falso e sera dado o devido valor para o campo na construção do tabuleiro
        this.Goal = false;
        this.Repeated = false;
        this.Hole = false;
        this.Player = false;
    }

    public boolean isGoal() {
        return Goal;
    }

    public void setGoal(boolean Goal) {
        this.Goal = Goal;
    }

    public boolean isRepeated() {
        return Repeated;
    }

    public void setRepeated(boolean Repeated) {
        this.Repeated = Repeated;
    }

    public boolean isHole() {
        return Hole;
    }

    public void setHole(boolean Hole) {
        this.Hole = Hole;
    }

    public boolean isPlayer() {
        return Player;
    }

    public void setPlayer(boolean Player) {
        this.Player = Player;
    }
}
