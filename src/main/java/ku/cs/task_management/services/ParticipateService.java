package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Participation;
import ku.cs.task_management.entities.Task;
import ku.cs.task_management.entities.keys.ParticipationKey;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundParticipationException;
import ku.cs.task_management.exceptions.NotFoundTaskException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ParticipationRepository;
import ku.cs.task_management.repositories.TaskRepository;
import ku.cs.task_management.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParticipateService {
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private MemberRepository memberRepository;

    public List<Participation> getParticipation() {
        return participationRepository.findAll();
    }

    public List<Participation> getAllParticipationByTaskId(UUID taskId) throws NotFoundTaskException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundTaskException(taskId));
        List<Participation> responses = new ArrayList<>();
        for (Participation participation : participationRepository.findAllByTaskTaskId(taskId)) {
            Participation response = new Participation();
            response.setMember(participation.getMember());
            response.setTask(task);
            responses.add(response);
        }
        return responses;
    }

    public SuccessResponse addParticipation(UUID taskId, List<Member> members) throws NotFoundMemberException, NotFoundTaskException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException(taskId));
        for (Member member : members) {
            memberRepository.findById(member.getMemberId())
                    .orElseThrow(() -> new NotFoundMemberException(member.getMemberId()));

            Participation participation = new Participation();
            participation.setId(new ParticipationKey(member.getMemberId(), task.getTaskId()));
            participation.setTask(task);
            participation.setMember(member);

            participationRepository.save(participation);
        }
        return new SuccessResponse("successfully added to task " + task.getTaskId(), HttpStatus.OK);
    }

    public SuccessResponse removeParticipation(UUID taskId, List<UUID> participants) throws NotFoundTaskException, NotFoundMemberException, NotFoundParticipationException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException(taskId));

        for (UUID participantId : participants) {
            Member member = memberRepository.findById(participantId)
                    .orElseThrow(() -> new NotFoundMemberException(participantId));
            ParticipationKey participationKey = new ParticipationKey(task.getTaskId(), member.getMemberId());
            System.out.println(participationKey);
            Participation participation = participationRepository
                    .findById(participationKey)
                    .orElseThrow(() -> new NotFoundParticipationException(participantId, taskId));
            participationRepository.delete(participation);

        }
        return new SuccessResponse( "successfully removed from task "  + task.getTaskId(), HttpStatus.OK);
    }

}
