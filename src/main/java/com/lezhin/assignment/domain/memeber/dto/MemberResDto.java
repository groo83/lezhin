package com.lezhin.assignment.domain.memeber.dto;

import com.lezhin.assignment.common.dto.BaseResDto;
import com.lezhin.assignment.common.jwt.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public static MemberResDto from(TokenDto dto) {
        return new MemberResDto(dto.getGrantType(), dto.getAccessToken(), dto.getRefreshToken(), dto.getAccessTokenExpiresIn());
    }
}