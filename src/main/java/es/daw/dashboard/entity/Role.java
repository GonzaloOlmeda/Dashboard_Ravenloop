package es.daw.dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName nombre;

    public enum RoleName {
        ADMIN, USER
    }



}
