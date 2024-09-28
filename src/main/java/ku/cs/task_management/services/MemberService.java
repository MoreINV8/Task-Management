package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.MemberDetail;
import ku.cs.task_management.repositories.MemberDetailRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.requests.MemberDetailRequest;
import ku.cs.task_management.responses.MemberResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberDetailRepository memberDetailRepository;

    public MemberResponse insertMember(MemberDetailRequest request) {
        Member requestMember = new Member();
        requestMember.setDetail(modelMapper.map(request, MemberDetail.class));

        String encodedPassword = passwordEncoder.encode(request.getMemberPassword());
        requestMember.getDetail().setMemberPassword(encodedPassword);

        Member createdMember = memberRepository.save(requestMember);

        return modelMapper.map(createdMember, MemberResponse.class);
    }

    public boolean isEmailAvailable(String email) {
        return memberDetailRepository.findMemberDetailByMemberEmail(email) == null;
    }
}
