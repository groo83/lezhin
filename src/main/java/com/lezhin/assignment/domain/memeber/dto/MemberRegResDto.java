package com.lezhin.assignment.domain.memeber.dto;


import com.lezhin.assignment.common.dto.BaseResDto;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.memeber.Member;
import com.lezhin.assignment.domain.memeber.enums.AdultState;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegResDto {

    private String email;

    private String name;

    private AdultState adultState;

    public static MemberRegResDto fromEntity (Member member) {
        return MemberRegResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .adultState(member.getAdultState())
                .build();
    }
}
