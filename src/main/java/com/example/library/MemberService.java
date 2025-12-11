package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public long countMembers() {
        return memberRepository.count();
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }
}
