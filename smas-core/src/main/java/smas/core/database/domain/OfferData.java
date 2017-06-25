package smas.core.database.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class OfferData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    private Set<Long> relatedNodesIds;
}
