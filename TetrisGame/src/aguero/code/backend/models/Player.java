package aguero.code.backend.models;

public class Player {
    private int id;
    private String username;
    private String score;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Player(int id, String username, String score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }
}
