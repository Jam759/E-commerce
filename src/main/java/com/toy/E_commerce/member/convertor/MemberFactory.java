package com.toy.E_commerce.member.convertor;

import com.toy.E_commerce.auth.dto.api.request.LocalSignUpRequest;
import com.toy.E_commerce.global.util.UuidUtil;
import com.toy.E_commerce.member.entity.LocalLoginInfo;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.member.entity.enums.MemberRole;
import com.toy.E_commerce.member.entity.enums.OauthType;

public class MemberFactory {

    public static Member toMember(LocalSignUpRequest request) {

        return Member.builder()
                .email(request.getEmail())
                .oauthType(OauthType.LOCAL)
                .role(MemberRole.ROLE_USER)
                .oauthId("local"+request.getLoginId())
                .identityId(UuidUtil.getUUIDv7())
                .profileImgPath("")
                .build();

    }

    public static LocalLoginInfo toLocalLoginInfo(LocalSignUpRequest request, Member member) {
        return LocalLoginInfo.builder()
                .member(member)
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .build();
    }
}
