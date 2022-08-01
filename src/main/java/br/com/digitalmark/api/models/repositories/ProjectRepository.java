package br.com.digitalmark.api.models.repositories;

import br.com.digitalmark.api.models.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {

    @Query(value = "select p from Project p where p.nameProject like %?1%")
    List<Project> findByNameIgnoringCase(String name);

    public List<Project> findAll();

    public Project findById(int id);
}
