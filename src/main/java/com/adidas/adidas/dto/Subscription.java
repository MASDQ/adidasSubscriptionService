package com.adidas.adidas.dto;

import lombok.*;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DTO for subscriptions
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "Id")
    @GeneratedValue//(strategy = GenerationType.AUTO)
    private Integer id;

    //Required Values
    @Column(name = "Email")
    @Email
    @NotNull
    private String email;

    @Column(name = "DateOfBirth")
    @NotNull
    private Date dateOfBirth;

    @Column(name = "NewsletterId")
    @NotNull
    private String newsletterId;

    @Column(name = "Consent")
    @NotNull
    private Boolean consent;

    //Optional Values
    @Column(name = "Gender")
    @Min(3)
    @Max(20)
    @Nullable
    private String gender;

    @Column(name = "FirstName")
    @Nullable
    @Min(3)
    @Max(20)
    private String firstName;
}
