package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.UnavailableEmailException;
import ku.cs.task_management.requests.member_requests.MemberSignupRequest;
import ku.cs.task_management.responses.MemberResponse;
import ku.cs.task_management.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberResponse> memberRegister(@RequestBody MemberSignupRequest request)
            throws UnavailableEmailException {
        return ResponseEntity.ok(memberService.registerMember(request));
    }
}
