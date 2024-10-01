package ku.cs.task_management.services;

import ku.cs.task_management.entities.Meeting;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.NotFoundMeetingException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.MeetingRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.meeting_requests.MeetingRequest;
import ku.cs.task_management.responses.MeetingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingService {
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    public List<MeetingResponse> getAllMeetingByProject(UUID projectId) throws NotFoundProjectException {
        List<MeetingResponse> responses = new ArrayList<>();

        projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        for (Meeting meeting : meetingRepository.findAllByMeetingProjectProjectId(projectId)) {
            responses.add(modelMapper.map(meeting, MeetingResponse.class));
        }
        return responses;
    }

    public MeetingResponse createMeeting(MeetingRequest request) throws NotFoundProjectException {
        Meeting meeting = new Meeting();

        meeting.setMeetingTopic(request.getMeetingTopic());
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setMeetingLocation(request.getMeetingLocation());
        meeting.setMeetingProject(projectRepository.findById(request.getMeetingProject())
                .orElseThrow(() -> new NotFoundProjectException(request.getMeetingProject())));

        Meeting createdMeeting = meetingRepository.save(meeting);
        return modelMapper.map(createdMeeting, MeetingResponse.class);
    }

    public MeetingResponse getMeetingDetail (MeetingRequest request) throws NotFoundMeetingException, NotFoundProjectException {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new NotFoundMeetingException(request.getMeetingId()));

        projectRepository.findById(request.getMeetingProject())
                .orElseThrow(() -> new NotFoundProjectException(request.getMeetingProject()));

        return modelMapper.map(meeting, MeetingResponse.class);
    }

    public MeetingResponse updateMeeting(MeetingRequest request) throws NotFoundProjectException, NotFoundMeetingException {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new NotFoundMeetingException(request.getMeetingId()));
        projectRepository.findById(request.getMeetingProject())
                .orElseThrow(() -> new NotFoundProjectException(request.getMeetingProject()));

        meeting.setMeetingTopic(request.getMeetingTopic());
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setMeetingLocation(request.getMeetingLocation());

        Meeting updatedMeeting = meetingRepository.save(meeting);
        return modelMapper.map(updatedMeeting, MeetingResponse.class);
    }

    // TODO: Do deletion need to return response body back?
    public MeetingResponse deleteMeeting(MeetingRequest request) throws NotFoundMeetingException, NotFoundProjectException {
        Project project = projectRepository.findById(request.getMeetingProject())
                .orElseThrow(() -> new NotFoundProjectException(request.getMeetingProject()));

        Meeting meeting = project.getProjectMeetings().stream()
                .filter(m -> m.getMeetingId().equals(request.getMeetingId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundMeetingException(request.getMeetingId()));

        project.getProjectMeetings().remove(meeting);
        projectRepository.save(project);

        meetingRepository.delete(meeting);

        return modelMapper.map(meeting, MeetingResponse.class);
    }
}
