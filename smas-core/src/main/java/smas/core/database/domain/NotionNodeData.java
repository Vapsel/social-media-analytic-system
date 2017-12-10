package smas.core.database.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
public class NotionNodeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private CategoryData category;

    // TODO change mapping (additional column not table)
    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    private Set<Long> relatedNodesIds;

}
