package br.com.digitalmark.api.models.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Project extends RepresentationModel<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProject")
    @JsonProperty("id")
    private int idProject;

    @Column(name = "nameProject", nullable = false)
    @NotBlank
    @JsonProperty("name")
    private String nameProject;





}
