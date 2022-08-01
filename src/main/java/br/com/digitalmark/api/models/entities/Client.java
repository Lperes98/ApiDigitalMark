package br.com.digitalmark.api.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client extends RepresentationModel<Client> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient")
    @JsonProperty("id")
    private int idClient;

    @Column(name = "nameClient")
    @NotNull
    @JsonProperty("name")
    private String nameClient;

    @NotNull
    @Column(name = "technologyClient")
    @JsonProperty("tec")
    private String technologyClient;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id")
   // @NotNull
    private List<Project> projects;


}
