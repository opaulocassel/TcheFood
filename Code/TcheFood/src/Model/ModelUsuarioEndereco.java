package Model;

public class ModelUsuarioEndereco {
    private int id;
    private ModelUsuario usuarioId;
    private ModelCategoriaEndereco categoriaEnderecoId;
    private boolean padrao;
    private String rua1;
    private String rua2;
    private String cidade;
    private String estado;
    private String pais;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelUsuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(ModelUsuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public ModelCategoriaEndereco getCategoriaEnderecoId() {
        return categoriaEnderecoId;
    }

    public void setCategoriaEnderecoId(ModelCategoriaEndereco categoriaEnderecoId) {
        this.categoriaEnderecoId = categoriaEnderecoId;
    }

    public boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
    }

    public String getRua1() {
        return rua1;
    }

    public void setRua1(String rua1) {
        this.rua1 = rua1;
    }

    public String getRua2() {
        return rua2;
    }

    public void setRua2(String rua2) {
        this.rua2 = rua2;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
