package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.MemberDetail;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.*;
import ku.cs.task_management.repositories.MemberDetailRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.member_requests.MemberEditPasswordRequest;
import ku.cs.task_management.requests.member_requests.MemberSignupRequest;
import ku.cs.task_management.requests.member_requests.MemberLoginRequest;
import ku.cs.task_management.requests.member_requests.edit_profile.MemberEditProfileRequest;
import ku.cs.task_management.responses.MemberResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberDetailRepository memberDetailRepository;

    @Autowired
    private JWTService jwtService;

    public boolean isMatchPassword(Member member, String password) {
        return passwordEncoder.matches(password, member.getDetail().getMemberPassword());
    }

    public MemberResponse getLoginMember(MemberLoginRequest request)
            throws NotFoundMemberException, WrongPasswordException {
        // check user was existed
        Member member = memberRepository.findMemberByEmail(request.getMemberEmail());
        if (member == null) {
            throw new NotFoundMemberException(request.getMemberEmail());
        }

        // check password
        if (!isMatchPassword(member, request.getMemberPassword())) {
            throw new WrongPasswordException();
        }

        MemberResponse response = modelMapper.map(member, MemberResponse.class);
        response.setToken(jwtService.generateToken(member));

        return response;
    }

    public MemberResponse registerMember(MemberSignupRequest request)
            throws UnavailableEmailException {
        // check if email was used
        if (memberRepository.findMemberByEmail(request.getMemberEmail()) != null) {
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
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException(request.getMemberId()));

        // retrieve member account
        String oldEmail = null;

        // delete old table in case change email
        if (!member.getDetail().getMemberEmail().equals(request.getDetail().getMemberEmail())) {

            // check to not have copy email
            if (memberRepository.findMemberByEmail(request.getDetail().getMemberEmail()) != null) {
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

        // check if member account were existed
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException(request.getMemberId()));

        // check if password is correct
        if (!isMatchPassword(member, request.getCurrentPassword())) {
            throw new WrongPasswordException();
        }

        // check if current password and new password are the same
        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new SamePasswordException();
        }

        // encoded new password
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        // retrieve member data and replace password
        member.getDetail().setMemberPassword(encodedPassword);

        // save updated password
        return modelMapper.map(memberRepository.save(member), MemberResponse.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findMemberByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("not found user");
        }

        List<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(member.getMemberId().toString()));

        return new User(member.getDetail().getMemberEmail(), member.getDetail().getMemberPassword(), auth);
    }
}
