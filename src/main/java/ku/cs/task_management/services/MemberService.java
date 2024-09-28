package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.MemberDetail;
import ku.cs.task_management.exceptions.UnavailableEmailException;
import ku.cs.task_management.repositories.MemberDetailRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.requests.member_requests.MemberSignupRequest;
import ku.cs.task_management.requests.member_requests.MemberLoginRequest;
import ku.cs.task_management.requests.member_requests.edit_profile.MemberEditProfileRequest;
import ku.cs.task_management.responses.MemberResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public boolean isAccountExist(UUID memberId) {
        return memberRepository.findMemberByMemberId(memberId) != null;
    }

    public boolean isAccountExist(String memberEmail) {
        return memberRepository.findMemberByEmail(memberEmail) != null;
    }

    public boolean isMatchPassword(String email, String password) {
        MemberDetail memberDetail = memberDetailRepository.findMemberDetailByMemberEmail(email);

        return passwordEncoder.matches(password, memberDetail.getMemberPassword());
    }

    public MemberResponse getLoginMember(MemberLoginRequest request) {
        return modelMapper.map(memberRepository.findMemberByEmail(request.getMemberEmail()), MemberResponse.class);
    }

    public MemberResponse insertMember(MemberSignupRequest request) {
        // mapping request with entity class
        Member requestMember = new Member();
        requestMember.setDetail(modelMapper.map(request, MemberDetail.class));

        // encode password
        String encodedPassword = passwordEncoder.encode(request.getMemberPassword());
        requestMember.getDetail().setMemberPassword(encodedPassword);

        // insert data to database
        Member createdMember = memberRepository.save(requestMember);

        return modelMapper.map(createdMember, MemberResponse.class);
    }

    public MemberResponse updateMember(MemberEditProfileRequest request)
            throws UnavailableEmailException {
        // retrieve member account
        Member member = memberRepository.findMemberByMemberId(request.getMemberId());
        String oldEmail = null;

        // delete old table in case change email
        if (!member.getDetail().getMemberEmail().equals(request.getDetail().getMemberEmail())) {

            // check to not have copy email
            if (isAccountExist(request.getDetail().getMemberEmail())) {
                throw new UnavailableEmailException(request.getDetail().getMemberEmail());
            }

            oldEmail = member.getDetail().getMemberEmail();
        }

        // replace information
        member.updateMemberDetail(modelMapper.map(request.getDetail(), MemberDetail.class));

        // update to database
        MemberResponse response = modelMapper.map(memberRepository.save(member), MemberResponse.class);

        // delete old data
        if (oldEmail != null) {
            memberDetailRepository.deleteById(oldEmail);
        }

        return response;
    }
}
