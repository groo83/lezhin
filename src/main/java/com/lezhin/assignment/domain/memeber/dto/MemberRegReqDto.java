package com.lezhin.assignment.domain.memeber.dto;

import com.lezhin.assignment.common.valid.ValidEnum;
import com.lezhin.assignment.domain.memeber.Member;
import com.lezhin.assignment.domain.memeber.enums.AdultState;
import com.lezhin.assignment.domain.memeber.enums.Authority;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRegReqDto {

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "비밀번호은 필수입니다.")
    private String password;

    @ValidEnum(target = AdultState.class, message = "GENERAL(일반) 또는 ADULT(성인) 만 입력해주세요.")
    private String adultState;

    @ValidEnum(target = Authority.class, message = "USER(사용자) 또는 ADMIN(관리자) 만 입력해주세요.")
    private String authority = "USER";

    @Builder
    public MemberRegReqDto(String email, String name, String password, String adultState, String authority) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.adultState = adultState;
        this.authority = authority;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .adultState(AdultState.of(this.adultState))
                .build();
    }
}
