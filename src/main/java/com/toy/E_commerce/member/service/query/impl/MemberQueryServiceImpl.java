package com.toy.E_commerce.member.service.query.impl;

import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.member.exception.MemberException;
import com.toy.E_commerce.member.exception.code.MemberErrorCode;
import com.toy.E_commerce.member.repository.MemberRepository;
import com.toy.E_commerce.member.service.query.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository repository;

    @Override
    public Member getByIdentityId(UUID identityId) {
        return repository.findByIdentityIdWithLoginInfo(identityId)
                .orElseThrow( () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND_ERROR));
    }
}
