package com.github.IkJuLim.accommodation_booking.domain;

import com.github.IkJuLim.accommodation_booking.domain.common.BaseEntity;
import com.github.IkJuLim.accommodation_booking.domain.enums.UserRoll;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private UserRoll userRoll;

    @Setter
    private String nickname;

    @Setter
    @Column(columnDefinition = "SMALLINT")
    @ColumnDefault("1")
    private int status; //  0: 비활성화, 1: 활성
}
