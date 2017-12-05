package match.service;


import match.commons.Sc2Exception;
import match.dao.AccountMapper;
import match.dao.MatchPoolMapper;
import match.dao.MatchResultMapper;
import match.dao.StatisticalListMapper;
import match.dao.model.*;
import match.utils.EnumUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author wangjian
 * @date 17-10-30
 */
@Configuration
@EnableScheduling
public class ScheduleTask {
    /**
     * if the match is started
     */
    public static AtomicBoolean matchOn = new AtomicBoolean(true);

    @Resource
    private MatchPoolMapper matchPoolMapper;
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private MatchResultMapper matchResultMapper;
    @Resource
    private StatisticalListMapper statisticalListMapper;
    @Resource
    private MatchService matchService;
    @Value("${map.directory}")
    private String mapDirectory;
    @Value("${sc2.path}")
    private String sc2Path;
    @Value("${replay.path}")
    private String replayPath;



    public File selectMap(String mapPath) throws Sc2Exception {
        File mapDir = new File(mapPath);
        if (mapDir.isDirectory()) {
            File[] mapFiles = mapDir.listFiles();
            if (mapFiles == null) {
                throw new Sc2Exception("no map files", 100);
            }
            int i = RandomUtils.nextInt(0, mapFiles.length);
            return mapFiles[i];
        } else {
            throw new Sc2Exception("no map directory", 101);
        }
    }
    @Scheduled(cron = "0/10 * * * * ? ")
    public void match() throws Exception {
        if(!ScheduleTask.matchOn.get()){
            System.out.println("The match is off.");
            return;
        }
        MatchPoolExample matchPoolExample = new MatchPoolExample();
        matchPoolExample.createCriteria();
        List<MatchPool> matchPoolList = matchPoolMapper.selectByExample(matchPoolExample);
        if (matchPoolList.size() < 2) {
            throw new Sc2Exception("Not Enough Bots", 23);
        }
        Collections.shuffle(matchPoolList);
        MatchPool A = matchPoolList.get(0);
        MatchPool B = matchPoolList.get(1);
        if (A.getInMatch() || B.getInMatch()) {
            return;
        }
        File mapFile = selectMap(mapDirectory);
        String mapPath = mapFile.getAbsolutePath();
        String mapName = mapFile.getName();

        String usernameA = A.getUsername();
        String usernameB = B.getUsername();
        Account accountA = accountMapper.selectByPrimaryKey(usernameA);
        Account accountB = accountMapper.selectByPrimaryKey(usernameB);
        int matchResultInt = matchService.match(sc2Path,
                accountA.getBotPath(), accountA.getConfigPath(), EnumUtils.getRace(accountA.getRace()),
                accountB.getBotPath(), accountB.getConfigPath(), EnumUtils.getRace(accountB.getRace()),
                mapPath, replayPath);
        // update match result
        MatchResult matchResult = new MatchResult();
        matchResult.setBotNameA(accountA.getBotName());
        matchResult.setBotNameB(accountB.getBotName());
        matchResult.setId(null);
        matchResult.setMap(mapName);
        matchResult.setReplayPath(replayPath);
        matchResult.setTime(new Date());
        matchResult.setUsernameA(usernameA);
        matchResult.setUsernameB(usernameB);
        matchResult.setWin(matchResultInt);
        matchResultMapper.insert(matchResult);

        updateStatistical(usernameA, matchResultInt);
        updateStatistical(usernameB, -matchResultInt);

    }

    private void updateStatistical(String username, int win) {
        StatisticalList statisticalList = statisticalListMapper.selectByPrimaryKey(username);
        statisticalList.setMatches(statisticalList.getMatches() + 1);
        if (win == 1) {
            statisticalList.setWins(statisticalList.getWins() + 1);
        } else {
            statisticalList.setWins(statisticalList.getWins());
        }
        statisticalList.setWinRate(((float) statisticalList.getWins()) / statisticalList.getMatches());
        statisticalListMapper.updateByPrimaryKeySelective(statisticalList);
    }

}

