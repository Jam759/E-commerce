package com.toy.E_commerce.auth.facade.impl;

import com.toy.E_commerce.auth.convertor.AuthAssembler;
import com.toy.E_commerce.auth.convertor.AuthFactory;
import com.toy.E_commerce.auth.dto.api.request.LocalLoginRequest;
import com.toy.E_commerce.auth.dto.api.request.LocalSignUpRequest;
import com.toy.E_commerce.auth.dto.api.response.JwtResponse;
import com.toy.E_commerce.auth.dto.application.TokenBundle;
import com.toy.E_commerce.auth.dto.application.TokenInfo;
import com.toy.E_commerce.auth.dto.application.enums.TokenType;
import com.toy.E_commerce.auth.dto.cache.AccessTokenBlackListCache;
import com.toy.E_commerce.auth.entity.RefreshToken;
import com.toy.E_commerce.auth.exception.JwtUtilErrorCode;
import com.toy.E_commerce.auth.exception.JwtUtilException;
import com.toy.E_commerce.auth.facade.LocalAuthFacade;
import com.toy.E_commerce.auth.service.command.AccessTokenBlackListCommandService;
import com.toy.E_commerce.auth.service.command.RefreshTokenCommandService;
import com.toy.E_commerce.auth.service.query.RefreshTokenQueryService;
import com.toy.E_commerce.auth.util.JwtUtil;
import com.toy.E_commerce.member.convertor.MemberFactory;
import com.toy.E_commerce.member.entity.LocalLoginInfo;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.member.service.command.MemberCommandService;
import com.toy.E_commerce.member.service.query.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalAuthFacadeImpl implements LocalAuthFacade {

    private final JwtUtil jwtUtil;
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final RefreshTokenCommandService refreshTokenCommandService;
    private final RefreshTokenQueryService refreshTokenQueryService;
    private final AccessTokenBlackListCommandService accessTokenBlackListCommandService;

    @Override
    @Transactional(readOnly = false)
    public JwtResponse login(LocalLoginRequest request) {
        String loginId = request.getLoginId();
        String password = request.getPassword();

        Member member
                = memberQueryService.getByLoginIdAndPassword(loginId, password);

        //잔여 refreshToken 있으면 삭제 없으면 무시
        refreshTokenQueryService.findByMember(member)
                .ifPresent(refreshTokenCommandService::delete);

        TokenBundle jwtBundle = jwtUtil.createAllToken(member);
        RefreshToken savedRefreshToken
                = AuthFactory.toRefreshToken(member, jwtBundle);
        refreshTokenCommandService.save(savedRefreshToken);

        return AuthAssembler.toJwtResponse(jwtBundle);
    }

    @Override
    public JwtResponse reissue(String refreshToken) {
        UUID jti = jwtUtil.getJtiFromRefreshToken(refreshToken);
        UUID userIdentity = jwtUtil.getSubjectFromRefreshToken(refreshToken);

        Member requester = memberQueryService.getByIdentityId(userIdentity);
        RefreshToken savedToken = refreshTokenQueryService.getById(jti);

        if (!savedToken.isOwner(requester) || savedToken.remainingTime() <= 0) {
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_EXPIRED);
        }

        //기존 삭제
        refreshTokenCommandService.delete(savedToken);

        // 새로 발급
        TokenBundle jwtBundle = jwtUtil.createAllToken(requester);
        RefreshToken savedRefreshToken
                = AuthFactory.toRefreshToken(requester, jwtBundle);
        refreshTokenCommandService.save(savedRefreshToken);

        return AuthAssembler.toJwtResponse(jwtBundle);
    }

    @Override
    @Transactional(readOnly = false)
    public JwtResponse signUp(LocalSignUpRequest request) {

        memberQueryService.isExistsByEmail(request.getEmail());
        Member newMember
                = MemberFactory.toMember(request);
        Member savedMember = memberCommandService.save(newMember);

        LocalLoginInfo loginInfo
                = MemberFactory.toLocalLoginInfo(request, savedMember);
        LocalLoginInfo savedInfo
                = memberCommandService.loginInfoSave(loginInfo);

        TokenBundle jwtBundle = jwtUtil.createAllToken(savedMember);
        RefreshToken savedRefreshToken
                = AuthFactory.toRefreshToken(savedMember, jwtBundle);
        refreshTokenCommandService.save(savedRefreshToken);

        return AuthAssembler.toJwtResponse(jwtBundle);

    }

    @Override
    @Transactional(readOnly = false)
    public void logOut(Member member, String headerToken, String refreshToken) {
        String rawToken = jwtUtil.stripBearerPrefix(headerToken);
        TokenInfo accessTokeInfo
                = jwtUtil.getTokenInfo(rawToken, TokenType.ACCESS);
        AccessTokenBlackListCache blackListCache
                = AuthAssembler.toAccessTokenBlackListCache(member, accessTokeInfo);

        TokenInfo refreshTokenInfo
                = jwtUtil.getTokenInfo(refreshToken, TokenType.REFRESH);
        RefreshToken savedRefreshToken
                = refreshTokenQueryService.getById(refreshTokenInfo.getJti());

        refreshTokenCommandService.delete(savedRefreshToken);
        accessTokenBlackListCommandService.saveCache(blackListCache);
    }
}
