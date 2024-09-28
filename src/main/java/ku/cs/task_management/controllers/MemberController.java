package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.UnavailableEmailException;
import ku.cs.task_management.exceptions.WrongPasswordException;
import ku.cs.task_management.requests.MemberDetailRequest;
import ku.cs.task_management.requests.MemberLoginRequest;
import ku.cs.task_management.responses.MemberResponse;
import ku.cs.task_management.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/member/register")
    public ResponseEntity<MemberResponse> memberRegister(@RequestBody MemberDetailRequest request)
            throws UnavailableEmailException {
        // check if email was used
        if (!memberService.isEmailAvailable(request.getMemberEmail())) {
            throw new UnavailableEmailException(request.getMemberEmail());
        }

        return new ResponseEntity<>(memberService.insertMember(request), HttpStatus.CREATED);
    }

    @PostMapping("/member/login")
    public ResponseEntity<MemberResponse> testGetRequest(@RequestBody MemberLoginRequest request)
            throws NotFoundMemberException, WrongPasswordException {
        // check user was existed
        if (memberService.isEmailAvailable(request.getMemberEmail())) {
            throw new NotFoundMemberException(request.getMemberEmail());
        }

        // check password
        if (!memberService.isMatchPassword(request.getMemberEmail(), request.getMemberPassword())) {
            throw new WrongPasswordException();
        }

        return new ResponseEntity<>(memberService.getLoginMember(request), HttpStatus.ACCEPTED);
    }
}
