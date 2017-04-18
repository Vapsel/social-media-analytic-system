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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String notion;

    @ElementCollection(targetClass = Long.class)
    private List<Long> categoryIds;

    @ElementCollection(targetClass = Long.class)
    private List<Long> relatedNodesIds;

}
