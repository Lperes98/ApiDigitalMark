package br.com.digitalmark.api.controllers;

import br.com.digitalmark.api.models.entities.Client;
import br.com.digitalmark.api.models.entities.Project;
import br.com.digitalmark.api.models.repositories.ClientRepository;
import br.com.digitalmark.api.models.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/cliente/projeto")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;


    @RequestMapping(value = "/buscarProjeto/{id}", method = RequestMethod.GET)
    public Project findByProjectId(@PathVariable int id){
        return projectRepository.findById(id);

    }

    @RequestMapping(value = "/buscarProjeto/{nameProject}", method = RequestMethod.GET)
    public List<Project> findByNameProject(@PathVariable String nameProject) {
        List<Project> projects = projectRepository.findByNameIgnoringCase(nameProject);
        return projectRepository.findByNameIgnoringCase(nameProject);
    }

    @RequestMapping(value = "/buscarProjeto/{numeroPagina}/{qntPagina}", method = RequestMethod.GET)
    public Iterable<Project> findByPage(@PathVariable int numeroPagina, @PathVariable int qntPagina) {
        if(qntPagina >=20) {
            qntPagina = 10;
        }
        Pageable page = PageRequest.of(numeroPagina, qntPagina);
        return projectRepository.findAll(page);
    }

    @RequestMapping(value = "/cadastrarProjeto", method = RequestMethod.POST)
    public Project creatProject(Project project){
        projectRepository.save(project);
        return project;
    }

    @RequestMapping(value = "/projeto/{id}", method = RequestMethod.DELETE)
    public void deleteProjectById(@PathVariable int id){
        projectRepository.deleteById(id);
    }

    @RequestMapping(value = "/alterarProjeto", method = RequestMethod.PATCH)
    public Project updateProject(Project project){
        projectRepository.save(project);
        return project;
    }



}
