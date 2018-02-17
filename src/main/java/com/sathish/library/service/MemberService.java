package com.sathish.library.service;

import com.sathish.library.domain.Member;
import com.sathish.library.dao.jpa.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    CounterService counterService;

    public MemberService() {
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMember(long id) {
        return memberRepository.findOne(id);
    }

    public void updateMember(Member member) {
    	memberRepository.save(member);
    }

    public void deleteMember(Long id) {
    	memberRepository.delete(id);
    }

    public Page<Member> getAllMembers(Integer page, Integer size) {
        Page pageOfMembers = memberRepository.findAll(new PageRequest(page, size));
        if (size > 50) {
            counterService.increment("sathish.memberService.getAll.largePayload");
        }
        return pageOfMembers;
    }
}
