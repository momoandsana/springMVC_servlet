package hello.servlet.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되지 않음, 실무에는 ConcurrentHashMap,AtomicLong 사용 고려
 */
public class MemberRepository {
    private static Map<Long,Member> store=new HashMap<>();
    private static long sequence=0L;

    private static final MemberRepository instance=new MemberRepository();

    public static MemberRepository getInstance()
    {
        // 싱글톤
        return instance;
    }

    private MemberRepository(){
        // 기본 생성자
    }

    public Member save(Member member)
    {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    public Member findById(Long id)
    {
        return store.get(id);
    }

    public List<Member> findAll()
    {
        return new ArrayList<>(store.values());
        // store 의 value 들을 꺼내서 새로운 리스트로 만든다
        // 직접 store 의 내용물을 건드리고 싶지 않기 때문에
    }

    public void clearStore()
    {
        store.clear();
    }
}
