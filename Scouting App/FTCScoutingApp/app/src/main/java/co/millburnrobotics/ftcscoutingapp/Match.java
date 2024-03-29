package co.millburnrobotics.ftcscoutingapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;
import com.parse.ParseRelation;

import java.util.Date;
import java.util.List;

/**
 * Created by Yanjun on 9/29/2015.
 */
@ParseClassName("Match")
public class Match extends ParseObject {

    public Match() {

    }

    public void setCompetitionName(String competitionName) {
        put(COMPETITION_NAME, competitionName);
    }

    public String getCompetitionName() {
        return getString(COMPETITION_NAME);
    }

    public void setCompetitionDate(Date competitionDate) {
        put(COMPETITION_DATE, competitionDate);
    }

    public Date getCompetitionDate() {
        return getDate(COMPETITION_DATE);
    }

    public Competition getCompetition() {
        ParseQuery<Competition> query = ParseQuery.getQuery(Competition.class);
        query.whereEqualTo(Competition.NAME, getString(COMPETITION_NAME));
        List<Competition> list = null;

        try {
            list = query.find();
        } catch (ParseException e) {
            return null;
        }

        if (list.size() >= 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void setMatchNumber(int matchNumber) {
        put(MATCH_NUMBER, matchNumber);
    }

    public int getMatchNumber() {
        return getInt(MATCH_NUMBER);
    }

    public void addMatchData(MatchData matchData) {
        ParseRelation<MatchData> relation = getRelation(MATCH_DATA);
        relation.add(matchData);
    }

    public void removeMatchData(MatchData matchData) {
        ParseRelation<MatchData> relation = getRelation(MATCH_DATA);
        relation.remove(matchData);
    }

    public ParseRelation<MatchData> getMatchDataz() {
        return getRelation(MATCH_DATA);
    }

    public String getNotes() {
        return getString(NOTES);
    }

    public void setNotes(String notes) {
        put(NOTES, notes);
    }

    public int getBlueAllianceRawScore() {
        return getInt(BLUE_ALLIANCE_RAW_SCORE);
    }

    public void setBlueAllianceRawScore(int score) {
        put(BLUE_ALLIANCE_RAW_SCORE, score);
    }

    public int getBlueAlliancePenalty() {
        return getInt(BLUE_ALLIANCE_PENALTY);
    }

    public void setBlueAlliancePenalty(int penalty) {
        put(BLUE_ALLIANCE_PENALTY, penalty);
    }

    public int getRedAllianceRawScore() {
        return getInt(RED_ALLIANCE_RAW_SCORE);
    }

    public void setRedAllianceRawScore(int score) {
        put(RED_ALLIANCE_RAW_SCORE, score);
    }

    public int getRedAlliancePenalty() {
        return getInt(RED_ALLIANCE_PENALTY);
    }

    public void setRedAlliancePenalty(int penalty) {
        put(RED_ALLIANCE_PENALTY, penalty);
    }

    public int getRedAllianceTotalScore() {
        return getInt(RED_ALLIANCE_RAW_SCORE) + getInt(RED_ALLIANCE_PENALTY);
    }

    public int getBlueAllianceTotalScore() {
        return getInt(BLUE_ALLIANCE_RAW_SCORE) + getInt(BLUE_ALLIANCE_PENALTY);
    }


    public static final String COMPETITION_NAME = "competitionName";
    public static final String COMPETITION_DATE = "competitionDate";
    public static final String MATCH_DATA = "matchData";
    public static final String MATCH_NUMBER = "matchNumber";
    public static final String NOTES = "notes";
    public static final String BLUE_ALLIANCE_RAW_SCORE = "blueAllianceRawScore";
    public static final String BLUE_ALLIANCE_PENALTY = "blueAlliancePenalty";
    public static final String RED_ALLIANCE_RAW_SCORE = "redAllianceRawScore";
    public static final String RED_ALLIANCE_PENALTY = "redAlliancePenalty";
}
