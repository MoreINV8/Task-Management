package ku.cs.task_management.controllers;

import ku.cs.task_management.entities.Meeting;
import ku.cs.task_management.exceptions.NotFoundMeetingException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.requests.meeting_requests.MeetingCreateRequest;
import ku.cs.task_management.requests.meeting_requests.MeetingRequest;
import ku.cs.task_management.responses.MeetingResponse;
import ku.cs.task_management.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MeetingController {

    @Autowired private MeetingService meetingService;

    @GetMapping("/meeting")
    public List<Meeting> getAllMeetings() {
        return meetingService.getAllMeetings();
    }

    @GetMapping("{projectId}/meeting")
    public ResponseEntity<List<MeetingResponse>> getMeetings(@PathVariable UUID projectId) throws NotFoundProjectException {
        return new ResponseEntity<>(meetingService.getAllMeetingByProject(projectId), HttpStatus.OK);
    }

    @GetMapping("{projectId}/{meetingId}/detail")
    public ResponseEntity<MeetingResponse> getMeetingDetail(@RequestBody MeetingRequest request) throws NotFoundMeetingException, NotFoundProjectException {
        return new ResponseEntity<>(meetingService.getMeetingDetail(request), HttpStatus.OK);
    }
    @PostMapping("meeting/create")
    public ResponseEntity<MeetingResponse> createMeeting(@RequestBody MeetingCreateRequest request) throws NotFoundProjectException {
        return new ResponseEntity<>(meetingService.createMeeting(request), HttpStatus.CREATED);
    }

    @PutMapping("meeting/edit")
    public ResponseEntity<MeetingResponse> editMeeting(@RequestBody MeetingRequest request) throws NotFoundProjectException, NotFoundMeetingException {
        return new ResponseEntity<>(meetingService.updateMeeting(request), HttpStatus.OK);
    }

    @DeleteMapping("meeting/delete")
    public ResponseEntity<MeetingResponse> deleteMeeting(@RequestBody MeetingRequest request) throws NotFoundProjectException, NotFoundMeetingException {
        return new ResponseEntity<>(meetingService.deleteMeeting(request), HttpStatus.OK);
    }
}
