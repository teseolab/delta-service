package no.ntnu.mikaelr.model.dto.outgoing;

import no.ntnu.mikaelr.model.entities.LogRecord;
import no.ntnu.mikaelr.util.LogRecordType;
import java.util.Date;

public class LogRecordOut {

    private Integer id;
    private Date date;
    private String description;
    private LogRecordType type;
    private Integer generatedScore;

    private Integer suggestionId;
    private Integer projectId;
    private String achievementBadgeName;

    public static LogRecordOut fromLogRecord(LogRecord logRecord) {

        LogRecordOut logRecordOut = new LogRecordOut();

        logRecordOut.id = logRecord.getId();
        logRecordOut.date = logRecord.getDate();
        logRecordOut.description = logRecord.getDescription();
        logRecordOut.type = logRecord.getType();
        logRecordOut.generatedScore = logRecord.getGeneratedScore();

        if (logRecord.getType() == LogRecordType.ACHIEVEMENT && logRecord.getAchievement() != null) {
            logRecordOut.setAchievementBadgeName(logRecord.getAchievement().getBadgeName());
        }

        // TODO: Suggestion and project

        return logRecordOut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LogRecordType getType() {
        return type;
    }

    public void setType(LogRecordType type) {
        this.type = type;
    }

    public Integer getGeneratedScore() {
        return generatedScore;
    }

    public void setGeneratedScore(Integer generatedScore) {
        this.generatedScore = generatedScore;
    }

    public Integer getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(Integer suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getAchievementBadgeName() {
        return achievementBadgeName;
    }

    public void setAchievementBadgeName(String achievementBadgeName) {
        this.achievementBadgeName = achievementBadgeName;
    }
}
