package br.edu.utfpr.pb.pw44s.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(length = 150)
    private String division;

    @Column(length = 50)
    private String postalCode;

    @Column(nullable = false, length = 200)
    private String city;

    @Column(nullable = false, length = 200)
    private String street;

    @Column(length = 50)
    private String addressNumber;

    @Column(nullable = false, length = 50)
    private String addressType;
}