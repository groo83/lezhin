package com.lezhin.assignment.domain.memeber.service;

import com.lezhin.assignment.common.code.ErrorCode;
import com.lezhin.assignment.common.jwt.TokenDto;
import com.lezhin.assignment.common.jwt.TokenProvider;
import com.lezhin.assignment.domain.memeber.Member;
import com.lezhin.assignment.domain.memeber.dto.MemberRegReqDto;
import com.lezhin.assignment.domain.memeber.dto.MemberRegResDto;
import com.lezhin.assignment.domain.memeber.dto.MemberReqDto;
import com.lezhin.assignment.domain.memeber.dto.MemberResDto;
import com.lezhin.assignment.domain.memeber.repository.MemberRepository;
import com.lezhin.assignment.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberRegResDto signup(MemberRegReqDto memberDto) {

        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new BusinessException(ErrorCode.EXIST_EMAIL);
        }
        Member member = memberRepository.save(memberDto.toEntity(passwordEncoder));

        return MemberRegResDto.fromEntity(member);
    }

    public MemberResDto signin(MemberReqDto memberReqDto) {

        UsernamePasswordAuthenticationToken authenticationToken = memberReqDto.toAuthentication();

        memberRepository.findByEmail(memberReqDto.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        return MemberResDto.from(tokenDto);
    }
}
