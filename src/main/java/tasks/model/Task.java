package tasks.model;

import java.util.List;
import lombok.Data;
import tasks.model.SubTask;

@Data
public class Task {

    public String title;
    public String description;
    public Boolean completed;
    public List<String> categories;
    public List<SubTask> subTasks;

}
