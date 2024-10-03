package com.lezhin.assignment.domain.memeber.api;

import com.lezhin.assignment.common.dto.DataResponse;
import com.lezhin.assignment.domain.memeber.dto.MemberRegReqDto;
import com.lezhin.assignment.domain.memeber.dto.MemberRegResDto;
import com.lezhin.assignment.domain.memeber.dto.MemberReqDto;
import com.lezhin.assignment.domain.memeber.dto.MemberResDto;
import com.lezhin.assignment.domain.memeber.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<MemberRegResDto> signup(@Valid @RequestBody MemberRegReqDto memberDto) {
        return DataResponse.create(authService.signup(memberDto));
    }

    @PostMapping("/signin")
    @ResponseStatus(value = HttpStatus.OK)
    public DataResponse<MemberResDto> signin(@Valid @RequestBody MemberReqDto memberLoginReqDto) {
        return DataResponse.create(authService.signin(memberLoginReqDto));
    }

}
