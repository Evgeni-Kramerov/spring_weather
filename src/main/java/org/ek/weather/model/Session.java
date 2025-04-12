package org.ek.weather.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "expiresat")
    private Date expiryDate;

    public Session(User user, Date expiryDate) {
        this.user = user;
        this.expiryDate = expiryDate;
    }
}
