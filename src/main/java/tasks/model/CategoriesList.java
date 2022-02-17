package tasks.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "categories")
public class CategoriesList {

    @Id
    public String id;
    public String userId;
    private List<String> categories = new ArrayList<>();

}
