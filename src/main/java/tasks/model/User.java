package tasks.model;

import lombok.Data;

@Data
public class User {

    private String email;
    private boolean email_verified;
    private String family_name;
    private String given_name;
    private String locale;
    private String name;
    private String nickname;
    private String picture;
    private String sub;
    private String updated_at;
    
}
