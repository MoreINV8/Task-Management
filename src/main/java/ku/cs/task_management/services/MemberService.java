package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.MemberDetail;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.SamePasswordException;
import ku.cs.task_management.exceptions.UnavailableEmailException;
import ku.cs.task_management.exceptions.WrongPasswordException;
import ku.cs.task_management.repositories.MemberDetailRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.requests.member_requests.MemberEditPasswordRequest;
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

    public String getMemberEmailById(UUID id) {
        return memberRepository.findMemberEmailByMemberId(id);
    }

    public MemberResponse getLoginMember(MemberLoginRequest request)
            throws NotFoundMemberException, WrongPasswordException {
        // check user was existed
        if (!isAccountExist(request.getMemberEmail())) {
            throw new NotFoundMemberException(request.getMemberEmail());
        }

        // check password
        if (!isMatchPassword(request.getMemberEmail(), request.getMemberPassword())) {
            throw new WrongPasswordException();
        }

        return modelMapper.map(memberRepository.findMemberByEmail(request.getMemberEmail()), MemberResponse.class);
    }

    public MemberResponse insertMember(MemberSignupRequest request)
            throws UnavailableEmailException {
        // check if email was used
        if (isAccountExist(request.getMemberEmail())) {
            throw new UnavailableEmailException(request.getMemberEmail());
        }

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

    public MemberResponse updateMemberDetail(MemberEditProfileRequest request)
            throws UnavailableEmailException, NotFoundMemberException {
        // check if member account excise
        if (!isAccountExist(request.getMemberId())) {
            throw new NotFoundMemberException(request.getDetail().getMemberEmail());
        }

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

    public MemberResponse updateMemberPassword(MemberEditPasswordRequest request)
            throws SamePasswordException, NotFoundMemberException, WrongPasswordException {
        // retrieve email
        String email = getMemberEmailById(request.getMemberId());

        // check if member account were existed
        if (!isAccountExist(request.getMemberId())) {
            throw new NotFoundMemberException(email);
        }

        // check if password is correct
        if (!isMatchPassword(email, request.getCurrentPassword())) {
            throw new WrongPasswordException();
        }

        // check if current password and new password are the same
        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new SamePasswordException();
        }

        // encoded new password
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        // retrieve member data and replace password
        Member member = memberRepository.findMemberByMemberId(request.getMemberId());
        member.getDetail().setMemberPassword(encodedPassword);

        // save updated password
        return modelMapper.map(memberRepository.save(member), MemberResponse.class);
    }
}
