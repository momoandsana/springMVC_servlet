package hello.servlet.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    MemberRepository memberRepository=MemberRepository.getInstance();
    // 나중에 스프링을 쓰면 자동으로 싱글톤 보장해줌

    @AfterEach // 테스트가 끝나면 초기화
    void afterEach(){
        memberRepository.clearStore();
    }
    /*
    테스트가 끝날 때마다 AfterEach 의 코드가 실행된다
    여기서 클리어를 하지 않으면 save 테스트가 먼저 실행될지 findAll 테스트가 먼저 실행될지 모르기 때문에
    데이터가 꼬여서 에러가 생길 수도 있다
     */

    @Test
    void save()
    {
        //given
        Member member = new Member("hello", 20);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
                //assertj
    }

    @Test
    void findAll()
    {
        //given
        Member member1=new Member("member1",20);
        Member member2=new Member("member2",30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        //junit Assertions , option enter 로 static import 만들기
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1,member2);


    }
}