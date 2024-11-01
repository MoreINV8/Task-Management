package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.WrongPasswordException;
import ku.cs.task_management.requests.member_requests.MemberLoginRequest;
import ku.cs.task_management.responses.MemberResponse;
import ku.cs.task_management.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody MemberLoginRequest request)
            throws NotFoundMemberException, WrongPasswordException {
        return ResponseEntity.ok(memberService.getLoginMember(request));
    }
}
