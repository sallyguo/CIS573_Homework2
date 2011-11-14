import java.util.ArrayList;


public class Filter {
    Integer startYear = null;
    Integer endYear = null;
    String team = null;
    Boolean showWin = null;
    Boolean showLoss = null;
    
    Filter setStartYear(int startYear){
        this.startYear = startYear;
        return this;
    }
    
    Filter setEndYear(int endYear){
        this.endYear = endYear;
        return this;
    }
    
    Filter setYear(int year){
        this.startYear = year;
        this.endYear = year;
        return this;
    }
    
    Filter setTeam(String team){
        this.team = team;
        return this;
    }
    
    Filter setCondition(String chr){
        if ("A".equalsIgnoreCase(chr)){
            this.showWin = true;
            this.showLoss = true;
        } else if ("W".equalsIgnoreCase(chr)){
            this.showWin = true;
        } else if ("L".equalsIgnoreCase(chr)){
            this.showLoss = true;
        }
        return this;
    }
    
    ArrayList<WorldSeriesInstance> filter(ArrayList<WorldSeriesInstance> original){
        ArrayList<WorldSeriesInstance> filtered = new ArrayList<WorldSeriesInstance>();
        for (WorldSeriesInstance current : original){
            if (isValid(current))
                filtered.add(current);
        }
        return filtered;
    }
    
    boolean isValid(WorldSeriesInstance instance){
        int sum = 0;
        sum += checkYear(instance);
        sum += checkTeam(instance);
        sum += checkTeamWin(instance);
        sum += checkTeamLoss(instance);
        if (sum>0)
            return false;
        return true;
    }
    
    int checkYear(WorldSeriesInstance instance){
        if ((this.startYear == null) || (this.endYear == null))
            return 0;
        if ((instance.year() >= this.startYear) && (instance.year() <= this.endYear))
            return 0;
        return 1;
    }
    
    int checkTeam(WorldSeriesInstance instance){
        if (this.team == null)
            return 0;
        if ((instance.winner().equalsIgnoreCase(this.team)) || (instance.loser().equalsIgnoreCase(this.team)))
            return 0;
        return 1;
    }
    
    int checkTeamWin(WorldSeriesInstance instance){
        if ((this.team == null) || this.showWin == null)
            return 0;
        if (this.showWin && (instance.winner().equalsIgnoreCase(this.team)))
            return 0;
        return 1;
    }
    
    int checkTeamLoss(WorldSeriesInstance instance){
        if ((this.team == null) || this.showLoss == null)
            return 0;
        if (this.showLoss && (instance.loser().equalsIgnoreCase(this.team)))
            return 0;
        return 1;
    }

}
