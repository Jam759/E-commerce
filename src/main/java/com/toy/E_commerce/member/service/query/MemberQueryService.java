package com.toy.E_commerce.member.service.query;

import com.toy.E_commerce.member.entity.Member;

import java.util.UUID;

public interface MemberQueryService {

    Member getByIdentityId(UUID identityId);

}
