    public enum Categoria {
        BASICOS("Produtos Básicos"),
        BEBIDAS("Bebidas"),
        LIMPEZA("Limpeza"),
        HIGIENE("Higiene"),
        FRIOS("Frios"),
        HORTIFRUTI("Hortifrúti"),
        PADARIA("Padaria"),
        CONVENIENCIA("Conveniência e Bomboniere");

        private final String descricao;
        private double margemLucro;

        Categoria(String descricao) {
            this.descricao = descricao;
            this.margemLucro = 0.0;
    }
        public String getDescricao() { return descricao; }
        public double getMargemLucro() { return margemLucro; }
        public void setMargemLucro(double margemLucro) { this.margemLucro = margemLucro; }
    }
