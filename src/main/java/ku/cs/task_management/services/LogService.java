package ku.cs.task_management.services;

import ku.cs.task_management.entities.Log;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.LogRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.log_request.LogRequest;
import ku.cs.task_management.responses.LogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public LogResponse saveLog(LogRequest request)
            throws NotFoundMemberException, NotFoundProjectException {
        // not found the actor
        if (memberRepository.findMemberByMemberId(request.getLogActor()) == null){
            throw new NotFoundMemberException(request.getLogAction().toString());
        }

        // not found the project
        if (!projectRepository.existsById(request.getLogProject())) {
            throw new NotFoundProjectException(request.getLogProject());
        }

        Log logRequest = new Log();
        logRequest.setAction(request.getLogAction());
        logRequest.setTime(Date.valueOf(LocalDate.now()));
        logRequest.setActor(memberRepository.findMemberByMemberId(request.getLogActor()));
        logRequest.setProject(projectRepository.getReferenceById(request.getLogProject()));

        return getResponse(logRepository.save(logRequest));
    }

    public List<LogResponse> getLogs(UUID projectId)
            throws NotFoundProjectException {
        // not found project
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundProjectException(projectId);
        }

        List<LogResponse> responses = new ArrayList<>();
        for (Log log : logRepository.getLogsByProjectID(projectId)) {
            responses.add(getResponse(log));
        }

        return responses;
    }

    public void deleteLog(UUID projectId)
            throws NotFoundProjectException {
        // in case delete project

        if(!projectRepository.existsById(projectId)) {
            throw new NotFoundProjectException(projectId);
        }

        List<Log> logs = logRepository.getLogsByProjectID(projectId);
        for (Log log : logs) {
            log.setProject(null);
            log.setActor(null);
            logRepository.save(log);

            logRepository.delete(log);
        }
    }

    private LogResponse getResponse(Log log) {
        LogResponse response = new LogResponse();
        response.setLogAction(log.getAction());
        response.setLogTime(log.getTime());
        response.setLogActor(log.getActor().getMemberId());
        response.setLogProject(log.getProject().getProjectId());

        return response;
    }
}
