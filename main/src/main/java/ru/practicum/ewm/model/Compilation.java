package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COMPILATIONS", schema = "PUBLIC")
@EqualsAndHashCode(exclude = "events")
@ToString(exclude = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean pinned;

    @ManyToMany
    @JoinTable(name = "COMPILATIONS_EVENTS",
            joinColumns = @JoinColumn(name = "COMPILATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "EVENT_ID"))
    private List<Event> events;
}
