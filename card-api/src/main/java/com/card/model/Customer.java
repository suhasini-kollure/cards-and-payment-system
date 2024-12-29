package com.card.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customer_id")
    @Size(min = 8, max = 20, message = "Customer ID length must be between 8 and 20 characters.")
    private String customerId;

    @NotNull(message = "Name can't be null.")
    @Size(min = 2, max = 20, message = "Name length must be between 2 and 20 characters.")
    private String name;

    @NotNull(message = "Age can't be null.")
    @Size(min = 1, max = 3, message = "Age length must be between 1 and 3 digits.")
    private String age;

    @NotNull(message = "Address can't be null.")
    @Size(min = 5, max = 100, message = "Address length must be between 5 and 100 characters.")
    private String address;

    @NotNull(message = "Account number can't be null.")
    @Column(unique = true)
    @Size(max = 12, message = "Account number must contain exactly 12 digits.")
    private String accountNumber;

    @NotNull(message = "IFSC can't be null.")
    @Size(max = 8, message = "IFSC length must be exactly 8 characters.")
    private String ifsc;

    @NotNull(message = "Branch can't be null.")
    @Size(min = 5, max = 20, message = "Branch length must be between 5 and 20 characters.")
    private String branch;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Card> cards;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Payment> payments;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private Credentials credentials;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Customer customer)) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy
                ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy
                ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        return getCustomerId() != null && Objects.equals(getCustomerId(), customer.getCustomerId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy
                ? hibernateProxy
                        .getHibernateLazyInitializer()
                        .getPersistentClass()
                        .hashCode()
                : getClass().hashCode();
    }
}
