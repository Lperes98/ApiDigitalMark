package br.com.digitalmark.api.controllers;

import br.com.digitalmark.api.models.entities.Client;
import br.com.digitalmark.api.models.entities.Project;
import br.com.digitalmark.api.models.repositories.ClientRepository;
import br.com.digitalmark.api.models.repositories.ProjectRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@Api(value = "API REST")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @RequestMapping(value = "cliente/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna um Cliente")
    public ResponseEntity<Client> findByClientId(@PathVariable int id){
        Optional<Client> client = Optional.ofNullable(clientRepository.findById(id));
        if(client.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            client.get().add(linkTo(methodOn(ClientController.class).findAll()).withRel("Lista de Clientes"));
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "clientes", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna uma Lista de Clientes")
    public ResponseEntity<List<Client>> findAll(){
       List<Client> clients = clientRepository.findAll();
        if(clients.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Client client : clients){
                int id = client.getIdClient();
                client.add(linkTo(methodOn(ClientController.class).findByClientId(id)).withSelfRel());
            }
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "cliente", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna um Cliente por Nome")
    public List<Client> findByNameClient(@RequestParam(name = "name") String name) {
        return clientRepository.findByNameIgnoringCase(name);
    }

    @RequestMapping(value = "clientes/{numeroPagina}/{qntPagina}", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna uma Lista de Clientes Paginada")
    public Iterable<Client> findByPageClient(@PathVariable int numeroPagina, @PathVariable int qntPagina) {
        if(qntPagina >=20) {
            qntPagina = 10;
        }
        Pageable page = PageRequest.of(numeroPagina, qntPagina);
        return clientRepository.findAll(page);
    }

    @RequestMapping(value = "cliente",method = RequestMethod.POST)
    @ApiOperation(value = "Cria um Cliente")
    public ResponseEntity creatClient(@Valid @RequestBody Client client){
        clientRepository.save(client);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "cliente/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deleta um Cliente por ID")
    public ResponseEntity deleteById(@PathVariable int id){
        clientRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "clientes", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deleta todos os Clientes")
    public ResponseEntity deleteAll(){
        clientRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "cliente",method = RequestMethod.PATCH)
    @ApiOperation(value = "Atualiza um Cliente")
    public ResponseEntity updateClient(@RequestBody @Valid Client client){
        clientRepository.save(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "cliente",method = RequestMethod.PUT)
    @ApiOperation(value = "Atualiza um Cliente")
    public ResponseEntity updateClientPut(@RequestBody @Valid Client client){
        clientRepository.save(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    // PROJECT

    @RequestMapping(value = "projeto/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna um Projeto")
    public ResponseEntity<Project> findByProjectId(@PathVariable int id){
       Optional<Project> project = Optional.ofNullable(projectRepository.findById(id));
       if(project.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }else {
           project.get().add(linkTo(methodOn(ClientController.class).findAllProject()).withRel("Lista de Projetos"));
           return new ResponseEntity<>(project.get(), HttpStatus.OK);
       }
    }

    @RequestMapping(value = "projetos", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna uma Lista de Projetos")
    public ResponseEntity<List<Project>> findAllProject(){
        List<Project> projects = projectRepository.findAll();
        if(projects.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Project project : projects){
                int id = project.getIdProject();
                project.add(linkTo(methodOn(ClientController.class).findByProjectId(id)).withSelfRel());
            }
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "projeto", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna um Projeto por Nome")
    public List<Project> findByNameProject(@RequestParam(value = "name") String name) {
        return projectRepository.findByNameIgnoringCase(name);
    }

    @RequestMapping(value = "projetos/{numeroPagina}/{qntPagina}", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna uma Lista de Projetos Paginado")
    public Iterable<Project> findByPage(@PathVariable int numeroPagina, @PathVariable int qntPagina) {
        if(qntPagina >=20) {
            qntPagina = 10;
        }
        Pageable page = PageRequest.of(numeroPagina, qntPagina);
        return projectRepository.findAll(page);
    }

    @RequestMapping(value = "{client}/projeto", method = RequestMethod.POST)
    @ApiOperation(value = "Cria um Projeto inserindo um Cliente")
    public ResponseEntity creatProject( @RequestBody @Valid Project project,@PathVariable int client){
        Client client1 = clientRepository.findById(client);
        client1.getProjects().add(project);
        clientRepository.save(client1);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "projeto/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deleta um Projeto por ID")
    public ResponseEntity deleteProjectById(@PathVariable int id){
        projectRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "projetos", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deleta todos os Projetos")
    public ResponseEntity deleteAllProject(){
        projectRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "projeto", method = RequestMethod.PATCH)
    @ApiOperation(value = "Atualiza um Projeto")
    public ResponseEntity updateProject(@RequestBody @Valid Project project){
        projectRepository.save(project);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "projeto", method = RequestMethod.PUT)
    @ApiOperation(value = "Atualiza um Projeto")
    public ResponseEntity updateProjectPut(@RequestBody @Valid Project project){
        projectRepository.save(project);
        return new ResponseEntity(HttpStatus.OK);
    }

}
