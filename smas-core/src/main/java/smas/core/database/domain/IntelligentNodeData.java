package smas.core.database.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class IntelligentNodeData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String notion;

    @OneToMany(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IntelligentNodeData> relatedNodes;

}
