package com.toy.E_commerce.security.service;

import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.member.exception.MemberException;
import com.toy.E_commerce.member.service.query.MemberQueryService;
import com.toy.E_commerce.security.entity.UserDetailsImpl;
import com.toy.E_commerce.auth.exception.JwtUtilErrorCode;
import com.toy.E_commerce.auth.exception.JwtUtilException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberQueryService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UUID identityId = UUID.fromString(username);
        try {
            Member member = service.getByIdentityId(identityId);
            return new UserDetailsImpl(member);
        } catch (MemberException e) {
            //jwt 서명부분은 맞는데 member를 못찾을 경우 이 예외를 던지게 함
            throw new JwtUtilException(JwtUtilErrorCode.TOKEN_ILLEGAL_ARGUMENT);
        }
    }

}
