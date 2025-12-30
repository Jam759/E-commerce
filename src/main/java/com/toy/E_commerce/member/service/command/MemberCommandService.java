package com.toy.E_commerce.member.service.command;

import com.toy.E_commerce.member.entity.LocalLoginInfo;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.member.repository.LocalLoginInfoRepository;
import com.toy.E_commerce.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository repository;
    private final LocalLoginInfoRepository loginInfoRepository;

    public Member save(Member newMember) {
        return repository.save(newMember);
    }

    public LocalLoginInfo loginInfoSave(LocalLoginInfo loginInfo) {
        return loginInfoRepository.save(loginInfo);
    }
}
