package tasks.model;

import java.util.List;
import java.util.Date;
import org.springframework.data.annotation.Id;
import tasks.model.Task;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks")
public class TaskList {

    @Id
    public String id;
    private String userId;
    public Date startDate;
    public Date endDate;
    private List<Task> tasks;

}
