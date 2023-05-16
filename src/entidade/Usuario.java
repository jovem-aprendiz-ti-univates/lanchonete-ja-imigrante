package entidade;

public class Usuario {

    private int id;
    private String nome;
    private String senha;
    private char situacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public char getSituacao() {
        return situacao;
    }

    public void setSituacao(char situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " Nome: " + this.getNome() + " Senha: " + this.getSenha() + " Situacao: " + this.getSituacao();
    }
}
