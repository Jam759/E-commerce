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

        TokenBundle jwtBundle = jwtUtil.createAllToken(member);

        RefreshToken savedRefreshToken
                = AuthFactory.toRefreshToken(member, jwtBundle);
        refreshTokenCommandService.save(savedRefreshToken);

        return AuthAssembler.toJwtResponse(jwtBundle);
    }

    @Override
    public JwtResponse reissue(String refreshToken) {
        UUID jti = jwtUtil.getJtiFromRefreshToken(refreshToken);
        RefreshToken savedToken
                = refreshTokenQueryService.getById(jti);


        if (savedToken.remainingTime() <= 3) {
            Member member = savedToken.getMember();
            TokenBundle jwtBundle = jwtUtil.createAllToken(member);
            RefreshToken newRefreshToken
                    = AuthFactory.toRefreshToken(member, jwtBundle);
            refreshTokenCommandService.save(newRefreshToken);
            return AuthAssembler.toJwtResponse(jwtBundle);
        }

        TokenInfo accessToken = jwtUtil.createAccessToken(savedToken.getMember());
        return AuthAssembler.toJwtResponse(accessToken, savedToken);

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
        refreshTokenCommandService.delete(refreshTokenInfo.getJti());
        accessTokenBlackListCommandService.saveCache(blackListCache);
    }
}
