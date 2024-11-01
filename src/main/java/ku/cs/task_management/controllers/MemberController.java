package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.*;
import ku.cs.task_management.requests.member_requests.MemberEditPasswordRequest;
import ku.cs.task_management.requests.member_requests.edit_profile.MemberEditProfileRequest;
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

    @PutMapping("/member/edit-profile")
    public ResponseEntity<MemberResponse> editMemberProfile(@RequestBody MemberEditProfileRequest request)
            throws NotFoundMemberException, UnavailableEmailException {
        return new ResponseEntity<>(memberService.updateMemberDetail(request), HttpStatus.ACCEPTED);
    }

    @PutMapping("/member/edit-password")
    public ResponseEntity<MemberResponse> editMemberPassword(@RequestBody MemberEditPasswordRequest request)
            throws NotFoundMemberException, WrongPasswordException, SamePasswordException {
        return new ResponseEntity<>(memberService.updateMemberPassword(request), HttpStatus.ACCEPTED);
    }
}
