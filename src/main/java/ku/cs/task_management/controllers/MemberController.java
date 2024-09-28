package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.UnavailableEmailException;
import ku.cs.task_management.requests.MemberDetailRequest;
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

    @GetMapping("/member")
    public String testGetRequest() {
        return "get /member/";
    }

    @PostMapping("/member/register")
    public ResponseEntity<MemberResponse> memberRegister(@RequestBody MemberDetailRequest request)
            throws UnavailableEmailException {
        if (!memberService.isEmailAvailable(request.getMemberEmail())) {
            throw new UnavailableEmailException(request.getMemberEmail());
        }

        return new ResponseEntity<>(memberService.insertMember(request), HttpStatus.CREATED);
    }
}
