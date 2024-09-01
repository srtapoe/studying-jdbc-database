package br.com.jdbc.studies.domain.account;

import br.com.jdbc.studies.domain.client.DateClient;

public record DateAccount(int number, DateClient dateClient) {
}
