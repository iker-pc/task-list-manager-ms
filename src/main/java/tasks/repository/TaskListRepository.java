package tasks.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import tasks.model.TaskList;

import java.util.List;
import java.util.Date;

public interface TaskListRepository extends MongoRepository<TaskList, String> {

  List<TaskList> findByUserId(String userId);


  /**
    By using this query we search the task list which:
    1 - Task list start date is higher than first day of the current month and task
      list end date is lower than the last day of the currrent month.
    2 - Task list start date is lower than the first day of the current month and
      list end date is within the current month.
    3 - Task list start date is within the current month, but task list end date
      is higher then the last day of the current month
    4 - Task list start date is lower than the first day of the current month and
      task list end date is higher than the current month.
  **/
  @Query("{ $and : [{ 'userId' : ?0 }," +
            "{ '$or': [" +
                "{ '$and': [{ 'startDate' : { $gte : ?1 }}, { 'endDate' : { $lte : ?2 }} ] }," +
                "{ '$and': [{ 'startDate' : { $lte : ?1 }}, { 'endDate' : { $gte : ?1 }}, { 'endDate' : { $lte : ?2 }} ] }," +
                "{ '$and': [{ 'startDate' : { $gte : ?1 }}, { 'startDate' : { $lte : ?2 }}, { 'endDate' : { $gte : ?2 }} ] }," +
                "{ '$and': [{ 'startDate' : { $lte : ?1 }}, { 'endDate' : { $gte : ?2 }} ] }" +
             "] }" +
           "] }")
  List<TaskList> findByUserIdAndDateRange(String userID, Date fromDate, Date endDate);

  /**
    By using this query we search the task list which:
    1 - Task list start date is lower than selected date and task
      list end date is higher than selected date
  **/
  @Query("{ $and : [{ 'userId' : ?0 }, { 'startDate' : { $lte : ?1 }}, { 'endDate' : { $gte : ?1 }} ] }")
  List<TaskList> findByUserIdAndTimestamp(String userID, Date selectedDate);

  /**
    By using this query we search for the task list with the closest end date from the given date
  **/
  @Query("{ $and : [{ 'userId' : ?0 }, { 'endDate' : { $lt : ?1 }} ] }")
  List<TaskList> findClosestPreviousTaskListByUserIdAndTimestamp(String userID, Date selecteDate, Sort sort);


  /**
    By using this query we search for the task list with the closest start date from the given date
  **/
  @Query("{ $and : [{ 'userId' : ?0 }, { 'startDate' : { $gt : ?1 }} ] }" )
  TaskList findClosestNextTaskListByUserIdAndTimestamp(String userID, Date selectedDate, Sort sort);


}
