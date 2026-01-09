package com.toy.E_commerce.member.service.query.impl;

import com.toy.E_commerce.member.entity.LocalLoginInfo;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.member.exception.MemberException;
import com.toy.E_commerce.member.exception.code.MemberErrorCode;
import com.toy.E_commerce.member.repository.LocalLoginInfoRepository;
import com.toy.E_commerce.member.repository.MemberRepository;
import com.toy.E_commerce.member.service.query.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository repository;
    private final LocalLoginInfoRepository loginInfoRepository;

    @Override
    public Member getByIdentityId(UUID identityId) {
        return repository.findByIdentityIdWithLoginInfo(identityId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND_ERROR));
    }

    @Override
    public Member getByLoginIdAndPassword(String loginId, String password) {
        return loginInfoRepository.findByLoginIdAndPasswordWithMember(loginId, password)
                .map(LocalLoginInfo::getMember)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND_ERROR));
    }

    @Override
    public void isExistsByEmail(String email) {
        if (repository.existsByEmailAndDeletedAtIsNull(email)) {
            throw new MemberException(MemberErrorCode.MEMBER_EMAIL_DUPLICATED_ERROR);
        }
    }
}
