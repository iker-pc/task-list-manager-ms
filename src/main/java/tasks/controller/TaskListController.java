package tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;

import tasks.model.TaskList;
import tasks.model.User;
import tasks.repository.TaskListRepository;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/task-list")
public class TaskListController {

  @Autowired
  private TaskListRepository taskListRepository;

  @GetMapping
  public List<TaskList> getTaskListByIdMonthAndYear(@SessionAttribute User user,
                                              @RequestParam(name = "month", required = false) Integer month,
                                              @RequestParam(name = "year", required = false) Integer year) {
      if (month != null && year != null){
        LocalDate date = LocalDate.of(year, month, 1);
        LocalDate startMonthDate = date.withDayOfMonth(1);
        LocalDate endMonthDate = date.withDayOfMonth(date.lengthOfMonth());

        ZoneId defaultZoneId = ZoneId.systemDefault();

        return taskListRepository.findByUserIdAndDateRange(user.getSub(), Date.from(startMonthDate.atStartOfDay(defaultZoneId).toInstant()), Date.from(endMonthDate.atStartOfDay(defaultZoneId).toInstant()));
      }
      return taskListRepository.findByUserIdAndTimestamp(user.getSub(), new Date());
  }
  
  @PostMapping
  public TaskList addTaskList(@SessionAttribute User user, @Valid @RequestBody TaskList taskList) {
	  taskList.setUserId(user.getSub());
      taskListRepository.insert(taskList);
      return taskList;
  }

  @PutMapping
  public ResponseEntity<TaskList> updateTaskList(@SessionAttribute User user, @Valid @RequestBody TaskList taskList) {
      List<TaskList> tasksListInTaskListDateRange = taskListRepository.findByUserIdAndDateRange(user.getSub(), taskList.startDate, taskList.endDate);
      TaskList selectedTaskList = tasksListInTaskListDateRange.stream()
                                  .filter(taskListInList -> taskListInList.id.equals(taskList.id))
                                  .findFirst()
                                  .orElse(null);
      if(tasksListInTaskListDateRange.size() == 1 && selectedTaskList != null){
    	  taskList.setUserId(user.getSub());
          taskListRepository.save(taskList);
          return new ResponseEntity<TaskList>(taskList, HttpStatus.OK);
      }
      return new ResponseEntity<TaskList>(selectedTaskList, HttpStatus.CONFLICT);
  }
  
  @DeleteMapping
  public void removeTaskList(@Valid @RequestBody TaskList taskList) {
      taskListRepository.delete(taskList);
  }

  @RequestMapping(value = "/previous", method = RequestMethod.GET)
  public List<TaskList> getPreviousTaskListByIdAndTimestamp(@SessionAttribute User user, @RequestParam Long timestamp) {
      Date selectedDate = new Date(timestamp);
      return taskListRepository.findClosestPreviousTaskListByUserIdAndTimestamp(user.getSub(), selectedDate, new Sort(Sort.Direction.DESC, "startDate"));
  }

  
}
