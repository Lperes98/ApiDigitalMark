package br.com.digitalmark.api.models.repositories;

import br.com.digitalmark.api.models.entities.Client;
import br.com.digitalmark.api.models.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {
    public Client findById(int id);

    public List<Client> findAll();

    @Query(value = "select c from Client c where c.nameClient like %?1%")
    List<Client> findByNameIgnoringCase(String name);
}
