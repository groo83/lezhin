package com.lezhin.assignment.domain.memeber;

import com.lezhin.assignment.common.entity.BaseEntity;
import com.lezhin.assignment.domain.memeber.enums.AdultState;
import com.lezhin.assignment.domain.memeber.enums.Authority;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@DynamicInsert // null 제외 insert 하여 ColumnDefault로 설정한 값 적용을 위함
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ColumnDefault("'GENERAL'")
    @Enumerated(EnumType.STRING)
    private AdultState adultState; // 성인인증 여부


    @Builder
    public Member(String email, String name, String password, Authority authority, AdultState adultState) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.authority = authority;
        this.adultState = adultState;
    }

}
