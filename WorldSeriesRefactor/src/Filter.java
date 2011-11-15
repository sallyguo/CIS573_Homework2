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
        sum += checkTeamName(instance);
        sum += checkTeamWinLoss(instance);
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
    
    int checkTeamName(WorldSeriesInstance instance){
        if (this.team == null)
            return 0;
        if (containsTeam(instance.winner(), team) || containsTeam(instance.loser(), team))
            return 0;
        return 1;
    }
    
    int checkTeamWinLoss(WorldSeriesInstance instance){
        if (this.team == null)
            return 0;
        if ((this.showWin != null) && this.showWin && containsTeam(instance.winner(), team))
            return 0;
        if ((this.showLoss != null) && this.showLoss && containsTeam(instance.loser(), team))
            return 0;
        return 1;
    }
    
    static boolean containsTeam(String arg1, String arg2){
        return arg1.toUpperCase().contains(arg2.toUpperCase());
    }

}
