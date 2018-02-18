package fhku.challengegram;

public class Challenge {

    protected int id;
    protected String title;
    protected String description;
    protected String hosts;
    protected String sponsors;
    protected String hasthags;
    protected int lastEdited;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(int lastEdited) {
        this.lastEdited = lastEdited;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getSponsors() {
        return sponsors;
    }

    public void setSponsors(String sponsors) {
        this.sponsors = sponsors;
    }

    public String getHasthags() {
        return hasthags;
    }

    public void setHasthags(String hasthags) {
        this.hasthags = hasthags;
    }


    public void setLastEditedToNow() {
        int timestamp = (int) (System.currentTimeMillis() / 1000L);
        this.lastEdited = timestamp;
    }

}
