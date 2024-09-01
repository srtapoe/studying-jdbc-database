package br.com.jdbc.studies.domain.client;

import java.util.Objects;

public class Client {

    private String name;
    private String cpf;
    private String email;

    public Client(DateClient dateClient) {
        this.name = dateClient.name();
        this.cpf = dateClient.cpf();
        this.email = dateClient.email();
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(cpf, client.cpf) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cpf, email);
    }

    @Override
    public String toString() {
        return "Client's date: " +
                "NAME: '" + name + '\'' +
                ",CPF:'" + cpf + '\'' +
                ", EMAIL: '" + email;
    }
}
